package com.application.components;

import java.awt.Graphics;

public class CubeObject extends MyObject {
    public CubeObject(int size, int mass) {
        super(size, mass);
    }

    protected void paintShape(Graphics g) {
        g.setColor(OBJ_COLOR);
        g.fillRect(upperLeft.x, upperLeft.y, size, size);
    }
}
