package com.application.components.panel;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BackgroundPanel extends JPanel {

    public BackgroundPanel() {
        setBackground(Color.YELLOW);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image backgroundImage = ImageIO.read(getClass().getResource("../../assets/bg.jpg"));
            backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT);
            g.drawImage(backgroundImage, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
