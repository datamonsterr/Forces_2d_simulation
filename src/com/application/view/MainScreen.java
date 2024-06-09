package com.application.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.application.components.CubeObject;
import com.application.components.CylinderObject;
import com.application.components.Force;
import com.application.components.MyObject;
import com.application.components.MyObjectPanel;
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
        emptyPanel.setBackground(MyObjectPanel.BACKGROUND_COLOR);

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
                Integer firstActor = Integer.parseInt(userData.get("First Actor").getText());
                Integer secondActor = Integer.parseInt(userData.get("Second Actor").getText());
                Float frictionCoefficient = Float.parseFloat(userData.get("Friction Coefficient").getText());
                String shape = userInput.getShape();
                float totalForce = firstActor - secondActor;
                String direction = totalForce > 0 ? "right" : "left";

                MyObject obj;
                float friction;
                if (shape == "Cube") {
                    obj = new CubeObject(size, mass);
                    friction = frictionCoefficient * mass * 10;
                } else {
                    obj = new CylinderObject(size, mass);
                    friction = frictionCoefficient * mass * 10 / 3;
                }
                totalForce = Math.abs(totalForce) - friction;

                Force firstActorForce = new Force(obj, "Actor 1", firstActor, 1, new Point(0, 0));
                Force secondActorForce = new Force(obj, "Actor 2", secondActor, -1, new Point(0, 0));
                Force frictionForce = new Force(obj, "Friction", (int) friction, -1, new Point(0, obj.size / 2));
                MyObjectPanel objPanel = new MyObjectPanel() {
                    @Override
                    protected void paintShape(Graphics g) {
                        obj.draw(g);
                        firstActorForce.apply(g);
                        secondActorForce.apply(g);
                        frictionForce.apply(g);
                    }
                };

                float acceleration = totalForce / mass;
                if (direction == "left") {
                    acceleration = -acceleration;
                }

                if (totalForce < 0) {
                    acceleration = 0;
                    surface.setAcceralation(acceleration);
                } else {
                    surface.setAcceralation(acceleration);
                }

                simulationPanel.removeAll();
                simulationPanel.revalidate();
                simulationPanel.add(objPanel);
                simulationPanel.add(surface);
                simulationPanel.repaint();
            }
        });

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (surface.checkPaused()) {
                    surface.resume();
                    pauseButton.setText("Pause");
                } else {
                    surface.pause();
                    pauseButton.setText("Resume");
                }
            }
        });
        container.add(simulationPanel);
        container.add(userInput);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setMaximumSize(new Dimension(1200, 50));
        buttonPanel.add(button);
        buttonPanel.add(pauseButton);

        container.add(buttonPanel);

        pack();

        setSize(1200, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
