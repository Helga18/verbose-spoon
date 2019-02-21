package services;

import dao.SimpleUserDAO;
import entities.User;

import javax.servlet.http.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private SimpleUserDAO userDAO = new SimpleUserDAO();

    public User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("current_user");
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remembered")) {
                    User cookie_user = getUserByLogin(cookie.getValue());
                    if (cookie_user != null) {
                        session.setAttribute("current_user", cookie_user);
                        return cookie_user;
                    }
                }
            }

        }
        return user;
    }

    public User authenticate(HttpServletRequest request) {
        String login = request.getParameter("login");
        if (login != null) {
            User user = userDAO.getUserByLogin(login);
            if (user == null) return null;
            String password = request.getParameter("password");
            if (password.equals(user.getPassword())) return user;
            else return null;
        }
        return null;
    }

    public void authorize(User current_user, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("current_user", current_user);
        if (request.getParameter("remembered") != null) {
            Cookie cookie = new Cookie("remembered", current_user.getLogin());
            cookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cookie);
        }
    }

    public User getUserByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    public boolean loginIsUnique(String login) {
        return userDAO.getUserByLogin(login) == null;
    }

    public User register(HttpServletRequest request, Part filePart, String path) {
        String login = request.getParameter("login");
        if (login != null && loginIsUnique(login)) {
            userDAO.addUser(request.getParameter("name"), request.getParameter("login"), request.getParameter("password"), request.getParameter("age"), request.getParameter("year"), filePart, path);
            System.out.println(userDAO.getUserByLogin(request.getParameter("login")));
            return userDAO.getUserByLogin(request.getParameter("login"));
        } else return null;

    }

    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("remembered") && cookie.getValue().equals(getCurrentUser(request).getLogin())) {
                Cookie cookie2 = new Cookie("remembered", "");
                cookie.setMaxAge(0);
                response.addCookie(cookie2);
            }
        }
        session.setAttribute("current_user", null);

    }

    public String updateUser(HttpServletRequest request) {
        String newLogin = getCurrentUser(request).getLogin();
        String newName = getCurrentUser(request).getUsername();
        String newPassword = getCurrentUser(request).getPassword();
        String newYear = getCurrentUser(request).getYear();
        String newAge = getCurrentUser(request).getAge();
        if (request.getParameter("login").length() == 0 && request.getParameter("username").length() == 0
                && request.getParameter("password").length() == 0 && request.getParameter("age").length() == 0 && request.getParameter("experience").length() == 0) {
            return "empty_request";
        } else {
            if (request.getParameter("login").length() > 0) {
                if (loginIsUnique(request.getParameter("login"))) {
                    newLogin = request.getParameter("login");
                } else {
                    return "not_unique_login";
                }
            }
            if (request.getParameter("username").length() > 0) {
                newName = request.getParameter("username");
            }
            if (request.getParameter("password").length() > 0) {
                newPassword = request.getParameter("password");
            }
            if (request.getParameter("age").length() > 0) {
                newAge =request.getParameter("age");
            }  if (request.getParameter("year").length() > 2018) {
                newYear = request.getParameter("year");
            }

        }
        userDAO.update(newName, newLogin, newPassword, newAge, newYear, getCurrentUser(request).getId());
        User user = userDAO.getById(getCurrentUser(request).getId());
        HttpSession session = request.getSession();
        session.setAttribute("current_user", user);
        return "success";
    }

    public User getUserById(HttpServletRequest request) {
        return userDAO.getById(Integer.parseInt(request.getPathInfo().split("/")[1]));
    }

    public void updatePhoto(HttpServletRequest request, Part filePart, String realPath) {
        userDAO.updatePhoto(getCurrentUser(request).getId(), filePart, realPath);
        HttpSession session = request.getSession();
        session.setAttribute("current_user", userDAO.getById(getCurrentUser(request).getId()));
    }
}