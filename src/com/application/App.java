package com.application;

import javax.swing.SwingUtilities;

import com.application.view.StartScreen;

public class App {
    public static void main(String[] args) {
        // Run the application in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartScreen();
            }
        });
    }
}
