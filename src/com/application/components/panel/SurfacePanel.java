package com.application.components.panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SurfacePanel extends JPanel {
    private final int RECT_WIDTH = 60;
    private final int RECT_WIDTH_IN_METRE = 5;
    private final int RECT_HEIGHT = 15;
    private final int GAP = 25;
    private final Color BACKGROUND_COLOR = new Color(4, 3, 54);
    private final Color SURFACE_COLOR = new Color(240, 240, 240);

    private ArrayList<Integer> xPositions = new ArrayList<Integer>();

    public SurfacePanel() {
        for (int i = 0; i < 50; i++) {
            xPositions.add(i * (RECT_WIDTH + GAP));
        }
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createMatteBorder(5, 0, 0, 0, Color.white));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(SURFACE_COLOR);
        for (int x : xPositions) {
            g.fillRect(x, getHeight() / 2 - RECT_HEIGHT / 2, RECT_WIDTH, RECT_HEIGHT);
        }
    }

    public void updatePosition(double v, double t) {
        for (int i = 0; i < xPositions.size(); i++) {
            int x = xPositions.get(i);
            xPositions.set(i, x - (int) (v * t));
            if (x + RECT_WIDTH + GAP < 0) {
                x = 50 * (RECT_WIDTH + GAP);
                xPositions.remove(0);
                xPositions.add(x);
            }
        }
    }

}
