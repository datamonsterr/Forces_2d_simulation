package com.application.components;

import java.awt.Graphics;

public class CubeObject extends MyObject {
    public CubeObject(int size, int mass) {
        super(size, mass);
    }

    protected void drawShape(Graphics g) {
        g.fillRect(upperLeft.x, upperLeft.y, size, size);
    }
}
