package servlets;

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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("edit") != null) {
            response.sendRedirect("/edit");
        } else if (request.getParameter("logOut") != null) {
            userService.logOut(request, response);
            response.sendRedirect("/index");
        } else if (request.getParameter("changePhoto")!=null){
            userService.updatePhoto(request, request.getPart("file"), getServletContext().getRealPath("/files/users"));
            response.sendRedirect("/profile");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        if (userService.getCurrentUser(request) != null) {
            PrintWriter pw = response.getWriter();
            Configuration cfg = MainHelper.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("profile.ftl");
            HashMap<String, Object> root = new HashMap<>();
            String path = request.getPathInfo();
            if (path == null || path.equals("/")) {
                root.put("curr_user", userService.getCurrentUser(request));
            } else if (path.split("/").length == 2) {
                root.put("user", userService.getUserById(request));
                System.out.println("Оля дура");
            } else {
                response.sendRedirect("/login");
            }
            try {
                tmpl.process(root, response.getWriter());
            } catch (
                    TemplateException e) {
                e.printStackTrace();
            }
            pw.close();

        } else {
            response.sendRedirect("/login");
        }
    }
}