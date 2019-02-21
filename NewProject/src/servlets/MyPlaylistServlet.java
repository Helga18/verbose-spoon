package servlets;

import entities.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import helpers.MainHelper;
import services.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "MyPlaylistServlet")
public class MyPlaylistServlet extends HttpServlet {
    private UserService userService = new UserService();
    private TrackService trackService = new TrackService();
    private CommentService commentService = new CommentService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        String path = request.getPathInfo();

        if (path == null || path.equals("/")) {
            Configuration cfg = MainHelper.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("playlist.ftl");
            HashMap<String, Object> root = new HashMap<>();
            List<Track> tracks = trackService.getAllTracks();
            root.put("tracks", tracks);
            try {
                tmpl.process(root, response.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        } else if (path.split("/").length == 2) {
            int trackId = Integer.parseInt(path.split("/")[1]);
            Configuration cfg = MainHelper.getConfig(getServletContext());
            Template tmpl = cfg.getTemplate("track.ftl");
            HashMap<String, Object> root = new HashMap<>();
            Track track = trackService.getTrackById(trackId);
            List<Comment> comments = commentService.getAllCommentsByTrackId(trackId);
            root.put("track", track);
            root.put("comments", comments);
            root.put("user", userService.getCurrentUser(request));
            try {
                tmpl.process(root, response.getWriter());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }

    }
}