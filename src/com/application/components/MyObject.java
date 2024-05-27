package com.application.components;

import javax.swing.JPanel;

import java.awt.*;

public abstract class MyObject extends JPanel {
    public static final Color OBJ_COLOR = new Color(135, 62, 35);
    public static final Color ARROW_COLOR = new Color(238, 238, 228);
    public static final Color BACKGROUND_COLOR = new Color(171, 219, 227);

    protected int size;
    protected int mass;
    protected Point center;
    protected Point upperLeft;
    private String direction;

    private final int WIDTH = 1200;
    private final int HEIGHT = 300;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintShape(g);
        if (direction == "right") {
            arrowLine(g, center, new Point(center.x + size, center.y));
        } else if (direction == "left") {
            arrowLine(g, center, new Point(center.x - size, center.y));
        }
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

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

    abstract protected void paintShape(Graphics g);
}