package app.dao.postgresdao;

import app.entities.Tag;
import app.entities.Track;
import app.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements app.dao.TrackDAO {
    private Connection connection;
    private app.dao.UserDAO userDAO;

    public TrackDAO() {
        this.connection = ConnectionSingleton.getInstance();
        this.userDAO = new app.dao.postgresdao.UserDAO();
    }

    @Override
    public List<Track> getAllTracks() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM track");
        List<Track> tracks = new ArrayList<>();
        while (resultSet.next()) {
            tracks.add(instance(resultSet));
        }
        return tracks;
    }

    private Track instance(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getInt("id"),
                userDAO.getUserById(resultSet.getInt("author_id")),
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getDate("datetime"),
                TagDAO.getTagById(resultSet.getInt("tag_id"))
        );
    }

    @Override
    public void newTrack(User author, String title, Date datetime, String tag) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO post (author_id, title, datetime, tag) VALUES (?, ?, ?, ?, ?)");
        statement.setInt(1, author.getId());
        statement.setString(2, title);
        statement.setDate(4, datetime);
        statement.setString(5, tag);
        statement.execute();
    }

    @Override
    public Track getTrackById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }

    @Override
    public boolean deleteTrack(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM track WHERE id = ?");
        statement.setInt(1, id);
        return statement.execute();
    }

    @Override
    public List<Track> getTrackByAuthor(User author) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE author_id = ?");
        statement.setInt(1, author.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Track track = instance(resultSet);
            tracks.add(track);
        }
        return tracks;

    }

    @Override
    public List<Track> getTracksByTag(Tag tag) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE tag_id = ?");
        statement.setInt(1, tag.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Track track = instance(resultSet);
            tracks.add(track);
        }       return tracks;
    }


}
