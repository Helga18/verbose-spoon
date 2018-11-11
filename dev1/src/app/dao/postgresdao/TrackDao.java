package app.dao.postgresdao;

import app.dao.UserDAO;
import app.entities.Tag;
import app.entities.Track;
import app.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrackDAO implements app.dao.TrackDAO {
    private Connection connection;
    private UserDAO userDAO;
    private TagDAO tagDAO;

    public TrackDAO() {
        this.connection = ConnectionSingleton.getInstance();
        this.userDAO = new app.dao.postgresdao.UserDAO();
        this.tagDAO = new app.dao.postgresdao.TagDAO();
    }

    @Override
    public List<Track> getAllTracks() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM track ORDER BY title DESC");
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
                resultSet.getDate("datetime"),
                tagDAO.getTagById(resultSet.getInt("sport_id"))
        );
    }

    @Override
    public void newTrack(User author, String title, Date datetime, int tag_id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO post (author_id, title, datetime, tag_id) VALUES ( ?, ?, ?, ?)");
        statement.setInt(1, author.getId());
        statement.setString(2, title);
        statement.setDate(3, datetime);
        statement.setInt(4, tag_id);
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
    public List<Track> getTracksByAuthor(User author) throws SQLException {
        List<Track> tracks = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE user_id= ? ORDER BY track.id DESC");
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
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM track WHERE title = ? ORDER BY track.id DESC");
        statement.setInt(1, tag.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Track track = instance(resultSet);
            tracks.add(track);
        }
        return tracks;
    }


}