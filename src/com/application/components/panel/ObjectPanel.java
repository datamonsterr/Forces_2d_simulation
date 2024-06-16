package com.application.components.panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.application.components.controller.Controller;
import com.application.components.object.CubeObject;
import com.application.components.object.MyObject;

public class ObjectPanel extends JPanel {
    private MyObject obj;

    public ObjectPanel() {
        setBackground(new Color(0, 0, 0, 0));
        try {
            obj = new CubeObject(50, 500, 150, getClass().getResource("../../assets/container.jpg"));
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
        revalidate();
        repaint();
        Controller.getUpperPanel().repaint();
        obj = newObj;
        Controller.setObj(obj);
    }
}
