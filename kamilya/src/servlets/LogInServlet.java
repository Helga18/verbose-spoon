package servlets;

import models.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import helpers.MainHelper;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LogInServlet extends javax.servlet.http.HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        if (userService.getCurrentUser(request) != null) {
            response.sendRedirect("/fav");
        } else {
            PrintWriter pw = response.getWriter();
            Configuration cfg = MainHelper.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("SignIn.jsp");
            try {
                tmpl.process(null, response.getWriter());
            } catch (
                    TemplateException e) {
                e.printStackTrace();
            }
            pw.close();

        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        if (userService.getCurrentUser(request) != null) {
            response.sendRedirect("/fav");
        } else {
            User current_user = userService.authenticate(request);
            if (current_user != null) {
                userService.authorize(current_user, request, response);
                response.sendRedirect("/SignIn");
            } else {
                response.sendRedirect("/login?err=too_bad_login");
            }


        }
    }
}

