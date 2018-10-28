package app.dao;

import app.entities.Track;
import app.entities.Tag;
import app.entities.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface  TrackDAO {
    List<Track> getAllTracks() throws SQLException;

    void newTrack(User author, String title, Date datetime, String  tag) throws SQLException;

    Track getTrackById(int id) throws SQLException;

    boolean deleteTrack(int id) throws SQLException;

    List<Track> getTrackByAuthor(User author) throws SQLException;

    List<Track> getTracksByTag(Tag tag) throws SQLException;
}

