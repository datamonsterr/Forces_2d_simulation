package com.application.view;

import java.awt.*;
import javax.swing.*;

import com.application.components.panel.LowerPanel;
import com.application.components.panel.UpperPanel;

public class MainScreen extends JFrame {
    private Container container = getContentPane();

    public MainScreen() {
        setSize(1200, 900);

        LowerPanel lowerPane = new LowerPanel();

        UpperPanel upperPane = new UpperPanel();

        JSplitPane splitPane = new JSplitPane();

        upperPane.setLayout(new BorderLayout());
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(upperPane);
        splitPane.setBottomComponent(lowerPane);
        splitPane.setResizeWeight(0.7);

        container.add(splitPane);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
