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
    protected int width;
    protected int height = 150;
    protected URL imgURL;
    protected Image img;
    protected double actor1;
    protected double actor2;

    public int calWidth(int w, int h, int height) {
        return w / h * height;
    }

    public double getActor1() {
        return UserInput.get("Actor 1");
    }

    public double getActor2() {
        return UserInput.get("Actor 2");
    }

    public double getCurVelocity(double t) {
        return velocity + t * acceleration;
    }

    public double getAcc() {
        return acceleration;
    }

    public double getMass() {
        return UserInput.get("Mass");
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

    public MyObject(int mass, int height, URL imgURL) {
        this.height = height;
        this.mass = 0;
        try {
            Image img = ImageIO.read(imgURL);
            img = img.getScaledInstance(calWidth(img.getWidth(null), img.getHeight(null), height), height,
                    Image.SCALE_DEFAULT);
            this.img = img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension((int) Controller.getWindowWidth(), height));
        setBackground(new Color(0, 0, 0, 0));
    }

    public double getFrictionForce() {
        return Physic.getFrictionForce();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawShape(g, img);
        if (getActor1() != 0) {
            Force f = new Force("Actor 1", Math.abs(getActor1()), getActor1() > 0 ? 1 : -1);
            g.setColor(Color.WHITE);
            f.apply(g, -10);
        }
        if (getActor2() != 0) {
            Force f = new Force("Actor 2", Math.abs(getActor2()), getActor2() > 0 ? 1 : -1);
            g.setColor(Color.CYAN);
            f.apply(g, 0);
        }
        if (getActor1() != 0 || getActor2() != 0) {
            Force f = new Force("Resultant", Math.abs(getActor1() + getActor2()),
                    getActor1() + getActor2() > 0 ? 1 : -1);
            g.setColor(Color.GREEN);
            f.apply(g, -20);
        }
        if (getFrictionForce() != 0) {
            Force f = new Force("Friction", Math.abs(getFrictionForce()), getActor1() + getActor2() < 0 ? 1 : -1);
            g.setColor(Color.RED);
            f.apply(g, 10);
        }
    }

    protected abstract void drawShape(Graphics g, Image img);
}
