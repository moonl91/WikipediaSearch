package wikipedia;

import java.util.List;
import java.util.NoSuchElementException;

public class ConsoleMenu {
    private final SearchInput input = new SearchInput();
    private final WikipediaApi api = new WikipediaApi();
    private final WikipediaParser parser = new WikipediaParser();
    private final Browser browser = new Browser();

    public void start() {
        System.out.println("=== Поиск в Википедии ===");
        try {
            boolean continueSearch = true;
            while (continueSearch) {
                runSearch();
                continueSearch = input.askContinue();
            }
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Ввод завершён.");
        }
        System.out.println("Спасибо за использование программы!");
    }

    private void runSearch() {
        String query = input.readQuery();
        try {
            System.out.println("Ищем статьи...\n");
            String json = api.search(query);
            List<SearchResult> results = parser.parse(json);
            showResults(results);

            if (results.isEmpty()) {
                System.out.println("По вашему запросу ничего не найдено.");
                return;
            }

            int choice = input.readNumber(0, results.size());
            if (choice != 0) {
                browser.openArticle(results.get(choice - 1));
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Запрос был прерван. Попробуйте снова.");
        } catch (Exception e) {
            System.out.println("Не удалось выполнить поиск. Проверьте подключение к интернету и попробуйте снова.");
            System.out.println("Подробности ошибки: " + e.getMessage());
        }
    }

    private void showResults(List<SearchResult> results) {
        if (results.isEmpty()) {
            return;
        }
        System.out.println("Результаты поиска:");
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            System.out.println((i + 1) + ". " + result.getTitle());
            if (!result.getSnippet().isBlank()) {
                System.out.println("   ..." + result.getSnippet() + "...");
            }
            System.out.println();
        }
    }
}