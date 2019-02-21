package services;

import dao.CommentDAO;
import entities.Comment;
import entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public class CommentService {
    private CommentDAO commentDAO = new CommentDAO();
    private UserService userService = new UserService();

    public List<Comment> getAllCommentsByTrackId(int id) {
        return commentDAO.getAllTrackComments(id);
    }

    public Comment addComment(HttpServletRequest request) {
        String text = request.getParameter("text");

        commentDAO.addComment(text, userService.getCurrentUser(request).getId(), Integer.parseInt(request.getParameter("car_id")));
        return commentDAO.getCommentByText(text);

    }

    public void deleteComment(HttpServletRequest request) {
        commentDAO.deleteComment(Integer.parseInt(request.getParameter("comment_id")));
    }
}
