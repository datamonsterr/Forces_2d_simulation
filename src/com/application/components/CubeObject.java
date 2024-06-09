package com.application.components;

import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;

public class CubeObject extends MyObject {
    public CubeObject(int size, int mass) {
        super(size, mass);
    }

    protected void drawShape(Graphics g) {
        try {
            Image img = ImageIO.read(getClass().getResource("../assets/sand.jpg"));
            img = img.getScaledInstance(size, size, Image.SCALE_DEFAULT);
            g.drawImage(img, upperLeft.x, upperLeft.y, null);
        } catch (Exception e) {
            System.out.println("Cannot find image");
            g.fillRect(upperLeft.x, upperLeft.y, size, size);
        }
    }
}
