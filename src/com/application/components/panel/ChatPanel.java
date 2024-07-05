package com.application.components.panel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

import com.application.components.controller.Controller;
import com.application.components.controller.UserInput;
import com.application.components.object.MyObject;
import com.application.genai.GenAI;

public class ChatPanel extends JPanel {
    private static String CONTEXT = "I have an app that stimulates the Newton's law motion. It takes Mass of object, first actor force, second actor force and kinetic and static friction coeficient as inputs. You are a chatbot that converts natural language into a map with 'Mass','Actor 1','Actor 2','Kinetic Friction','Static Friction' keys. 'Actor 1' and 'Actor 2' are two actor's force range from -5000 to 5000. There are constraints: Mass must be greater than 0, 'Kinetic Friction' must be smaller than 'Static Friction' and both are smaller than 1, greater than 0. If there are any constraints violated, you should notify user and tell them to change it. If the map missing some fileds, you should ask for clarification. If all data is filled with good value, you can answer 'All inputs are satisfied. Let's start'. Default value for a map: {Mass: 0, Actor 1: 0, Actor 2: 0, Kinetic Friction: 0.0, Static Friction: 0.0}. Your output must follow the format: Data(<the  map here>)#. Msg(<your message other than map here>)# (the # symbol is for ending so it must be included). User's request will be in form of Data(<user data>)# and Msg(<user's message>)#. If user want to modify the data's value, you should return the modified data and a message show what have you modified. Some knowledge in Knowledge() will be provided you should use that to answer related question. If user's message are not relevant to the context, you can answer it normally. Can you help me with this?.";
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
                    MyObject obj = Controller.getObj();
                    StringBuilder knowledge = new StringBuilder();
                    knowledge.append("velocity: " + obj.getCurVelocity(Controller.getTime()) + ", ")
                            .append("position: " + obj.getPosition() + ", ")
                            .append("acceleration: " + obj.getAcc() + ", ")
                            .append("friction force:" + obj.getFrictionForce() + ", ")
                            .append("scene: we are in a street at night under a cloudy sky with a big moon");

                    chatArea.append("You: " + message + "\n");
                    var m = new HashMap<String, String>();
                    m.put("role", "user");
                    m.put("text",
                            UserInput.getAll() + "#. Msg(" + message + ")#" + ". Knowledge(" + knowledge.toString()
                                    + ")#.");
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
        Pattern msgPattern = Pattern.compile("(?<=Msg\\()(.+?)(?=\\)#)", Pattern.DOTALL);
        Pattern dataPattern = Pattern.compile("(?<=Data\\()(.+?)(?=\\)#)", Pattern.DOTALL);
        Pattern textPattern = Pattern.compile("(?<=\"text\":\\s\")(.+?)(?=\")", Pattern.DOTALL);
        Matcher msgMatcher = msgPattern.matcher(resp);
        Matcher dataMatcher = dataPattern.matcher(resp);
        Matcher textMatcher = textPattern.matcher(resp);

        if (textMatcher.find()) {
            Map<String, String> m = new HashMap<>();
            m.put("role", "model");
            m.put("text", textMatcher.group(0));
            messages.add(m);
            if (dataMatcher.find()) {
                var output = dataMatcher.group(0);
                var items = output.split(",");
                for (var item : items) {
                    var pair = item.split(":");
                    var m1 = pair[0].trim();
                    var m2 = pair[1].trim();
                    if (m2.indexOf('.') != -1) {
                        UserInput.setVal(m1, Double.parseDouble(m2));
                    } else {
                        UserInput.setVal(m1, Integer.parseInt(m2));
                    }
                }
            }
            if (msgMatcher.find()) {
                chatArea.append("AI: " + msgMatcher.group(0) + "\n");
            }
        } else {
            System.out.println("No text found");
        }

    }
}