package com.application.components.object;

import java.awt.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.application.components.controller.UserInput;

public abstract class MyObject extends JPanel {
    protected int mass;
    protected double velocity = 0.0d;
    protected double acceleration = 0.0d;
    protected int width = 150;
    protected int height = 150;
    protected URL imgURL;
    protected Image img;
    protected double actor1;
    protected double actor2;
    protected double friction;

    public double getActor1() {
        return Double.parseDouble(UserInput.get("Actor 1"));
    }

    public double getActor2() {
        return Double.parseDouble(UserInput.get("Actor 2"));
    }

    public double getCurVelocity(double t) {
        return velocity + t * acceleration;
    }

    public double getPosition(double t) {
        return velocity * t + 0.5 * acceleration * t * t;
    }

    public double getAcc() {
        return acceleration;
    }

    public double getMass() {
        return Double.parseDouble(UserInput.get("Mass"));
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void setAcc(double acceleration) {
        this.acceleration = acceleration;
    }

    public MyObject(int mass, int width, int height, URL imgURL) {
        this.width = width;
        this.height = height;
        this.mass = 0;
        try {
            Image img = ImageIO.read(imgURL);
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            this.img = img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawShape(g, img);
    }

    protected abstract void drawShape(Graphics g, Image img);
}
