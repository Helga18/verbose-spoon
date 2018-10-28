

package app.services;

import app.config.ConnectionSingleton;
import app.dao.implementations.collectionDao.SimplePostDao;
import app.dao.implementations.collectionDao.SimpleUserDao;
import app.dao.implementations.dbDao.SimpleDbPostDao;
import app.dao.implementations.dbDao.SimpleDbUserDao;
import app.dao.interfaces.PostDao;
import app.dao.interfaces.UserDao;
import app.entities.Post;
import app.entities.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class UserService {

    Connection connection = ConnectionSingleton.getInstance();

    private static CookieService cookieService = new CookieService();
    private UserDao userDao = new SimpleDbUserDao(connection);
    private PostDao postDao = new SimpleDbPostDao(connection);

    private final static String USER_COOKIE = "remember_me";


    public User getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        User user = (User)request.getSession().getAttribute("current_user");

        if(user == null){
            user = getUserByCookie(request,response);
        }
        return user;
    }

    private User getUserByCookie(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        User user = null;
        Cookie rememberCookie = cookieService.getCookieByName(request,USER_COOKIE);

        if(rememberCookie!= null){

            String username = rememberCookie.getValue();
            if(username != null) {

                user = userDao.getUserByName(username);
                if(user!= null){
                    authorize(request,response,user);
                }
            }
        }

        return user;
    }

    public User authenticate(HttpServletRequest request) throws SQLException {

        String username = request.getParameter("username");

        if(username != null) {

            String password = request.getParameter("password");
            User user = userDao.getUserByName(username);

            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }


    public void authorize(HttpServletRequest request, HttpServletResponse response, User current_user) {

        request.getSession().setAttribute("current_user",current_user);

        if(request.getParameter("remember_me") != null){

            Cookie cookie = new Cookie("remember_me",  current_user.getName());
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);}
    }

    public void deleteProfile(Integer id) throws SQLException {
        userDao.deleteById(id);
    }

    public void updateProfile(Integer id, Map parameterMap) throws SQLException {

        User user = new User(id,
                (String)parameterMap.get("name"),
                (String)parameterMap.get("hashPassword"));
        userDao.update(user);
    }

    public List<User> getUsers() throws SQLException {
        return userDao.getUsers();
    }
}

