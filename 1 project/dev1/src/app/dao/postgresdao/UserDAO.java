package app.dao.postgresdao;

import app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements app.dao.UserDAO {
    Connection connection;

    public UserDAO() {
        connection = ConnectionSingleton.getInstance();
    }

    @Override
    public User getUserByLogin(String username) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE login = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"user\" WHERE id = ?");
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }

    public User instance(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                resultSet.getString("name")
        );
    }

    @Override
    public boolean addUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO \"user\" (login, password, name) VALUES (?, ?, ?)");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        return statement.execute();
    }

    @Override
    public boolean addToken(User user, String token) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "insert into tokens (user_id, token) values (?, ?)" +
                        " on conflict (user_id) do update set token = ?");
        statement.setInt(1, user.getId());
        statement.setString(2, token);
        statement.setString(3, token);
        return statement.execute();
    }

    @Override
    public boolean updateUser(int id, String password, String name, String login) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE \"user\" SET password = ?, name = ?, login = ? WHERE id = ?");
        statement.setString(1, password);
        statement.setString(2, name);
        statement.setString(3, login);
        statement.setInt(4, id);
        return statement.execute();
    }

    @Override
    public User getUserByToken(String token) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM  INNER JOIN \"user\" ON tokens.user_id = \"user\".id WHERE token = ?");
        statement.setString(1, token);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return instance(resultSet);
        }
        return null;
    }


}

