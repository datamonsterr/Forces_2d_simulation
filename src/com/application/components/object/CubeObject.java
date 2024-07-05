package com.application.components.object;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.imageio.ImageIO;

public class CubeObject extends MyObject {
    public CubeObject(int mass, int height, URL imgUrl) {
        super(mass, height, imgUrl);
    }

    protected void drawShape(Graphics g, Image img) {
        g.drawImage(img, getWidth() / 2 - calWidth(img.getWidth(null), img.getHeight(null), height) / 2, 0, null);
    }

    protected void drawActor(Graphics g) {
        try {
            int direction = this.getAcc() < 0 ? -1 : 1;
            String imgName = direction == 1 ? "hulk.png" : "hulk_flip.png";
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
