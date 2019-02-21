package dao;

import entities.User;
import helpers.MainHelper;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;

public class SimpleUserDAO {

    private Connection connection = MainHelper.getConn();
    public User getUserByLogin(String login) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from \"user\"");
            while (rs.next()) {
                if (rs.getString("login").equals(login)) {
                    return MainHelper.makeORMUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User getById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from \"user\"");
            while (rs.next()) {
                if (rs.getInt("userid") == id) {
                    return MainHelper.makeORMUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addUser(String username, String login, String password, String age, String year, Part filePart, String path) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into \"user\"(name, login, password, age, year) values (?,?,?,?,?) returning userid");
            ps.setString(1, username);
            ps.setString(2, login);
            ps.setString(3, password);
            ps.setString(4, age);
            ps.setString(5, year);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int userId = rs.getInt("userid");
            if (filePart.getSize() != 0) {
                savePic(filePart, path, userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String newName, String newLogin, String newPassword, String newAge, String newYear, int userid) {
        try {
            PreparedStatement ps = connection.prepareStatement("update \"user\" set name=?, login=?, password=?,age = ?, year = ? where userid=?");
            ps.setString(1, newName);
            ps.setString(2, newLogin);
            ps.setString(3, newPassword);
            ps.setString(4, newAge);
            ps.setString(5, newYear);
            ps.setInt(6, userid);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void savePic(Part filePart, String path, int userId) {
        File file = new File(path + File.separator + userId);
        file.mkdirs();
        OutputStream out = null;
        InputStream filecontent = null;
        String ext = getFileName(filePart).substring(getFileName(filePart).lastIndexOf(".")).toLowerCase();
        String fileName = userId + "/" + System.currentTimeMillis() + ext;
        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            PreparedStatement ps = connection.prepareStatement("update \"user\" set photo_path=? where id=?");
            ps.setString(1, "/files/users/" + fileName);
            ps.setInt(2, userId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public void updatePhoto(int userId, Part filePart, String path) {
        if (filePart.getSize() != 0) {
            File file = new File(path + File.separator + userId);
            if (file.length() != 0) {
                File[] files = file.listFiles();
                for (File f : files) {
                    f.delete();
                }
                file.delete();
            }
            savePic(filePart, path, userId);
        }

    }
}
