package no.kino.gui.staffContent;

import no.kino.control.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddSale extends JDialog implements ActionListener {
    private JTextField inputVisningsNr;
    private JButton executeButton, closeButton;

    public AddSale(String title) {
        setTitle(title);
        setLayout(new GridLayout(2, 1));

        //JPanel
        JPanel staffPanel = new JPanel();
        add(staffPanel);
        staffPanel.setLayout(new GridLayout(3, 2));

        //JPanel for buttons
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);

        //VisningNr
        inputVisningsNr = new JTextField("VisningsNr");
        inputVisningsNr.addActionListener(this);
        buttonPanel.add(inputVisningsNr);

        //Execute-button
        executeButton = new JButton("Utfør");
        executeButton.addActionListener(this);
        buttonPanel.add(executeButton);

        //Closing the window
        closeButton = new JButton("Lukk");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);
        setLocation(300, 300);
        pack();
    }


    public void actionPerformed(ActionEvent event) {
        String choice = event.getActionCommand();
        try {
            if (choice.equals("Utfør")) {
                System.out.println("test");
            } else if (choice.equals("Lukk")) {
                System.exit(0);
            }
        }catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
