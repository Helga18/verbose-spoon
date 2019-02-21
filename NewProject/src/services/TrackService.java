package services;

import dao.TrackDAO;
import entities.Track;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class TrackService {
    private UserService userService = new UserService();
    private TrackDAO trackDAO = new TrackDAO();

    public List<Track> getAllTracks() {
        return trackDAO.getAllTracks();
    }

    public Track getTrackById(int id) {
        return trackDAO.getTrackById(id);
    }

    public List<Track> getAllFavoritesByUserId(HttpServletRequest request) {
        return trackDAO.getAllFavorites(userService.getCurrentUser(request).getId());
    }

    public String addFavorite(HttpServletRequest request) {
        int trackId = Integer.parseInt(request.getParameter("track_id"));
        int userId = userService.getCurrentUser(request).getId();
        List<Track> alreadyFav = trackDAO.getAllFavorites(userId);
        boolean trackAlreadyInFavs = false;
        for (Track track : alreadyFav) {
            if (track.getId() == trackId) {
                trackAlreadyInFavs = true;
            }
        }
        if (trackAlreadyInFavs) {
            return "already";
        } else {
            trackDAO.addFavorite(trackId, userId);
            return "added";
        }

    }

    public void addTrack(HttpServletRequest request, Part filePart, String path) {
        String type = request.getParameter("type");
        String string = request.getParameter("string");
        int year = Integer.parseInt(request.getParameter("year"));
        trackDAO.addTrack(type, string, year, filePart, path);

    }

}