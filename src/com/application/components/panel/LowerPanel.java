package com.application.components.panel;

import java.awt.*;
import javax.swing.*;
import com.application.components.controller.*;
import com.application.components.object.*;

public class LowerPanel extends JPanel {
    private final Color BACKGROUND_COLOR = new Color(13, 17, 23);

    public LowerPanel() {
        setLayout(new GridLayout(1, 2));
        setBackground(BACKGROUND_COLOR);
        JPanel leftPane = new JPanel();
        leftPane.setBackground(BACKGROUND_COLOR);
        leftPane.setLayout(new BorderLayout());

        JPanel rightPane = new JPanel();
        rightPane.setBackground(BACKGROUND_COLOR);
        rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
        rightPane.setPreferredSize(new Dimension(300, 100));

        rightPane.add(Box.createVerticalGlue());
        rightPane.add(creatPanel("Mass", 1000, 0, 0, 1.0f));
        rightPane.add(creatPanel("Static Friction", 100, 0, 0, 0.01f));
        rightPane.add(creatPanel("Kinetic Friction", 100, 0, 0, 0.01f));
        rightPane.add(creatPanel("Actor 1", 5000, -5000, 0, 1.0f));
        rightPane.add(creatPanel("Actor 2", 5000, -5000, 0, 1.0f));

        JPanel btnPanel = new JPanel();
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
            Controller.getObj().setPosition(0);
            Controller.getObj().setVelocity(0);
            Controller.resetTimer();
        });
        btnPanel.add(startButton);
        btnPanel.add(resetButton);
        btnPanel.add(pauseButton);
        JButton shapeButton = new JButton("Cylinder");
        shapeButton.addActionListener(e -> {
            if (Controller.getObj() instanceof CubeObject) {
                JDialog dialog = new JDialog();
                dialog.setLayout(new FlowLayout());
                dialog.setSize(300, 100);
                dialog.setLocationRelativeTo(null);
                dialog.add(new JLabel("Radius: "));
                JTextField radiusField = new JTextField(10);
                dialog.add(radiusField);
                JButton okButton = new JButton("OK");
                okButton.addActionListener(e1 -> {
                    try {
                        MyObject myObj = new CylinderObject(50, 150,
                                getClass().getResource("../../assets/ball.png"),
                                Integer.parseInt(radiusField.getText()));
                        Controller.getObjPanel().setObject(myObj);
                    } catch (Exception ex) {
                        System.out.println("Cannot find image");
                    }
                    dialog.dispose();
                    shapeButton.setText("Cube");
                });
                dialog.add(okButton);
                dialog.setVisible(true);
            } else if (Controller.getObj() instanceof CylinderObject) {
                try {
                    MyObject myObj = new CubeObject(50, 150,
                            getClass().getResource("../../assets/container.jpg"));
                    Controller.getObjPanel().setObject(myObj);
                } catch (Exception ex) {
                    System.out.println("Cannot find image");
                }
                shapeButton.setText("Cyliner");
            }
        });
        btnPanel.add(shapeButton);

        ChatPanel chatBotPane = new ChatPanel();
        leftPane.add(chatBotPane.getChatPanel(), BorderLayout.CENTER);
        leftPane.add(chatBotPane.getInputPanel(), BorderLayout.SOUTH);
        leftPane.add(btnPanel, BorderLayout.NORTH);

        add(leftPane);
        add(rightPane);
    }

    private JPanel creatPanel(String label, int maxVal, int minVal, int initVal, float step) {
        JPanel panel = new JPanel();
        JPanel innerPanel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        innerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(BACKGROUND_COLOR);
        innerPanel.setBackground(BACKGROUND_COLOR);
        panel.setFont(new Font("monospace", Font.PLAIN, 20));
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
            Controller.getUpperPanel().repaint();
            Controller.getObjPanel().repaint();
            Controller.getObj().revalidate();
            Controller.getObj().repaint();
        });
        innerPanel.add(slider);
        innerPanel.add(textField);

        panel.add(Box.createHorizontalStrut(100));
        panel.add(lb);
        panel.add(Box.createHorizontalStrut(100 - label.length() * 5));
        panel.add(innerPanel);

        return panel;
    }
}
