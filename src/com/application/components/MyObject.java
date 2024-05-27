package com.application.components;

import javax.swing.JPanel;

import java.awt.*;

public class MyObject extends JPanel {
    protected final Color OBJ_COLOR = new Color(135, 62, 35);
    protected final Color ARROW_COLOR = new Color(238, 238, 228);
    protected final Color BACKGROUND_COLOR = new Color(171, 219, 227);

    protected int size;
    protected int mass;
    protected Point center;
    protected Point upperLeft;

    private final int WIDTH = 1200;
    private final int HEIGHT = 300;

    public MyObject(int size, int mass) {
        this.size = size;
        this.mass = mass;
        setBackground(BACKGROUND_COLOR);
        setSize(1200, size);
        setMaximumSize(new Dimension(WIDTH, HEIGHT));
        center = new Point(WIDTH / 2, HEIGHT - size / 2);
        upperLeft = new Point((WIDTH - size) / 2, HEIGHT - size);
    }

    protected void arrowLine(Graphics g, Point from, Point to) {
        int d = 15;
        g.setColor(ARROW_COLOR);
        g.drawLine(from.x, from.y, to.x, to.y);
        g.fillOval(from.x - d / 2, from.y - d / 2, d, d);
        if (from.x > to.x) {
            g.fillPolygon(new int[] { to.x, to.x + d, to.x + d, to.x },
                    new int[] { to.y, to.y - d, to.y + d, to.y }, 4);
        } else {
            g.fillPolygon(new int[] { to.x, to.x - d, to.x - d, to.x },
                    new int[] { to.y, to.y - d, to.y + d, to.y }, 4);
        }
    }
}