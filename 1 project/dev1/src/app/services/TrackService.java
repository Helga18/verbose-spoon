package app.services;

import app.dao.TrackDAO;
import app.entities.Track;
import app.entities.Tag;
import app.entities.User;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TrackService {
    private TrackDAO trackDAO;

    public TrackService() {
        this.trackDAO = new app.dao.postgresdao.TrackDAO();
    }

    public void newTrack(User author, String title,  int tag_id) throws SQLException {
        trackDAO.newTrack(author, title,  new java.sql.Date(new Date().getTime()), tag_id);
    }

    public List<Track> getAllTracks() {
        try {
            return trackDAO.getAllTracks();
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }

    public Track getTrackById(int id) {
        try {
            return trackDAO.getTrackById(id);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Track> getTracksByTag(Tag tag) {
        try {
            return  trackDAO.getTracksByTag(tag);
        } catch (SQLException e) {
            return new LinkedList<>();
        }
    }
}

