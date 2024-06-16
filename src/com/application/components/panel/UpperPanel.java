package com.application.components.panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.application.components.controller.Controller;

public class UpperPanel extends JLayeredPane implements ActionListener {
    private ObjectPanel objectPane = new ObjectPanel();
    private StatisticPanel topPane = new StatisticPanel();
    private JPanel defaultPane = new BackgroundPanel();
    private SurfacePanel surfacePane = new SurfacePanel();

    public UpperPanel() {
        setBackground(Color.BLACK);

        add(defaultPane, JLayeredPane.DEFAULT_LAYER);
        add(surfacePane, JLayeredPane.PALETTE_LAYER);
        add(objectPane, JLayeredPane.POPUP_LAYER);
        add(topPane, JLayeredPane.POPUP_LAYER);
        Controller.setObjPanel(objectPane);
        Controller.setUpperPanel(this);
        Controller.createTimer(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Controller.tick();
        topPane.repaint();
        surfacePane.updatePosition(Controller.getTime());
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

}