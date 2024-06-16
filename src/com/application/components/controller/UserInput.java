package com.application.components.controller;

import java.util.HashMap;
import java.util.Map;

public class UserInput {
    private static Map<String, String> userInput = new HashMap<>(
            Map.of("Mass", "0", "Actor 1", "0", "Actor 2", "0", "Friction", "0"));

    public static void set(String key, String value) {
        userInput.put(key, value);
    }

    public static String get(String key) {
        return userInput.get(key);
    }
}
