package app.services;

import app.dao.simple_impl.SimpleUserDAO;
import app.entities.Post;
import app.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class PostService {
    private UserDAO userDAO = new SimpleUserDAO();

    public void addPost(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        userDAO.addPost(request.getParameter("text"), (User) request.getSession().getAttribute("current_user"));
    }

    public List<Post> getUserPosts(User user) throws SQLException, ClassNotFoundException {
        return userDAO.getPostsById(user.getId());
    }
    public Post getPostById(int id) throws SQLException, ClassNotFoundException {
        return userDAO.getPostById(id);
    }
    public void updatePostById(int id, String text) throws SQLException, ClassNotFoundException {
        userDAO.updatePostById(id,text);
    }
    public void deletePostById(int id) throws SQLException, ClassNotFoundException {
        userDAO.deletePostById(id);
    }

    public void deletePost(Integer id) {
    }
}
