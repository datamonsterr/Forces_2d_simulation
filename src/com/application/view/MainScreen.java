package com.application.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.application.components.CubeObject;
import com.application.components.CylinderObject;
import com.application.components.MyObject;
import com.application.components.Surface;
import com.application.components.UserInput;

public class MainScreen extends JFrame {
    private Container container = getContentPane();

    public MainScreen() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        Surface surface = new Surface();
        JPanel simulationPanel = new JPanel();
        JPanel emptyPanel = new JPanel();

        emptyPanel.setSize(1200, 300);
        emptyPanel.setMaximumSize(new Dimension(1200, 300));
        emptyPanel.setBackground(MyObject.BACKGROUND_COLOR);

        UserInput userInput = new UserInput();

        simulationPanel.setLayout(new BoxLayout(simulationPanel, BoxLayout.Y_AXIS));
        simulationPanel.add(emptyPanel);
        simulationPanel.add(surface);

        JButton button = new JButton("Submit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var userData = userInput.getUserInputMap();
                Integer mass = Integer.parseInt(userData.get("Mass").getText());
                Integer size = Integer.parseInt(userData.get("Size").getText());
                Float firstActor = Float.parseFloat(userData.get("First Actor").getText());
                Float secondActor = Float.parseFloat(userData.get("Second Actor").getText());
                Float frictionCoefficient = Float.parseFloat(userData.get("Friction Coefficient").getText());
                String shape = userInput.getShape();
                Float totalForce = firstActor - secondActor;
                String direction = totalForce > 0 ? "right" : "left";

                MyObject obj;
                if (shape == "Cube") {
                    obj = new CubeObject(size, mass);
                    totalForce = Math.abs(totalForce) - frictionCoefficient * mass * 10;
                } else {
                    obj = new CylinderObject(size, mass);
                    totalForce = Math.abs(totalForce) - frictionCoefficient * mass * 10 / 3;
                }

                float acceleration = totalForce / mass;
                if (direction == "left") {
                    acceleration = -acceleration;
                }

                if (totalForce < 0) {
                    acceleration = 0;
                    surface.setAcceralation(acceleration);
                    obj.setDirection("none");
                } else {
                    surface.setAcceralation(acceleration);
                    obj.setDirection(direction);
                }
                System.out.println(acceleration);

                simulationPanel.removeAll();
                simulationPanel.revalidate();
                simulationPanel.add(obj);
                simulationPanel.add(surface);
                simulationPanel.repaint();
            }
        });
        container.add(simulationPanel);
        container.add(userInput);
        container.add(button);

        pack();

        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
