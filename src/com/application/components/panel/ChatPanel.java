package com.application.components.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import com.application.genai.GenAI;

public class ChatPanel extends JPanel {
    private static JPanel chatPanel = new JPanel();
    private static JPanel inputPanel = new JPanel();
    private static ArrayList<Map<String, String>> messages = new ArrayList<>();

    public ChatPanel() {
        // Create a panel for the chat area
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a scroll pane for the chat area
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Create a text area for chat messages (initially empty)
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatPanel.add(chatArea);

        // Create a panel for input area
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a text field for user input
        JTextField inputField = new JTextField();
        inputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, inputField.getPreferredSize().height));

        // Create a button for sending messages
        JButton sendButton = new JButton("Send");

        // Add action listener to the send button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText().trim();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n");
                    var m = new HashMap<String, String>();
                    m.put("role", "user");
                    m.put("text", message);
                    messages.add(m);
                    GenAI.generateContent(messages);

                    inputField.setText("");
                }
            }
        });

        // Add components to the input panel
        inputPanel.add(inputField);
        inputPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Space between input field and button
        inputPanel.add(sendButton);
    }

    public JPanel getChatPanel() {
        return chatPanel;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public static void addMessage(String resp) {
        Pattern msgPattern = Pattern.compile("(?<=\"text\":\\s\")([\\w\\W]+)(?=\".+}.+],.+\"role\")");

        Matcher matcher = msgPattern.matcher(resp);

        if (matcher.find()) {
            System.out.println("\ncode: " + matcher.group(0).replace("\n", ""));
        } else {
            System.out.println("No match found");
        }
    }
}