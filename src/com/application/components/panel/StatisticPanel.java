package com.application.components.panel;

import java.awt.*;
import javax.swing.*;
import com.application.components.controller.Controller;

public class StatisticPanel extends JPanel {
    public StatisticPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.SrcOver);
        g2d.setColor(new Color(122, 122, 122, 120));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString(String.format("Velocity: %.2f", Controller.getVelocity()), 10, 25);
        g.drawString(String.format("Accerleration: %.2f", Controller.getObj().getAcc()), 10, 50);
        g.drawString(String.format("Position: %.2f", Controller.getObj().getPosition(Controller.getTime())), 10,
                75);
    }
}
