package wikipedia;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class Browser {
    private static final String ARTICLE_URL = "https://ru.wikipedia.org/w/index.php?curid=";

    public void openArticle(SearchResult result) {
        String url = ARTICLE_URL + result.getPageId();
        try {
            if (!Desktop.isDesktopSupported()) {
                System.out.println("На этом компьютере автоматическое открытие браузера не поддерживается.");
                System.out.println("Откройте ссылку вручную: " + url);
                return;
            }
            Desktop.getDesktop().browse(URI.create(url));
            System.out.println("Статья открыта в браузере.");
        } catch (IOException | UnsupportedOperationException | IllegalArgumentException e) {
            System.out.println("Не удалось открыть браузер автоматически.");
            System.out.println("Ссылка на статью: " + url);
        }
    }
}