package app.entities;

import java.util.Date;

public class Track {
    private int id;

    private Integer authorId;
    private User author;
    private String title;
    private String text;
    private Date datetime;
    private Tag tag;

    public Track(int id, User author, String title, Date datetime, Tag tag) {
        this.id = id;
        this.author = author;
        this.authorId = author.getId();
        this.title = title;
        this.datetime = datetime;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

