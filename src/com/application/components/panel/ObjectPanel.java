package com.application.components.panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.application.components.controller.Controller;
import com.application.components.object.*;

public class ObjectPanel extends JPanel {
    private MyObject obj;

    public ObjectPanel() {
        setBackground(new Color(0, 0, 0, 0));
        try {
            obj = new CubeObject(50, 150, getClass().getResource("../../assets/container.jpg"));
            Controller.setObj(obj);
        } catch (Exception e) {
            System.out.println("Cannot find image");
        }
        add(obj);
    }

    public MyObject getObject() {
        return obj;
    }

    public void setObject(MyObject newObj) {
        remove(obj);
        add(newObj);
        repaint();
        obj = newObj;
        Controller.setObj(obj);
        Controller.getUpperPanel().repaint();
    }
}
