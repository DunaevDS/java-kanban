package clients;

import model.utils.DataTransformation;

import java.net.http.HttpResponse;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.URI;

public class KVTaskClient {

    public static final int PORT = 8078;
    private final String url;
    private String API_TOKEN;

    public KVTaskClient() {
        url = "http://localhost:" + PORT;
        API_TOKEN = registerAPIToken(url);
        DataTransformation.createGson();
    }

    private String registerAPIToken(String url) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/register");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            HttpResponse<String> response = client.send(request, handler);
            API_TOKEN = response.body();
        } catch (InterruptedException | IOException e) {
            System.out.println("Registration failed " + e.getMessage());
        }
        return API_TOKEN;
    }

    public void put(String key, String json) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(body)
                .build();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            client.send(request, handler);
        } catch (InterruptedException | IOException e) {
            System.out.println("Save failed " + e.getMessage());
        }
    }

    public String load(String key) {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(url + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        HttpResponse<String> response = null;
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            response = client.send(request, handler);
        } catch (InterruptedException | IOException e) {
            System.out.println("Boot failed" + e.getMessage());
        }
        return response != null ? response.body() : "Boot OK";
    }
}