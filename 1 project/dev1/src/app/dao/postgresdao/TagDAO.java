package app.dao.postgresdao;

import app.entities.Tag;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TagDAO implements app.dao.TagDAO {
    private Connection connection;

    public TagDAO() {
        this.connection = ConnectionSingleton.getInstance();
    }

    @Override
    public Tag getTagById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tag WHERE id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }

    private Tag instance(ResultSet resultSet) throws SQLException {
        return new Tag(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }

    @Override
    public Tag getTagByName(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tag WHERE name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }

    @Override
    public List<Tag> getAllTags() throws SQLException {
        List<Tag> tags = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM tag");
        while (resultSet.next()) {
            tags.add(instance(resultSet));
        }
        return tags;
    }


}
