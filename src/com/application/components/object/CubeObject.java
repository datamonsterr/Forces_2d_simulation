package com.application.components.object;

import java.awt.Graphics;
import java.awt.Image;

public class CubeObject extends MyObject {
    public CubeObject(int mass, int width, int height, Image img) {
        super(mass, width, height, img);
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, 0, 0, null);
    }
}
