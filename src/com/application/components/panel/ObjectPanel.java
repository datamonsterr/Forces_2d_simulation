package com.application.components.panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.application.components.object.CubeObject;
import com.application.components.object.MyObject;

public class ObjectPanel extends JPanel {
    private MyObject obj;

    public ObjectPanel() {
        obj = new CubeObject(150, 50, getWidth() / 2, getHeight() - 150);
        obj.setMaximumSize(new Dimension(getWidth(), getHeight()));
        setBackground(new Color(0, 0, 0, 0));
        add(obj);
    }
}
