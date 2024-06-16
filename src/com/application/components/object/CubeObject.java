package com.application.components.object;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class CubeObject extends MyObject {
    public CubeObject(int mass, int width, int height, URL imgUrl) {
        super(mass, width, height, imgUrl);
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, getWidth() / 2 - width / 2, 0, null);
    }
}
