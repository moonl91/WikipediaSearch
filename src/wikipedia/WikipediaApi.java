package wikipedia;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class WikipediaApi {
    private static final String API_URL = "https://ru.wikipedia.org/w/api.php";
    private final HttpClient client = HttpClient.newHttpClient();

    public String search(String query) throws IOException, InterruptedException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = API_URL + "?action=query&list=search&utf8=1&format=json&srsearch="
                + encodedQuery + "&srlimit=10";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "BeginnerWikipediaSearch/1.0 (Java educational project)")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new IOException("Википедия вернула ошибку. Код: " + response.statusCode());
        }
        return response.body();
    }
}
