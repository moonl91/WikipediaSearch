
package wikipedia;

public class SearchResult {
    private final String title;
    private final String snippet;
    private final int pageId;

    public SearchResult(String title, String snippet, int pageId) {
        this.title = title;
        this.snippet = snippet;
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getPageId() {
        return pageId;
    }
}
