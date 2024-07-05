package com.application.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen {
    private JFrame frame;
    private JButton startButton;

    public StartScreen() {
        frame = new JFrame("Start Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create a button and center it in the frame
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 50));

        // Use a JPanel with GridBagLayout to center the button
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(startButton, gbc);

        frame.getContentPane().add(panel);

        // Add ActionListener to the button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToMainScreen();
            }
        });

        frame.setVisible(true);
    }

    private void switchToMainScreen() {
        frame.dispose();
        SwingUtilities.invokeLater(MainScreen::new);
    }

}