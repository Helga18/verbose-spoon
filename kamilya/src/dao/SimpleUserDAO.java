package dao;

import models.User;
import helpers.MainHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;

public class SimpleUserDAO {

    private Connection connection = MainHelper.getConn();
    public User getUserByLogin(String login) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from \"user\"");
            while (rs.next()) {
                if (rs.getString("login").equals(login)) {
                    return MainHelper.makeORMUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User getById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from \"user\"");
            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    return MainHelper.makeORMUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addUser(String username, String login, String password, String surname, String path) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into \"user\"(name, login, password, surname) values (?,?,?,?) returning id");
            ps.setString(1, username);
            ps.setString(2, login);
            ps.setString(3, password);
            ps.setString(4, surname);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int userId = rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String newName, String newLogin, String newPassword, String newSurname, int user_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("update \"user\" set name=?, login=?, password=?,surname = ? where id=?");
            ps.setString(1, newName);
            ps.setString(2, newLogin);
            ps.setString(3, newPassword);
            ps.setString(4, newSurname);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    }





