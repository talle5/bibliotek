package challenge.literalura.demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class ApiRequest {
    static final String URL = "https://gutendex.com/books/";

    public static String getHttps(String destino) {
        try (var client = HttpClient.newHttpClient()) {
            var request = HttpRequest.newBuilder(URI.create(destino)).build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI genUri(String... args) {
        var url = new StringBuilder();
        url.append(URL).append('?');
        for (var arg:args) {
            url.append(arg).append('&');
        }
        url.deleteCharAt(url.length() - 1);
        return URI.create(url.toString());
    }
}
