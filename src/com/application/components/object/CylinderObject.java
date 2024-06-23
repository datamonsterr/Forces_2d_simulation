package com.application.components.object;

import java.net.URL;

import javax.imageio.ImageIO;

import com.application.components.controller.Controller;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public CylinderObject(int mass, int height, URL imgUrl, int radius) {
        super(mass, height, imgUrl);
        this.radius = radius;
    }

    // write a function takes img and angle and return rotated image
    public Image rotateImage(Image img, double angle) {
        BufferedImage bimg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bimg.createGraphics();
        g2d.rotate(Math.toRadians(angle), img.getWidth(null) / 2, img.getHeight(null) / 2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bimg;
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(rotateImage(img, getCurrentAngularPosition(Controller.getTime() * 1000 / 60)),
                getWidth() / 2 - calWidth(img.getWidth(null), img.getHeight(null), height) / 2, 0, null);

    }

    protected void drawActor(Graphics g) {
        try {
            int direction = this.getAcc() < 0 ? -1 : 1;
            String imgName = direction == 1 ? "cr7.png" : "cr7_flip.png";
            Image actorImg = ImageIO.read(getClass().getResource("../../assets/" + imgName));
            int actorImgWidth = calWidth(actorImg.getWidth(null), actorImg.getHeight(null), height);
            actorImg = actorImg.getScaledInstance(actorImgWidth, 150, Image.SCALE_DEFAULT);
            if (direction == 1) {
                g.drawImage(actorImg,
                        getWidth() / 2 - (calWidth(img.getWidth(null), img.getHeight(null), height) / 2)
                                - actorImgWidth,
                        0,
                        null);
            } else {
                g.drawImage(actorImg,
                        getWidth() / 2 + (calWidth(img.getWidth(null), img.getHeight(null), height) / 2),
                        0,
                        null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
