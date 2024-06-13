package com.application.components;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

public class UserInput extends JPanel {
    private Map<String, JTextField> userInputMap = new HashMap<>();
    private JRadioButton cubeRadio, cylinderRadio;

    public UserInput() {
        setLayout(new GridLayout(getComponentCount() / 2, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("User Input"));

        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ButtonGroup shapeGroup = new ButtonGroup();
        cubeRadio = new JRadioButton("Cube");
        cylinderRadio = new JRadioButton("Cylinder");
        shapePanel.add(new JLabel("Shape:"));
        shapeGroup.add(cubeRadio);
        shapeGroup.add(cylinderRadio);
        shapePanel.add(cubeRadio);
        shapePanel.add(cylinderRadio);
        add(shapePanel);

        createInputText("Mass");
        createInputText("Size");
        createInputText("First Actor");
        createInputText("Second Actor");
        createInputText("Friction Coefficient");

        setSize(1200, 300);
        setMaximumSize(new Dimension(1200, 200));
    }

    private void createInputText(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField textField = new JTextField(20);
        userInputMap.put(label, textField);
        panel.add(new JLabel(label + ":"));
        panel.add(textField);
        add(panel);
    }

    public void setShape(String shape) {
        if (shape.toLowerCase().equals("cube")) {
            cubeRadio.setSelected(true);
        } else {
            cylinderRadio.setSelected(true);
        }
    }

    public String getShape() {
        return cubeRadio.isSelected() ? "Cube" : "Cylinder";
    }

    public Map<String, JTextField> getUserInputMap() {
        return userInputMap;
    }
}
