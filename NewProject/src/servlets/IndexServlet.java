package servlets;

import entities.Track;
import entities.User;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import helpers.MainHelper;
import services.TrackService;
import services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "IndexServlet")
public class IndexServlet extends HttpServlet {
    UserService userService = new UserService();
    TrackService trackService = new TrackService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter pw = response.getWriter();
        Configuration cfg = MainHelper.getConfig(getServletContext());
        Template tmpl = cfg.getTemplate("index.ftl");
        HashMap<String, Object> root = new HashMap<>();
        User curr_user = userService.getCurrentUser(request);
        List<Track> tracks = trackService.getAllTracks();
        root.put("user", curr_user);
        root.put("track1", tracks.get(0));


        if (curr_user != null && curr_user.getLogin().equals("admin")) {
            root.put("admin", userService.getCurrentUser(request));
        }
        try {
            tmpl.process(root, response.getWriter());
        } catch (
                TemplateException e) {
            e.printStackTrace();
        }
        pw.close();


    }
}
