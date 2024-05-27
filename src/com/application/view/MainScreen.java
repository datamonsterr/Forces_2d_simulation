package com.application.view;

import java.awt.*;
import javax.swing.*;
import com.application.components.*;

public class MainScreen extends JFrame {
    private Container container = getContentPane();

    public MainScreen() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel surface = new Surface();
        // CubeObject cube = new CubeObject(100, 50);
        CubeObject cube = new CubeObject(100, 50);

        JPanel simulationPanel = new JPanel();
        JPanel inputPanel = new UserInput();

        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
        simulationPanel.add(cube);
        simulationPanel.add(surface);

        container.add(simulationPanel);
        container.add(inputPanel);

        pack();

        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
