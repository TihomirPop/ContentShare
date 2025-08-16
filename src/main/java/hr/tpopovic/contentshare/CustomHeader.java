package hr.tpopovic.contentshare;

public enum CustomHeader {
    FILE_NAME("X-File-Name");

    private final String name;

    CustomHeader(String name) {
        this.name = name;
    }

    public String headerName() {
        return name;
    }
}
