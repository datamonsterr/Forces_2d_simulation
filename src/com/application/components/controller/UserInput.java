package com.application.components.controller;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JSlider;

public class UserInput {
    private static Map<String, JSlider> userInput = new HashMap<>();
    private static Map<String, Double> userInputStep = new HashMap<>();

    public static void set(String key, JSlider slider, double step) {
        userInput.put(key, slider);
        userInputStep.put(key, step);
    }

    public static void setVal(String key, int value) {
        try {
            userInput.get(key).setValue(value);
        } catch (Exception e) {
            System.out.println("Setting value for " + key + " to " + value);
        }
    }

    public static void setVal(String key, double value) {
        try {
            value /= userInputStep.get(key);
            int newValue = Integer.parseInt(String.valueOf(Math.round(value)));
            userInput.get(key).setValue(newValue);
        } catch (Exception e) {
            System.out.println("Setting value for " + key + " to " + value);
        }
    }

    public static double get(String key) {
        return userInput.get(key).getValue() * userInputStep.get(key);
    }

    public static String getAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("Data(");
        for (Map.Entry<String, JSlider> entry : userInput.entrySet()) {
            sb.append(entry.getKey()).append(": ")
                    .append(entry.getValue().getValue() * userInputStep.get(entry.getKey())).append(",");
        }
        sb.append(")");
        return sb.toString();
    }
}
