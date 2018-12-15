package models;


import models.User;
import models.Place;
import java.sql.Timestamp;
import java.util.Date;

public class Comment {
    private  int id;
    private String text;
    private Timestamp date;
    private User user;
    private Place place;

    public Comment(int id, String text, Timestamp date, User user) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
        this.place = place;
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


}
