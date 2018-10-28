package app.servlet;

import app.entities.Post;
import app.services.PostService;
import app.services.UserService;
import sun.awt.Win32GraphicsConfig;

import javax.security.auth.login.Configuration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "PostsServlet")
public class PostsServlet extends HttpServlet {
    private static final Win32GraphicsConfig ConfigSingleton = ;
    UserService userService = new UserService();
    PostService postService = new PostService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String path = request.getPathInfo();
        PrintWriter pw = response.getWriter();
        if (path == null || path.equals("/")) {
            response.sendRedirect("/post");
            return;
        }
        String[] parts = path.split("/");
        int id = Integer.parseInt(parts[1]);
        try {
            if (parts.length == 2 && postService.getPostById(id) != null) {
                if (request.getParameter("updatedText")!=null) {
                    postService.updatePostById(id, request.getParameter("updatedText"));
                    response.sendRedirect("/posts/");
                    return;
                }
                else {
                    postService.deletePostById(id);
                    response.sendRedirect("/posts/");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        pw.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Configuration cfg =  ConfigSingleton.getConfig(get)
        String path = request.getPathInfo();
        PrintWriter pw = response.getWriter();

        try {
            if (userService.getCurrentUser(request) != null) {
                if (path == null || path.equals("/")) {
                    for (Post post : postService.getUserPosts(userService.getCurrentUser(request))) {
                        pw.print("<h6>" + post.getText() + "</h6>");
                        pw.print("<form action=\"http://localhost:8080/posts/" + post.getId() + "\">\n" +
                                "        <button>Edit</button>\n" +
                                "    </form>");
                    }
                    return;
                }
                String[] parts = path.split("/");
                if (parts.length != 2) {
                    response.sendRedirect("/login");
                    return;
                }
                int id = Integer.parseInt(parts[1]);
                if (postService.getPostById(id) == null) {
                    response.sendRedirect("/login");
                    return;
                }
                pw.print("<h6>" + postService.getPostById(id).getText() + "</h6>");
                if (userService.getCurrentUser(request).getId() == postService.getPostById(id).getAuthor().getId()) {
                    pw.print("<form method=\"post\">\n" +
                            "        <input type=\"text\" name=\"updatedText\">\n" +
                            "        <input type=\"submit\" value=\"update\" name=\"update\">\n" +
                            "    </form>");
                    pw.print("<form method=\"post\">\n" +
                            "        <input type=\"submit\" value=\"delete\" name=\"delete\">\n" +
                            "    </form>");
                }
            } else {
                response.sendRedirect("/login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        pw.flush();

    }
}

