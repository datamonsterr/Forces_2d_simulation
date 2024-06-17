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
    private static int prev_id = 0;
    public final static String API_KEY = "AIzaSyBGjPZyyuIK3dxdsWEjXLRhXeWPmdIr0Co";
    private static String mass;
    private static String size;
    private static String shape;
    private static String firstActor;
    private static String secondActor;
    private static String frictionCoefficient;

    public static void generateContent(String input) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            URI uri = new URI(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key="
                            + API_KEY);
            String requestBody = """
                       {
                       "contents": [
                       {    "role": "user",
                           "parts":[{"text": "I have an app that stimulates the Newton's law motion. It takes mass, size, shape of object, first actor force, second actor force and friction coeeficient as inputs. Object shape is either cube or cylinder. You are a chatbot that converts natural language into json with mass,size,shape,firstActor,secondActor,frictionCoefficient keys. Sometimes you have to do some calculations or derive some values from the given inputs.Just return the json no other content is included. Example output: mass: 10, size: 5, shape: cube, firstActor: 10, secondActor: 5, frictionCoefficient: 0.5. Convert the following text to json: %s"},],}
                           ]
                       }
                    """;
            String formattedRequestBody = String.format(requestBody, input);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(formattedRequestBody)).build();

            client.sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(GenAI::setAttr)
                    .join();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

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
                reqBody.append("]}");
            }
            reqBody.append("]}");
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

    private static String getData(String str, String key, int from, char start, char end) {
        int id = str.indexOf(key, from);
        StringBuilder sb = new StringBuilder();
        while (str.charAt(id) != start) {
            id++;
        }
        for (int i = id; str.charAt(i) != end; i++) {
            sb.append(str.charAt(i));
        }
        prev_id = id;
        return sb.toString().trim();
    }

    private static void setAttr(String response) {
        mass = getData(response, "mass", prev_id, ' ', ',');
        size = getData(response, "size", prev_id, ' ', ',');
        shape = getData(response, "shape", prev_id, ' ', ',').replaceAll("[^a-zA-Z]", "");
        firstActor = getData(response, "firstActor", prev_id, ' ', ',');
        secondActor = getData(response, "secondActor", prev_id, ' ', ',');
        frictionCoefficient = getData(response, "frictionCoefficient", prev_id, ' ', '}');
    }

    public static String getMass() {
        return mass;
    }

    public static String getSize() {
        return size;
    }

    public static String getShape() {
        return shape;
    }

    public static String getFirstActor() {
        return firstActor;
    }

    public static String getSecondActor() {
        return secondActor;
    }

    public static String getFrictionCoefficient() {
        return frictionCoefficient;
    }
}