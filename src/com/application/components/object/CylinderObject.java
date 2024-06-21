package com.application.components.object;

import java.awt.*;
import java.net.URL;

public class CylinderObject extends MyObject {
    private int radius = 150;

    public double getAngularAcc() {
        return getFrictionForce() / (0.5 * getMass() * radius * radius / 10000);
    }

    public double getCurrentAngularVelocity(double t) {
        return getAngularAcc() * t;
    }

    public double getCurrentAngularPosition(double t) {
        return 0.5 * getAngularAcc() * t * t;
    }

    public CylinderObject(int mass, int width, int height, URL imgUrl, int radius) {
        super(mass, width, height, imgUrl);
        this.radius = radius;
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, getWidth() / 2 - width / 2, 0, null);
    }
}
