package app.dao;

import app.entities.Comment;
import app.entities.Track;
import app.entities.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface CommentDAO {
    Comment newComment(User author, Track track, Date datetime, String text) throws SQLException;

    boolean deleteComment(int id) throws SQLException;

    Comment getCommentById(int id) throws SQLException;

    List<Comment> getCommentsByTrack(Track track) throws SQLException;
}

