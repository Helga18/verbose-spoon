package models;

public class User {
    private int id;
    private String username;
    private String login;
    private String password;
    private String surname;


    public User(int id, String username, String login, String password, String surname) {
        this.id = id;
        this.username = username;
        this.login = login;
        this.password = password;
        this.surname = surname;

    }





    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
