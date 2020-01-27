package jku.dke.prmetaservice.entity;

public class ConnectionInfo {
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }


    private String name;
    private String url;
    private String description;

    public ConnectionInfo(String name, String url, String description) {
        this.name = name;
        this.url = url;
        this.description = description;
    }
}
