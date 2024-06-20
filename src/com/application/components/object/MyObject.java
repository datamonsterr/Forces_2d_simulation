package com.application.components.object;

import java.awt.*;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.application.components.controller.Controller;
import com.application.components.controller.Physic;
import com.application.components.controller.UserInput;
import com.application.components.force.Force;

public abstract class MyObject extends JPanel {
    protected double position = 0;
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

    public void setPosition(double position) {
        this.position = position;
    }

    public double getPosition() {
        return position;
    }

    public double getFriction() {
        return friction;
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
        setPreferredSize(new Dimension((int) Controller.getWindowWidth(), height));
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShape(g, img);
        if (getActor1() != 0) {
            Force f = new Force("Actor 1", getActor1(), getActor1() > 0 ? 1 : -1);
            g.setColor(Color.WHITE);
            f.apply(g);
        }
        if (getActor2() != 0) {
            Force f = new Force("Actor 2", getActor2(), getActor2() > 0 ? 1 : -1);
            g.setColor(Color.CYAN);
            f.apply(g);
        }
        if (Physic.getTotalForce() != 0) {
            Force f = new Force("Friction", -1 * Physic.getFrictionForce(),
                    Physic.getTotalForce() > 0 ? 1 : -1);
            g.setColor(Color.RED);
            f.apply(g);
        }
    }

    protected abstract void drawShape(Graphics g, Image img);
}
