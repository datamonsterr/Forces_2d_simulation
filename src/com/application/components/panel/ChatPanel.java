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
    private static String CONTEXT = "I have an app that stimulates the Newton's law motion. It takes mass, shape of object, first actor force, second actor force and kinetic and static friction coeficient as inputs. Object shape is either cube or cylinder. You are a chatbot that converts natural language into json with mass,shape,firstActor,secondActor,kineticFriction,staticFriction keys. There are constraints: mass must be greater than 0, kineticFriction must be smaller than staticFriction and both are smaller than 1, greater than 0. If user's question are not relevant to the context, you can answer normally. If there are any constraints violated, you can answer with the constraints. If user's question is not clear, you can ask for clarification. If user's question is clear, you can answer with the json first then a message 'All inputs are sastified' at the end, nothing else is necessary. You will be passed some json data to process to, the form is similar with the your output, this is for some extra commands from user. First, if user chat [name of actor 1] push a little bit harder, you should increase firstActor an appropriate ammount of force and return the json with a message that tells 'increase first actor x(N)'.Can you help me with this?";
    private static JPanel chatPanel = new JPanel();
    private static JPanel inputPanel = new JPanel();
    private static JTextArea chatArea = new JTextArea();
    private static ArrayList<Map<String, String>> messages = new ArrayList<>();

    public ChatPanel() {
        var initM = new HashMap<String, String>();
        initM.put("role", "user");
        initM.put("text", CONTEXT);
        messages.add(initM);

        // Create a panel for the chat area
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a scroll pane for the chat area
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Create a text area for chat messages (initially empty)
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
                    new Thread(
                            () -> {
                                GenAI.generateContent(messages);
                            }).start();

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
        Pattern msgPattern = Pattern.compile("(?<=\"text\":\\s\")([\\w\\W]+?)(?=\")", Pattern.DOTALL);
        Matcher matcher = msgPattern.matcher(resp);

        if (matcher.find()) {
            System.out.println(resp);
            var ans = matcher.group(0).replace("\\n", "");
            Map<String, String> m = new HashMap<>();
            m.put("role", "model");
            m.put("text", ans);
            messages.add(m);
            chatArea.append("AI: " + ans + "\n");
        } else {
            System.out.println(resp);
            System.out.println("No match found");
        }
    }
}