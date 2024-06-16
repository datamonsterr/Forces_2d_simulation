package com.application.components.panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UpperPanel extends JLayeredPane implements ActionListener {
    private JPanel objectPane = new ObjectPanel();
    private JPanel topPane = new StatisticPanel();
    private JPanel defaultPane = new BackgroundPanel();
    private SurfacePanel surfacePane = new SurfacePanel();

    private static final int FPS = 60;
    private static Timer timer;
    private static double currentTime = 0.0d; // measure in seconds

    public UpperPanel() {
        setBackground(Color.BLACK);

        add(defaultPane, JLayeredPane.DEFAULT_LAYER);
        add(surfacePane, JLayeredPane.PALETTE_LAYER);
        add(objectPane, JLayeredPane.POPUP_LAYER);
        add(topPane, JLayeredPane.POPUP_LAYER);

        timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentTime += 1.0d / FPS;
        topPane.repaint();
        surfacePane.updatePosition(10.0d, currentTime);
        surfacePane.repaint();
    }

    @Override
    public void doLayout() {
        super.doLayout();
        defaultPane.setBounds(0, 0, getWidth(), getHeight());
        objectPane.setBounds(0, getHeight() - 180, getWidth(), 150);
        surfacePane.setBounds(0, getHeight() - 80, getWidth(), 80);
        topPane.setBounds(100, 150, 200, 200);
    }

    public static double getTime() {
        return currentTime;
    }

    public static void pause() {
        timer.stop();
    }

    public static void resume() {
        timer.start();
    }
}