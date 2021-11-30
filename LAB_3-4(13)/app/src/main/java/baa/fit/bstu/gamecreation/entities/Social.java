package baa.fit.bstu.gamecreation.entities;

public class Social {
    private final String name;
    private final String link;
    private final String pakageId;

    public Social(String name, String link, String pakageId) {
        this.name = name;
        this.link = link;
        this.pakageId = pakageId;
    }

    public String getName() { return name; }

    public String getLink() {
        return link;
    }

    public String getPakageId() {
        return pakageId;
    }
}
