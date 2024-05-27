package com.application.components;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class UserInput extends JPanel {
    private Map<String, JTextField> userInputMap = new HashMap<>();
    private JButton button;

    public UserInput() {
        setLayout(new GridLayout(getComponentCount() / 2, 2, 10, 10));
        setBorder(BorderFactory.createTitledBorder("User Input"));

        button = new JButton("Submit");
        button.addActionListener(onClick);

        createInputText("Mass");
        createInputText("First Actor");
        createInputText("Second Actor");
        createInputText("Size");
        add(button);

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

    protected ActionListener onClick = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (var entry : userInputMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().getText());
            }
        }
    };
}
