package helpers;

import dao.*;
import entities.*;
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
    private static TrackDAO trackDAO = new TrackDAO();


    public static Connection getConn() {
        try {
            if (conn==null) {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/music",
                        "postgres",
                        "leliks");
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
                    rs.getInt("userid"),
                    rs.getString("name"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("age"),
                    rs.getString("year"),
                    rs.getString("photo_path"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Track makeORMTrack(ResultSet rs) {
        try {
            return new Track(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("singer"),
                    rs.getInt("year"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Track> makeFavoriteTrackORMList(ResultSet rs) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        while (rs.next()) {
            Track track = trackDAO.getTrackById(rs.getInt("track_id"));
            tracks.add(track);
        }
        return tracks;
    }



    public static Comment makeORMComment(ResultSet rs) {
        try {
            return new Comment(
                    rs.getInt("id"),
                    rs.getString("text"),
                    rs.getTimestamp("date"),
                    userDAO.getById(rs.getInt("user_id")),
                    trackDAO.getTrackById(rs.getInt("track_id")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    public static List<Track> makeTrackORMList(ResultSet rs) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        Track track;
        while (rs.next()) {
            track = makeORMTrack(rs);
            tracks.add(track);
        }
        return tracks;
    }




}
