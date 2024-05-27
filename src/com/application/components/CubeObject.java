package com.application.components;

import java.awt.Graphics;
import java.awt.Point;

public class CubeObject extends MyObject {
    public CubeObject(int size, int mass) {
        super(size, mass);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(OBJ_COLOR);
        g.fillRect(upperLeft.x, upperLeft.y, size, size);
        arrowLine(g, center, new Point(center.x + size, center.y));
    }
}
