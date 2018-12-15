package dao;

import models.Place;
import models.Comment;
import helpers.MainHelper;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class CommentDAO {
    private Connection connection = MainHelper.getConn();
    public Comment getCommentById(int id){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from comment");
            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    return MainHelper.makeORMComment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Comment getCommentByText(String text){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from comment");
            while (rs.next()) {
                if (rs.getString("content").equals(text)) {
                    return MainHelper.makeORMComment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comment> getAllCarComments(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("select * from comment where place_id=? order by date DESC ");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return MainHelper.makeCommentORMList(rs);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addComment(String text, int user_id, int place_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into comment(content, date, user_id, place_id ) values (?,'now',?,?)");
            ps.setString(1, text);
            ps.setInt(2, user_id);
            ps.setInt(3, place_id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int comment_id) {
        try {
            PreparedStatement statement = connection.prepareStatement("delete from comment where id = ? ");
            statement.setInt(1, comment_id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}