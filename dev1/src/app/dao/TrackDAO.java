package app.dao;

import app.entities.Track;
import app.entities.Tag;
import app.entities.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface  TrackDAO {
    List<Track> getAllTracks() throws SQLException;


    Track getTrackById(int id) throws SQLException;


    List<Track> getTracksByAuthor(User author) throws SQLException;

    List<Track> getTracksByTag(Tag tag) throws SQLException;

    void newTrack(User author, String title, Date datetime, int tag_id) throws SQLException;
}

