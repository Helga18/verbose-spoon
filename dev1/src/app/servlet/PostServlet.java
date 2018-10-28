package app.servlet;


import config.ConfigSingleton;
import app.entities.Post;
import app.entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import app.services.PostService;
import app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PostServlet extends HttpServlet {

    private UserService userService = new UserService();
    private PostService postService = new PostService();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;
        try {
            user = userService.getCurrentUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user == null) {
            response.sendRedirect("/login");
            return;
        }
        else {
            String path = request.getPathInfo();
            System.out.println("Path: " + path);
            if (path != null) {

                Integer id = Integer.parseInt(path.split("/")[1]);
                Map parameterMap = request.getParameterMap();
                if (parameterMap.isEmpty()) {
                    try {
                        postService.deletePost(id);
                        response.sendRedirect("/profile");
                        return;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        postService.updatePost(id,user,request);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    postService.publishPost(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        response.sendRedirect("/profile");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;
        try {
            user = userService.getCurrentUser(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(user == null) {
            response.sendRedirect("/login");
            return;
        }
        String path = request.getPathInfo();
        PrintWriter writer = response.getWriter();

        Configuration cfg = ConfigSingleton.getConfig(getServletContext());
        Template tmpl = cfg.getTemplate("Post.ftl");
        HashMap<String, Object> root = new HashMap<>();

        if (path != null) {

            Integer postId = Integer.parseInt(path.split("/")[1]);
            try {
                Post post = postService.getPost(postId);
                root.put("post", post);
                if(post.getUser().getId() == user.getId()){
                    root.put("is_owner", true);
                }
                else {
                    root.put("is_owner", false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
