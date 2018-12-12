package app.dao;

import app.entities.Tag;

import java.sql.SQLException;
import java.util.List;

public interface TagDAO {
    Tag getTagById(int id) throws SQLException;

    Tag getTagByName(String name) throws SQLException;

    List<Tag> getAllTags() throws SQLException;
}