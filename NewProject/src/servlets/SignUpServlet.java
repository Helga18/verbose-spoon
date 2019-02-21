package servlets;

import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import helpers.MainHelper;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SignUpServlet")
@MultipartConfig
public class SignUpServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        if (userService.getCurrentUser(request) != null) {
            response.sendRedirect("/login");
        } else {
            final Part filePart = request.getPart("file");
            User newUser = userService.register(request, filePart, getServletContext().getRealPath("/files/users"));
            if (newUser != null) {
                userService.authorize(newUser, request, response);
                response.sendRedirect("/login");
            } else {
                response.sendRedirect("/signUp");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        if (userService.getCurrentUser(request) != null) {
            response.sendRedirect("/profile");
        } else {
            PrintWriter pw = response.getWriter();
            Configuration cfg = MainHelper.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("signUp.ftl");
            try {
                tmpl.process(null, response.getWriter());
            } catch (
                    TemplateException e) {
                e.printStackTrace();
            }
            pw.close();
        }
    }
}
