package app.entities;

import app.entities.Track;
import app.entities.User;

import java.util.Date;

public class Comment {
    private int id;
    private Integer authorId;

    private User author;

    private Integer trackId;
    private Date datetime;
    private String text;
    private Track track;

    public Comment(int id, User author, Track track, Date datetime, String text) {
        this.id = id;
        this.author = author;
        this.track = track;
        this.datetime = datetime;
        this.text = text;
    }

    public Comment() {

    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getTrackId() {
        return trackId;
    }

    public void setTrackId(Integer trackId) {
        this.trackId = trackId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Track getTrack() {
        return track;
    }

    public void setPost(Track track) {
        this.track = track;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

