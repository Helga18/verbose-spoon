package app.services;


import app.dao.TagDAO;
import app.entities.Tag;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TagService {
    private TagDAO tagDAO;

    public TagService() {
        this.tagDAO = new app.dao.postgresdao.TagDAO();
    }

    public Tag getTagById(int id) {
        try {
            return tagDAO.getTagById(id);
        } catch (SQLException e) {
            return null;
        }
    }
}
