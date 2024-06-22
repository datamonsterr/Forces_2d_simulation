package com.application.genai;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import com.application.components.panel.ChatPanel;

public class GenAI {
    private final static String API_KEY = "AIzaSyBGjPZyyuIK3dxdsWEjXLRhXeWPmdIr0Co";

    public static void generateContent(List<Map<String, String>> messages) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI uri = new URI(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                            + API_KEY);

            StringBuffer reqBody = new StringBuffer();
            reqBody.append("{\"contents\": [");

            for (var message : messages) {
                reqBody.append("{");
                reqBody.append("\"role\": \"" + message.get("role") + "\",");
                reqBody.append("\"parts\": [");
                reqBody.append("{\"text\": ");
                reqBody.append("\"" + message.get("text") + "\"}");
                reqBody.append("]},");
            }
            reqBody.append("]}");
            System.out.println("Debug: " + reqBody.toString() + "\n");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(reqBody.toString())).build();

            client.sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(ChatPanel::addMessage)
                    .join();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}