package com.application.components.object;

import java.awt.*;

import javax.imageio.ImageIO;

public class CylinderObject extends MyObject {
    public CylinderObject(int size, int mass, int x, int y) {
        super(size, mass, x, y);
    }

    protected void drawShape(Graphics g) {
        try {
            Image img = ImageIO.read(getClass().getResource("../../assets/bugatti.png"));
            img = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
            g.drawImage(img, upperLeft.x, upperLeft.y, null);
        } catch (Exception e) {
            System.out.println("Cannot find image");
            g.fillOval(upperLeft.x, upperLeft.y, size, size);
        }
    }
}
