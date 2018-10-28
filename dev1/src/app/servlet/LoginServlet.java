package app.servlet;

import config.ConfigSingleton;
import app.entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class LoginServlet extends HttpServlet {


    private static UserService userService = new UserService();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        User current_user = null;
        try {
            current_user = (User) userService.getCurrentUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(current_user != null) {
            response.sendRedirect("/profile");
            return;
        }

        else {

            try {
                current_user = userService.authenticate(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(current_user != null){
                userService.authorize(request,response,current_user);
                response.sendRedirect("/profile");
                return;
            }

            response.sendRedirect("/login");
        }



    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        PrintWriter writer = response.getWriter();

        User user = null;
        try {
            user = (User) userService.getCurrentUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user != null){

            response.sendRedirect("/profile");
            return;

        }
        else{

            response.setContentType("text/html");

            Configuration cfg = ConfigSingleton.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("Login.ftl");
            HashMap<String, Object> root = new HashMap<>();
            root.put("title","login");
            try {
                List<User> userList = userService.getUsers();
                root.put("users", userList);
            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {
                tmpl.process(root, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }finally {
                writer.close();
            }

        }
    }
}
