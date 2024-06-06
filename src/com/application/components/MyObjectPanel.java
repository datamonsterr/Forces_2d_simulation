package com.application.components;

import javax.swing.JPanel;

import java.awt.*;

public abstract class MyObjectPanel extends JPanel {
    public static final Color OBJ_COLOR = new Color(135, 62, 35);
    public static final Color ARROW_COLOR = new Color(238, 238, 228);
    public static final Color BACKGROUND_COLOR = new Color(171, 219, 227);

    protected static final int WIDTH = 1200;
    protected static final int HEIGHT = 300;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintShape(g);
    }

    public MyObjectPanel() {
        setBackground(BACKGROUND_COLOR);
        setSize(WIDTH, HEIGHT);
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
    }

    abstract protected void paintShape(Graphics g);
}