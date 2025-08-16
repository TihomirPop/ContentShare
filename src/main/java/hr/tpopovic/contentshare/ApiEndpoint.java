package hr.tpopovic.contentshare;

public enum ApiEndpoint {

    CONTENT("/api/content");

    public static final String CONTENT_PATH = "/api/content";

    private final String path;


    ApiEndpoint(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
