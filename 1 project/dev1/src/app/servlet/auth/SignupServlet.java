package app.servlet.auth;

import app.entities.User;
import app.services.UserService;
import app.servlet.Helper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "SignupServlet")
public class SignupServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userService.getCurrentUser(request) != null) {
            response.sendError(403);
        } else {
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            boolean success = userService.signUp(login, password, name);
            if (success) {
                response.sendRedirect("/profile");
            } else {
                response.sendRedirect("/auth?msg=fail");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = userService.getCurrentUser(request);
        if (user != null) {
            response.sendRedirect("/profile");
            return;
        }
        Helper.render(
                getServletContext(),
                response,
                "signup.ftl",
                new HashMap<>()
        );
    }
}
