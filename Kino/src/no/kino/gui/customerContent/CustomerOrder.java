package no.kino.gui.customerContent;

import no.kino.control.Control;
import no.kino.domain.Seat;
import no.kino.domain.Showing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerOrder extends javax.swing.JFrame implements ActionListener {
    Control control = Control.getInstance();

    JCheckBox[][] buttons;

    JPanel mPanel = new JPanel();

    JPanel bPanel = new JPanel();

    JPanel cPanel = new JPanel();

    JTextArea scoreKeeper = new JTextArea();

    Container c = getContentPane();

    int[][] intArray;



    public CustomerOrder(String title, int number) throws Exception {


        butGen(getCinemaRow(number), getCinemaSeatsPerRow(number));



        //cPanel.add(scoreKeeper);

        bPanel.setLayout(new GridLayout(getCinemaRow(number), getCinemaSeatsPerRow(number)));

        mPanel.setLayout(new BorderLayout());

        mPanel.add(bPanel, BorderLayout.CENTER);

        mPanel.add(cPanel, BorderLayout.SOUTH);

        c.add(mPanel);


        setTitle("ButtonMaddness");

        setSize(1000, 400);

        setLocation(200, 200);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }


    private void butGen(int row, int column){

         buttons = new JCheckBox[row][column];
         intArray = new int[row][column];


        for(int i=0;i<row;i++)

            for(int j=0;j<column;j++)

            {

                buttons[i][j] = new JCheckBox();

                buttons[i][j].setActionCommand("button" +i +"_" +j);

                buttons[i][j].addActionListener(this);

                bPanel.add(buttons[i][j]);

            }

    }



    /*private void score()

    {

        String string = "";

        for(int i=0;i<4;i++)

        {

            for(int j=0;j<10;j++)

                string += i+"x"+j+" => " +String.valueOf(intArray[i][j]) +"\t";

            string+= "\n";

        }

        scoreKeeper.setText(string);

    }



    private void score2()

    {

        for(int i=0;i<4;i++)

            for(int j=0;j<10;j++)

                buttons[i][j].setText(String.valueOf(intArray[i][j]));

    }*/

    private int getCinemaSeatsPerRow(int showingNumber) {
        ArrayList<Seat> seatList = control.getSeatList();
        ArrayList<Showing> showingsList = control.getShowingsList();
        int counter = 0;
        for(Showing showing : showingsList) {
            if (showingNumber == showing.getShowingNumber()) {
                int movieTheaterNumber = showing.getCinemaNumber();
                for(Seat s : seatList) {
                    if(movieTheaterNumber==s.getMovieTheaterNr() && 1 == s.getrowNr()) {
                        counter++;
                        System.out.println(counter);
                    }
                }
            }
        }
        return counter;
    }

    private int getCinemaRow(int showingNumber) {
        ArrayList<Seat> seatList = control.getSeatList();
        ArrayList<Showing> showingsList = control.getShowingsList();
        int counter = 0;
        for(Showing showing : showingsList) {
            if (showingNumber == showing.getShowingNumber()) {
                int movieTheaterNumber = showing.getCinemaNumber();
                for(Seat s : seatList) {
                    if(movieTheaterNumber==s.getMovieTheaterNr() && 1 == s.getseatNr()) {
                        counter++;
                        System.out.println(counter);
                    }
                }
            }
        }
        return counter;
    }

	    public ArrayList<OrderConfirmation> orderConfirmationArrayList() {
        ArrayList<OrderConfirmation> orderConfirmations = new ArrayList<>();
        for (OrderConfirmation o : orderConfirmations) {
            String movieName = o.getMovieName();
            int movieTheaterNumber = o.getMovieTheaterNumber();
            Time time = o.getTime();
            Date date = o.getDate();
            int rowNumber = o.getRowNumber();
            int seatNumber = o.getSeatNumber();
            String ticketCode = o.getTicketCode();

            OrderConfirmation orderConfirmation = new OrderConfirmation(movieName, movieTheaterNumber, date, time, ticketCode,rowNumber, seatNumber);
            orderConfirmations.add(orderConfirmation);
            System.out.println("hei");
        }
        return orderConfirmations;
    }



    public void actionPerformed(ActionEvent e){
		String choice = e.getActionCommand();
        if(e.getActionCommand().contains("button"))

        {

            int i = Integer.parseInt(Character.toString(e.getActionCommand().replaceAll("button","").replaceAll("_", "").charAt(0)));

            int j = Integer.parseInt(Character.toString(e.getActionCommand().replaceAll("button","").replaceAll("_", "").charAt(1)));

            intArray[i][j]++;

            System.out.println(e.getActionCommand() +"  " +i +"  " +j);

        }
		if (choice.equals("Velg seter")) {
               orderConfirmationArrayList();
        }
           else if (choice.equals("Tilbake")) {
                System.exit(0);
        }



    }






}
