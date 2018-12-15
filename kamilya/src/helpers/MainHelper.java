package helpers;

import dao.*;
import models.*;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;


import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainHelper {
    private static Connection conn;
    private static SimpleUserDAO userDAO = new SimpleUserDAO();



    public static Connection getConn() {
        try {
            if (conn==null) {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5433/kazan_project",
                        "postgres",
                        "Kamilya12");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static Configuration cfg = null;

    public static Configuration getConfig(ServletContext sc) {
        if (cfg == null) {
            cfg = new Configuration();
            cfg.setServletContextForTemplateLoading(
                    sc,
                    "/WEB-INF/templates"
            );
            cfg.setTemplateExceptionHandler(
                    TemplateExceptionHandler.HTML_DEBUG_HANDLER
            );
        }
        return cfg;
    }

    public static User makeORMUser(ResultSet rs) {
        try {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("surname"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Comment makeORMComment(ResultSet rs) {
        try {
            return new Comment(
                    rs.getInt("id"),
                    rs.getString("content"),
                    rs.getTimestamp("date"),
                    userDAO.getById(rs.getInt("user_id")));
                //    placeDAO.getPlaceById(rs.getInt("place_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Comment> makeCommentORMList(ResultSet rs) {
    }



   /* public static Comment makeORMComment(ResultSet rs) {
        try {
            return new Comment(
                    rs.getInt("id"),
                    rs.getString("text"),
                    rs.getTimestamp("date"),
                    userDAO.getById(rs.getInt("user_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Car> makeFavoriteCarORMList(ResultSet rs) throws SQLException {
        List<Car> cars = new ArrayList<>();
        while (rs.next()) {
            Car car = carDAO.getCarById(rs.getInt("car_id"));
            cars.add(car);
        }
        return cars;
    }

    public static List<Comment> makeCommentORMList(ResultSet rs) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        Comment comment;
        while (rs.next()) {
            comment = makeORMComment(rs);
            comments.add(comment);
        }
        return comments;
    }

    public static List<CarClass> makeClassORMList(ResultSet rs) throws SQLException {
        List<CarClass> carClasses = new ArrayList<>();
        CarClass carClass;
        while (rs.next()) {
            carClass = makeORMClass(rs);
            carClasses.add(carClass);
        }
        return carClasses;
    }
    */

}
