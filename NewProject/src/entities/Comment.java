package entities;


import java.sql.Timestamp;
import java.util.Date;

public class Comment {
    private  int id;
    private String text;
    private Timestamp date;
    private User user;
    private Track track;

    public Comment(int id, String text, Timestamp date, User user, Track track) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
        this.track = track;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Timestamp getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public Track getTrack() {
        return track;
    }
}