package no.kino.gui.adminContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Statistics extends JDialog implements ActionListener {


    public Statistics(String title) {
        setTitle(title);
        setLayout(new GridLayout(2, 1));
        JPanel adminPanel = new JPanel();
        add(adminPanel);
        adminPanel.setLayout(new GridLayout(3, 2));
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);
        JButton closeButton = new JButton("Lukk");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);
        setLocation(300, 300);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}