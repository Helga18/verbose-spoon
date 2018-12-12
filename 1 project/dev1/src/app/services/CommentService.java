package app.services;

import app.dao.CommentDAO;
import app.entities.Comment;
import app.entities.Track;
import app.entities.User;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO;

    public CommentService() {
        this.commentDAO = new app.dao.postgresdao.CommentDAO();
    }

    public Comment newComment(User author, Track track, String text) throws SQLException {
        return commentDAO.newComment(author, track, new java.sql.Date(new java.util.Date().getTime()), text);
    }

    public void deleteComment(int id) {
        try {
            commentDAO.deleteComment(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comment getCommentById(int id) {
        try {
            return commentDAO.getCommentById(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Comment> getCommentsByTrack(Track track) {
        try {
            return commentDAO.getCommentsByTrack(track);
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }
}

