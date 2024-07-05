package com.application.components.panel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.application.components.controller.Controller;
import com.application.components.controller.Physic;
import com.application.components.controller.UserInput;

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
        Controller.getObj().setPosition(Controller.getObj().getPosition()
                + Controller.getObj().getCurVelocity(Controller.getTime()) / 1000 * 60);
        topPane.repaint();
        repaint();
        objectPane.repaint();
        objectPane.getObject().revalidate();
        objectPane.getObject().repaint();
        surfacePane.repaint();
        if (Physic.getSF() < Physic.getKF()) {
            Controller.pauseTimer();
            JPopupMenu popup = new JPopupMenu();
            JButton button = new JButton("OK");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.setVisible(false);
                    UserInput.setVal("Static Friction", 0);
                    UserInput.setVal("Kinetic Friction", 0);
                }
            });
            popup.add(new JLabel("Error: Static Friction is less than Kinetic Friction!"));
            popup.add(new JLabel("Please set a different friction value and resume."));
            popup.add(button);
            popup.show(this, getWidth() / 2, getHeight() / 2);
            popup.setVisible(true);
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        defaultPane.setBounds(0, 0, getWidth(), getHeight());
        objectPane.setBounds(0, getHeight() - 180, getWidth(), 150);
        surfacePane.setBounds(0, getHeight() - 80, getWidth(), 80);
        topPane.setBounds(100, 150, 250, 200);
    }

}