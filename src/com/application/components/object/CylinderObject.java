package com.application.components.object;

import java.awt.*;

public class CylinderObject extends MyObject {
    public CylinderObject(int mass, int width, int height, Image img) {
        super(mass, width, height, img);
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, 0, 0, null);
    }
}
