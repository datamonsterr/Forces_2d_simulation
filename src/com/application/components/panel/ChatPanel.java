package com.application.components.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import com.application.components.controller.UserInput;
import com.application.genai.GenAI;

public class ChatPanel extends JPanel {
    private static String CONTEXT = "I have an app that stimulates the Newton's law motion. It takes Mass of object, first actor force, second actor force and kinetic and static friction coeficient as inputs. You are a chatbot that converts natural language into a map with 'Mass','Actor 1','Actor 2','Kinetic Friction','Static Friction' keys. 'Actor 1' and 'Actor 2' are two actor's force range from -5000 to 5000. There are constraints: Mass must be greater than 0, 'Kinetic Friction' must be smaller than 'Static Friction' and both are smaller than 1, greater than 0. If user's question are not relevant to the context, you can answer normally. If there are any constraints violated, you can answer with the constraints. If user's question is not clear, you should ask for clarification and DO NOT return an object. If user's question is clear, you can answer with the map first then a message 'All inputs are satisfied' at the end, nothing else is necessary.You will be passed some user's data to process to, the form is similar with the your output, this is for some extra commands from user. First, if user chat [name of actor 1] push a little bit harder, you should increase 'Actor 1' an appropriate ammount of force and return the son map with a message that tells 'increase first actor x(N)'.Can you help me with this?. Default value for a map: {Mass: 0, Actor 1: 0, Actor 2: 0, Kinetic Friction: 0.0, Static Friction: 0.0}. Your output must follow the format: Data(<the  map here>). Msg(<your message other than map here>).";
    private static JPanel chatPanel = new JPanel();
    private static JPanel inputPanel = new JPanel();
    private static JTextArea chatArea = new JTextArea();
    private static ArrayList<Map<String, String>> messages = new ArrayList<>();
    private static JScrollPane scrollPane = new JScrollPane(chatPanel);

    public ChatPanel() {
        var initM = new HashMap<String, String>();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        initM.put("role", "user");
        initM.put("text", CONTEXT);
        messages.add(initM);

        // Create a panel for the chat area
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        chatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
                    m.put("text", UserInput.getAll() + ". Msg(" + message + ")");
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

    public JScrollPane getChatPanel() {
        return scrollPane;
    }

    public JPanel getInputPanel() {
        return inputPanel;
    }

    public static void addMessage(String resp) {
        resp = resp.replace("\\n", "");
        Pattern msgPattern = Pattern.compile("(?<=Msg\\()(.+?)(?=\\))", Pattern.DOTALL);
        Pattern dataPattern = Pattern.compile("(?<=Data\\()(.+?)(?=\\))", Pattern.DOTALL);
        Pattern textPattern = Pattern.compile("(?<=\"text\":\\s\")(.+?)(?=\")", Pattern.DOTALL);
        Matcher msgMatcher = msgPattern.matcher(resp);
        Matcher dataMatcher = dataPattern.matcher(resp);
        Matcher textMatcher = textPattern.matcher(resp);

        System.out.println(resp);

        if (textMatcher.find()) {
            Map<String, String> m = new HashMap<>();
            m.put("role", "model");
            m.put("text", textMatcher.group(0));
            System.out.println("Debug: " + textMatcher.group(0));
            messages.add(m);
            if (dataMatcher.find()) {
                var output = dataMatcher.group(0);
                var items = output.split(",");
                for (var item : items) {
                    var pair = item.split(":");
                    try {
                        UserInput.setVal(pair[0].trim(), Integer.parseInt(pair[1].trim()));
                    } catch (NumberFormatException e) {
                        try {
                            UserInput.setVal(pair[0].trim(), Double.parseDouble(pair[1].trim()));
                        } catch (Exception e2) {
                            System.out.println("Err:" + pair[0].trim() + " " + pair[1].trim());
                        }
                    }
                }
            }
            if (msgMatcher.find()) {
                chatArea.append("AI: " + msgMatcher.group(0) + "\n");
            }
        } else {
            chatArea.append("AI: " + textMatcher.group(0) + "\n");
        }

    }
}