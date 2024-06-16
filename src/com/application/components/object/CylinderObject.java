package com.application.components.object;

import java.awt.*;
import java.net.URL;

public class CylinderObject extends MyObject {
    public CylinderObject(int mass, int width, int height, URL imgUrl) {
        super(mass, width, height, imgUrl);
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, getWidth() / 2 - width / 2, 0, null);
    }
}
