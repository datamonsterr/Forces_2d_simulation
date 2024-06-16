package com.application.components.panel;

import java.awt.Color;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.application.components.controller.Controller;
import com.application.components.object.CubeObject;
import com.application.components.object.CylinderObject;
import com.application.components.object.MyObject;

public class ObjectPanel extends JPanel {
    private MyObject obj;

    public ObjectPanel() {
        try {
            Image img = ImageIO.read(getClass().getResource("../../assets/bugatti.png"));
            obj = new CubeObject(50, 500, 150, img);
            Controller.setObj(obj);
            setBackground(new Color(0, 0, 0, 0));
            add(obj);
        } catch (Exception e) {
            System.out.println("Cannot find image");
        }
    }

    public MyObject getObject() {
        return obj;
    }
}
