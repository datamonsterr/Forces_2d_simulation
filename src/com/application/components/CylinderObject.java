package com.application.components;

import java.awt.Graphics;

public class CylinderObject extends MyObject {
    public CylinderObject(int size, int mass) {
        super(size, mass);
    }

    protected void paintShape(Graphics g) {
        g.setColor(OBJ_COLOR);
        g.fillOval(upperLeft.x, upperLeft.y, size, size);
    }
}
