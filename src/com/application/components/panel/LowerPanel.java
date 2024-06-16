package com.application.components.panel;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.application.components.controller.Controller;
import com.application.components.controller.Physic;
import com.application.components.controller.UserInput;
import com.application.components.object.CubeObject;
import com.application.components.object.CylinderObject;
import com.application.components.object.MyObject;

public class LowerPanel extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(13, 17, 23);

    public LowerPanel() {
        setLayout(new GridLayout(1, 2));
        setBackground(BACKGROUND_COLOR);
        JPanel leftPane = new JPanel();
        leftPane.setBackground(BACKGROUND_COLOR);
        JPanel rightPane = new JPanel();
        rightPane.setBackground(BACKGROUND_COLOR);
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setPreferredSize(new Dimension(300, 100));

        rightPane.add(Box.createVerticalGlue());
        rightPane.add(creatPanel("Mass", 1000, 0, 0, 1.0f));
        rightPane.add(creatPanel("Kinetic Friction", 100, 0, 0, 0.01f));
        rightPane.add(creatPanel("Static Friction", 100, 0, 0, 0.01f));
        rightPane.add(creatPanel("Actor 1", 1000, -1000, 0, 1.0f));
        rightPane.add(creatPanel("Actor 2", 1000, -1000, 0, 1.0f));

        JButton startButton = new JButton("Start");
        JButton resetButton = new JButton("Reset");
        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> {
            if (pauseButton.getText().equals("Pause")) {
                pauseButton.setText("Resume");
                Controller.pauseTimer();
            } else {
                pauseButton.setText("Pause");
                Controller.resumeTimer();
            }
        });
        startButton.addActionListener(e -> {
            Controller.startTimer();
        });
        resetButton.addActionListener(e -> {
            Controller.resetTimer();
        });
        leftPane.add(startButton);
        leftPane.add(resetButton);
        leftPane.add(pauseButton);
        JButton shapeButton = new JButton("Cylinder");
        shapeButton.addActionListener(e -> {
            if (Controller.getObj() instanceof CubeObject) {
                try {
                    MyObject myObj = new CylinderObject(50, 500, 150,
                            getClass().getResource("../../assets/bugatti.png"));
                    Controller.getObjPanel().setObject(myObj);
                } catch (Exception ex) {
                    System.out.println("Cannot find image");
                }
                shapeButton.setText("Cube");
            } else if (Controller.getObj() instanceof CylinderObject) {
                try {
                    MyObject myObj = new CubeObject(50, 500, 150,
                            getClass().getResource("../../assets/container.jpg"));
                    Controller.getObjPanel().setObject(myObj);
                } catch (Exception ex) {
                    System.out.println("Cannot find image");
                }
                shapeButton.setText("Cyliner");
            }
        });
        leftPane.add(shapeButton);
        add(leftPane);
        add(rightPane);
    }

    private JPanel creatPanel(String label, int maxVal, int minVal, int initVal, float step) {
        JPanel panel = new JPanel();
        panel.setBackground(BACKGROUND_COLOR);
        panel.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel lb = new JLabel(label);
        lb.setForeground(Color.WHITE);
        JSlider slider = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, initVal);
        JTextField textField = new JTextField(5);
        textField.setText(slider.getValue() + "");
        textField.addActionListener(e -> {
            try {
                Float value = Float.parseFloat(textField.getText());
                slider.setValue((int) (value / step));
            } catch (NumberFormatException ex) {
                textField.setText(slider.getValue() * step + "");
            }
        });
        slider.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            float value = source.getValue() * step;
            textField.setText(String.format("%.3f", value));
            UserInput.set(label, value + "");
            Controller.getObj().setAcc(Physic.calAcc());
        });
        panel.add(lb);
        panel.add(slider);
        panel.add(textField);
        return panel;
    }
}
