package com.application.components;

import java.awt.Graphics;

public class CylinderObject extends MyObject {
    public CylinderObject(int size, int mass) {
        super(size, mass);
    }

    protected void drawShape(Graphics g) {
        g.fillOval(upperLeft.x, upperLeft.y, size, size);
    }
}
