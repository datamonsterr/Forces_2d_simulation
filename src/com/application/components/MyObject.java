package com.application.components;

import java.awt.*;

public abstract class MyObject {
    public int size;
    protected int mass;
    protected Point center;
    protected Point upperLeft;

    public MyObject(int size, int mass) {
        this.size = size;
        this.mass = mass;
        center = new Point(MyObjectPanel.WIDTH / 2, MyObjectPanel.HEIGHT - size / 2);
        upperLeft = new Point((MyObjectPanel.WIDTH - size) / 2, MyObjectPanel.HEIGHT - size);
    }

    public void draw(Graphics g) {
        g.setColor(MyObjectPanel.OBJ_COLOR);
        drawShape(g);
    }

    protected abstract void drawShape(Graphics g);
}
