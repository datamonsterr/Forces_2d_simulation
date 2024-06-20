package com.application.components.force;

import java.awt.*;

import com.application.components.controller.Controller;

public class Force {
    private String label;
    private double magnitude;
    private int direction; // 1 == l to r, -1 == r to l;

    public Force(String label, double magnitude, int direction) {
        this.direction = direction;
        this.magnitude = magnitude;
        this.label = label;
    }

    public void apply(Graphics g, int fromY) {
        Point from = new Point(Controller.getObj().getWidth() / 2, Controller.getObj().getHeight() / 2 + fromY);
        Point to = new Point(from.x + direction * (int) (magnitude / 10), from.y);
        arrowLine(g, from, to);
        g.drawString(label, to.x - direction * 20, to.y - 10);
    }

    private void arrowLine(Graphics g, Point from, Point to) {
        int d = 5;
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
