package no.kino.control;

import no.kino.domain.*;
import no.kino.gui.staffContent.DeleteUnpaidOrder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Control {
    private static final Control instance = new Control();
    private ArrayList<Movie> movieList = new ArrayList<>();
    private ArrayList<Movie> movieDeleteList = new ArrayList<>();


    private ArrayList<Showing> showingsList = new ArrayList<>();
    private ArrayList<Showing> showingsDeleteList = new ArrayList<>();

    private ArrayList<Ticket> ticketList = new ArrayList<>();
    private ArrayList<Ticket> delTicket = new ArrayList<>();

    private ArrayList<SeatTicket> seatTicketList = new ArrayList<>();
    private ArrayList<SeatTicket> delSeatTicket = new ArrayList<>();


    private ArrayList<Cinema> cinemaList = new ArrayList<>();

    private ArrayList<Seat> seatList = new ArrayList<>();

    private ArrayList<Sort> sortList = new ArrayList<>();


    private String databasename = "jdbc:mysql://localhost:3306/kino?useSSL=false";
    private static Connection connection;
    private ResultSet result;
    private Statement statement;

    private Control() {
        System.out.println(movieList);
        try {
            makeConnection();
            fillSortList();
            fillMovie();
            fillShowing();
            fillTicket();
            fillSeat();
            fillTicketSeat();
            fillCinema();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static Control getInstance() {
        return instance;
    }



    public void makeConnection() throws Exception {
        try{
            connection = DriverManager.getConnection(databasename, "Case", "Esac");
            System.out.println("kontakt med databasen!");
        } catch(Exception e) {
            throw new Exception("Kan ikke oppnå kontakt med databasen");
        }
    }

    private void closeConnection() throws Exception {
        try {
            if(connection != null) {
                connection.close();
                result.close();
                statement.close();
            }
        }catch(Exception e) {
            throw new Exception("Kan ikke lukke databaseforbindelse");
        }
    }

    public static boolean checkLoginAdmin(String username, String password) throws Exception {
        String sqlStatement = "SELECT * FROM tbllogin;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            String correctUsernameAdmin = result.getString("l_brukernavn");
            String correctPassordAdmin = result.getString("l_pinkode");
            int isAdmin = result.getInt("l_erPlanlegger");
            if(username.equals(correctUsernameAdmin) && password.equals(correctPassordAdmin) && isAdmin == 1) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkLoginStaff(String username, String password) throws SQLException {
        String sqlStatement = "SELECT * FROM tbllogin;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            String correctUsernameStaff = result.getString("l_brukernavn");
            String correctPassordStaff = result.getString("l_pinkode");
            int isAdmin = result.getInt("l_erPlanlegger");
            if(username.equals(correctUsernameStaff) && password.equals(correctPassordStaff) && isAdmin == 0) {
                return true;
            }
        }
        return false;
    }

    public void fillMovie() throws Exception {
        String sqlStatement = "SELECT * FROM tblfilm;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int movieNumber = result.getInt("f_filmnr");
            String movieName = result.getString("f_filmnavn");

            movieList.add(new Movie(movieNumber, movieName));
        }
    }

    public void fillShowing() throws Exception {
        String sqlStatement = "SELECT * FROM tblvisning;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int showingNumber = result.getInt("v_visningnr");
            int movieNumber = result.getInt("v_filmnr");
            int cinemaNumber = result.getInt("v_kinosalnr");
            Date date = result.getDate("v_dato");
            Time startingTime = result.getTime("v_starttid");
            double price = result.getDouble("v_pris");

            showingsList.add(new Showing(showingNumber, movieNumber, cinemaNumber, date, startingTime, price));
        }
    }

    public void fillTicket() throws Exception {
        String sqlStatement = "SELECT * FROM tblbillett;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            String ticketCode = result.getString("b_billettkode");
            int showingNumber = result.getInt("b_visningsnr");
            int isPaid = result.getInt("b_erBetalt");

            ticketList.add(new Ticket(ticketCode, showingNumber, isPaid));
        }
    }

    public void fillSeat() throws Exception {
        String sqlStatement = "SELECT * FROM tblplass;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int rowNumber = result.getInt("p_radnr");
            int seatNumber = result.getInt("p_setenr");
            int cinemaNumber = result.getInt("p_kinosalnr");

            seatList.add(new Seat(rowNumber, seatNumber, cinemaNumber));
        }
    }

    public void fillTicketSeat() throws Exception {
        String sqlStatement = "SELECT * FROM tblplassbillett;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int rowNumber = result.getInt("pb_radnr");
            int seatNumber = result.getInt("pb_setenr");
            int cinemaNumber = result.getInt("pb_kinosalnr");
            String ticketCode = result.getString("pb_billettkode");

            seatTicketList.add(new SeatTicket(rowNumber, seatNumber, cinemaNumber, ticketCode));
        }
    }

    public void fillCinema() throws Exception {
        String sqlStatement = "SELECT * FROM tblkinosal;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int cinemaNumber = result.getInt("k_kinosalnr");
            String cinemaName = result.getString("k_kinonavn");
            String cinemaRoomName = result.getString("k_kinosalnavn");

            cinemaList.add(new Cinema(cinemaNumber, cinemaName, cinemaRoomName));
        }
    }

    public void fillSortList() throws Exception {
        String sqlStatement = "SELECT v_visningnr, f_filmnavn, v_kinosalnr, v_dato, v_starttid, v_pris\n" +
                "FROM tblvisning, tblfilm WHERE v_filmnr = f_filmnr;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sqlStatement);
        while(result.next()) {
            int showingNumber = result.getInt("v_visningnr");
            String movieName = result.getString("f_filmnavn");
            int cinemaNumber = result.getInt("v_kinosalnr");
            Date date = result.getDate("v_dato");
            Time time = result.getTime("v_starttid");
            double price = result.getDouble("v_pris");

            sortList.add(new Sort(showingNumber, movieName, cinemaNumber, date, time, price));
        }
    }

    //Legger alle bestillinger som ikke er betalt 30min før visning i slettinger.dat og arraylister
    public ResultSet findNotPaid() throws Exception {
        DeleteUnpaidOrder Writer = new DeleteUnpaidOrder();
        try {
            String sqlFind = "select b_billettkode, b_visningsnr, b_erBetalt from tblvisning\n" +
                    "LEFT join tblbillett on b_visningsnr=v_visningnr where b_erBetalt=0 and \n" +
                    "v_starttid < date_sub(now(), interval -31 minute) and v_dato <= curdate()\n" +
                    "or b_erBetalt=0 and v_dato < curdate()\n" +
                    "ORDER BY b_visningsnr" ;
            java.sql.Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sqlFind);
            while(result.next()) {
                String ticketNumber = result.getString("b_billettkode");
                int showNumber = result.getInt("b_visningsnr");
                int isPaid = result.getInt("b_erBetalt");
                Ticket del = new Ticket(ticketNumber, showNumber, isPaid);
                SeatTicket delSeat = new SeatTicket(1, 1, 1, ticketNumber);
                delTicket.add(del);
                delSeatTicket.add(delSeat);
                try {
                    delTicket.add(del);
                    Writer.writer(delTicket);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(Exception e) {
            throw new Exception(e);
        }
        return result;
    }

    //Sletter alt som ligger i arraylistene for ikke betalte bestiller fra databasen.
    public void delNotPaid() throws Exception {
        for (SeatTicket key1 : delSeatTicket) {
            String ticketNumber1 = key1.getTicketCode();
            String sqlDelete1 = "DELETE FROM kino.tblplassbillett WHERE pb_billettkode = '" + ticketNumber1 + "';";
            Statement delDB1 = connection.prepareStatement(sqlDelete1);
            delDB1.executeUpdate(sqlDelete1);
            for (Ticket key : delTicket) {
                String ticketNumber =  key.getTicketNumber();
                String sqlDelete = "DELETE FROM kino.tblbillett WHERE b_billettkode = '"+ticketNumber+"';";
                Statement delDB = connection.prepareStatement(sqlDelete);
                delDB.executeUpdate(sqlDelete);
            }
        }
    }


    public ArrayList<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    public ArrayList<Movie> getMovieDeleteList() {
        return movieDeleteList;
    }

    public void setMovieDeleteList(ArrayList<Movie> movieDeleteList) {
        this.movieDeleteList = movieDeleteList;
    }

    public ArrayList<Showing> getShowingsList() {
        return showingsList;
    }

    public void setShowingsList(ArrayList<Showing> showingsList) {
        this.showingsList = showingsList;
    }

    public ArrayList<Showing> getShowingsDeleteList() {
        return showingsDeleteList;
    }

    public void setShowingsDeleteList(ArrayList<Showing> showingsDeleteList) {
        this.showingsDeleteList = showingsDeleteList;
    }

    public ArrayList<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public ArrayList<Ticket> getDelTicket() {
        return delTicket;
    }

    public void setDelTicket(ArrayList<Ticket> delTicket) {
        this.delTicket = delTicket;
    }

    public ArrayList<SeatTicket> getSeatTicketList() {
        return seatTicketList;
    }

    public void setSeatTicketList(ArrayList<SeatTicket> seatTicketList) {
        this.seatTicketList = seatTicketList;
    }

    public ArrayList<SeatTicket> getDelSeatTicket() {
        return delSeatTicket;
    }

    public void setDelSeatTicket(ArrayList<SeatTicket> delSeatTicket) {
        this.delSeatTicket = delSeatTicket;
    }

    public ArrayList<Cinema> getCinemaList() {
        return cinemaList;
    }

    public void setCinemaList(ArrayList<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    public ArrayList<Sort> getSortList() {
        return sortList;
    }

    public void setSortList(ArrayList<Sort> sortList) {
        this.sortList = sortList;
    }
}


