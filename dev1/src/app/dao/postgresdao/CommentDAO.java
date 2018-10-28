package app.dao.postgresdao;

import app.entities.Comment;
import app.entities.Track;
import app.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO implements app.dao.CommentDAO {

    private Connection connection;
    private UserDAO userDAO;
    private TrackDAO trackDAO;

    public CommentDAO() {
        this.connection = ConnectionSingleton.getInstance();
        this.userDAO = new app.dao.postgresdao.UserDAO();
        this.trackDAO = new TrackDAO();
    }

    @Override
    public Comment newComment(User author, Track track, Date datetime, String text) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO comment (author_id, track_id, datetime, text) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, author.getId());
        statement.setInt(2, track.getId());
        statement.setDate(3, datetime);
        statement.setString(4, text);
        statement.executeUpdate();
        ResultSet keys = statement.getGeneratedKeys();
        if (keys.next()) {
            return instance(keys);
        }
        return null;
    }

    @Override
    public boolean deleteComment(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
        statement.setInt(1, id);
        return statement.execute();
    }

    @Override
    public Comment getCommentById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        return instance(resultSet);
    }

    @Override
    public List<Comment> getCommentsByTrack(Track track) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment c INNER JOIN \"user\" ON \"user\".id = c.author_id WHERE track_id = ? ORDER BY c.id ASC");
        statement.setInt(1, track.getId());
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("id"));

            User user = userDAO.instance(rs);
            user.setId(rs.getInt("author_id"));

            comment.setAuthor(user);
            comment.setDatetime(rs.getDate("datetime"));
            comment.setText(rs.getString("text"));
            comments.add(comment);
        }
        return comments;
    }

    public Comment instance(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getInt("id"),
                userDAO.getUserById(rs.getInt("author_id")),
                trackDAO.getTrackById(rs.getInt("track_id")),
                rs.getDate("datetime"),
                rs.getString("text")
        );
    }
}
