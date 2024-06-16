package com.application.components.object;

import java.awt.*;
import javax.swing.*;

import javax.imageio.ImageIO;

public abstract class MyObject extends JPanel {
    protected int size;
    protected Point upperLeft;
    protected int mass;
    protected double velocity = 0.0d;
    protected double acceleration = 0.0d;

    public double getCurVelocity(double t) {
        return velocity + t * acceleration;
    }

    public MyObject(int size, int mass, int x, int y) {
        this.size = size;
        this.mass = mass;
        upperLeft = new Point(x, y);
        setPreferredSize(new Dimension(1200, size));
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
            Image img = ImageIO.read(getClass().getResource("../../assets/bugatti.png"));
            g.drawImage(img, 0, 0, null);
        } catch (Exception e) {
            System.out.println("Cannot find image");
            g.fillRect(upperLeft.x, upperLeft.y, size, size);
        }
    }

    protected abstract void drawShape(Graphics g);
}
