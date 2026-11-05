package wikipedia;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;

public class WikipediaParser {
    public List<SearchResult> parse(String json) {
        List<SearchResult> results = new ArrayList<>();
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        JsonObject query = root.getAsJsonObject("query");
        if (query == null || !query.has("search")) {
            return results;
        }

        JsonArray articles = query.getAsJsonArray("search");
        for (JsonElement element : articles) {
            JsonObject article = element.getAsJsonObject();
            int pageId = article.get("pageid").getAsInt();
            String title = toConsoleText(article.get("title").getAsString());
            String snippet = article.has("snippet") ? article.get("snippet").getAsString() : "";
            snippet = snippet.replaceAll("<[^>]*>", "").replaceAll("&quot;", "\"")
                    .replaceAll("&#039;", "'").replaceAll("&amp;", "&")
                    .replaceAll("&lt;", "<").replaceAll("&gt;", ">");
            snippet = toConsoleText(snippet);
            results.add(new SearchResult(title, snippet, pageId));
        }
        return results;
    }

    /**
     * Заменяет типографские символы Википедии обычными.
     * В консольной кодировке Windows (CP866) нет длинного тире, кавычек-ёлочек
     * и неразрывного пробела, поэтому без замены они выводятся как «?».
     */
    private String toConsoleText(String text) {
        return text
                .replace('\u2014', '-')   
                .replace('\u2013', '-')   
                .replace('\u2011', '-')  
                .replace('\u00AB', '"')  
                .replace('\u00BB', '"')   
                .replace('\u201E', '"')   
                .replace('\u201C', '"')   
                .replace('\u201D', '"') 
                .replace('\u2018', '\'')  
                .replace('\u2019', '\'')  
                .replace('\u00A0', ' ')   
                .replace('\u202F', ' ')  
                .replace("\u2026", "...") 
                .replaceAll("\\s+", " ")
                .trim();
    }
}