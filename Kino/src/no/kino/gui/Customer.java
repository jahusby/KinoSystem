package no.kino.gui;

import no.kino.control.Control;
import no.kino.domain.Movie;
import no.kino.domain.Showing;
import no.kino.domain.Sort;
import no.kino.domain.Ticket;
import no.kino.gui.customerContent.CustomerOrder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.*;

public class Customer extends JDialog implements ActionListener {
    Control control = Control.getInstance();


    private JTable showingsTable;
    private final String[] colNames = {"Visningsnummer", "Filmnavn", "Kinosalnr", "Dato", "Starttid", "Pris"};
    private javax.swing.JCheckBox seat;

    public Customer(String title) throws Exception {
        setTitle(title);
        setLayout(new GridLayout(1, 1));
        JPanel adminPanel = new JPanel();
        add(adminPanel);
        adminPanel.setLayout(new GridLayout(1, 2));
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);

        JButton executeButton = new JButton("Velg visning");
        executeButton.addActionListener(this);
        buttonPanel.add(executeButton);

        JButton sortTimeButton = new JButton("Sorter tid");
        sortTimeButton.addActionListener(this);
        buttonPanel.add(sortTimeButton);

        JButton sortMovieNameButton = new JButton("Sorter etter filmnavn");
        sortMovieNameButton.addActionListener(this);
        buttonPanel.add(sortMovieNameButton);

        JButton searchTicketButton = new JButton("Søk etter billett");
        searchTicketButton.addActionListener(this);
        buttonPanel.add(searchTicketButton);

        JButton closeButton = new JButton("Lukk");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);

        Object[][] defaultTable = new Object[2][6];
        DefaultTableModel tableContent = new DefaultTableModel(defaultTable, colNames);
        showingsTable = new JTable(tableContent);
        adminPanel.add(showingsTable);
        JScrollPane scrollPane = new JScrollPane(showingsTable);
        adminPanel.add(scrollPane);
        createShowingsTable();

        setLocation(300, 300);
        pack();
    }

    // metode for å returnere kundens valgte visningnummer
    public int selectedRow() throws Exception {
        Integer test = (Integer) showingsTable.getValueAt(showingsTable.getSelectedRow(), 0);
        return test;

    }

    // metode for å lage visningstabellen, igangsetter metoden som fyller objektet content
    public void createShowingsTable() {
        Object[][] table = fillTable();
        showingsTable.setModel(new DefaultTableModel(table, colNames));
    }

    // sortering av listen etter tid
    public int compare(Showing s1, Showing s2) {
        Time firstTime = s1.getStartingTime();
        Time secondTime = s2.getStartingTime();
        return firstTime.compareTo(secondTime);
    }

    public ArrayList<Showing> sortListByTime() {
        ArrayList<Showing> showingList = control.getShowingsList();
        Collections.sort(showingList, this::compare);
        System.out.println(showingList);

        showingsTable.setModel(new DefaultTableModel(fillTable(), colNames));
        return showingList;
    }
    // sortering etter tid slutt

    public int compareTo(Sort s1, Sort s2) {
        String firstMovieName = s1.getMovieName();
        String secondMovieName = s2.getMovieName();
        return firstMovieName.compareTo(secondMovieName);
    }

    public ArrayList<Sort> testInit() {
        ArrayList<Sort> sortList = control.getSortList();
        fillTable();
        Collections.sort(sortList, this::compareTo);
        System.out.println(sortList);
        showingsTable.setModel(new DefaultTableModel(fillSort(), colNames));
        return sortList;
    }

    public Object[][] fillSort() {
        Object[][] content = new Object[control.getSortList().size()][6];
        ArrayList<Sort> sortList = control.getSortList();

        int counter = 0;
        try {
            for (Sort s : sortList) {
                int showingNumber = s.getShowingNumber();
                String movieName = s.getMovieName();
                int cinemaRoomNumber = s.getCinemaNumber();
                Date date = s.getDate();
                Time startTime = s.getStartingTime();
                double price = s.getPrice();

                content[counter][0] = showingNumber;
                content[counter][1] = movieName;
                content[counter][2] = cinemaRoomNumber;
                content[counter][3] = date;
                content[counter][4] = startTime;
                content[counter][5] = price;
                counter++;
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Klarte ikke opprette tabell for visninger");
        }
        return content;
    }

    // objektet som vises i visningstabellen fylles
    public Object[][] fillTable() {
        Object[][] content = new Object[control.getShowingsList().size()][6];
        ArrayList<Showing> showingList = control.getShowingsList();
        ArrayList<Movie> movieList = control.getMovieList();

        int counter = 0;
        try {
            for (Showing s : showingList) {
                int showingNumber = s.getShowingNumber();
                int movieNumber = s.getMovieNumber();
                int cinemaRoomNumber = s.getCinemaNumber();
                Date date = s.getDate();
                Time startTime = s.getStartingTime();
                double price = s.getPrice();
                for (Movie m : movieList) {
                    if (movieNumber == m.getMovieNumber()) {
                        String movieName = m.getMovieName();
                        content[counter][1] = movieName;
                    }
                }
                content[counter][0] = showingNumber;
                content[counter][2] = cinemaRoomNumber;
                content[counter][3] = date;
                content[counter][4] = startTime;
                content[counter][5] = price;
                counter++;
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Klarte ikke opprette tabell for visninger");
        }
        return content;
    }

    //Søker etter bestilling fra arraylist og skriver ut informasjon for bestillingen hvis den blir funnet.
    public Ticket findTicket() {
        ArrayList<Ticket> ticketList = control.getTicketList();
        String ticketSearch = JOptionPane.showInputDialog("Billettkode: ");
        for (Ticket key : ticketList) {
            String ticketNumber = key.getTicketNumber();
            int showNumber = key.getShowingNumber();
            int isPaid = key.getIsPaid();
            if(ticketSearch.equals(ticketNumber)) {
                if(isPaid != 0) {
                    JOptionPane.showMessageDialog(null, "Billettkode: " + ticketNumber + "\nVisningsnummer: "
                            + showNumber + "\nBilletten er betalt");
                } else {
                    JOptionPane.showMessageDialog(null, "Billettkode: " + ticketNumber + "\nVisningsnummer: "
                            + showNumber + "\nBilletten er ikke betalt");
                }

            }else {
                JOptionPane.showMessageDialog(null, "Billetten ble ikke funnet.");
            }

        }return null;
    }


    // håndterer valg av knapper
    @Override
    public void actionPerformed(ActionEvent e) {
        String choice = e.getActionCommand();
        try {
            if (choice.equals("Velg visning")) {

                CustomerOrder customerOrder = new CustomerOrder("Velg plasser", selectedRow());
                customerOrder.setVisible(true);
            } else if (choice.equals("Lukk")) {
                System.exit(0);
            } else if (choice.equals("Sorter tid")) {
                sortListByTime();

            } else if (choice.equals("Sorter etter filmnavn")) {
                testInit();
            }
            else if (choice.equals("Søk etter billett")) {
                findTicket();
            }
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}