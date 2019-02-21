package dao;

import entities.Track;
import helpers.MainHelper;

import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;
import java.util.List;

public class TrackDAO {
    private Connection connection = MainHelper.getConn();

    public Track getTrackById(int id) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from track");
            while (rs.next()) {
                if (rs.getInt("id") == id) {
                    return MainHelper.makeORMTrack(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Track> getAllTracks() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from track");
            return MainHelper.makeTrackORMList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Track> getAllFavorites(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from user_track where user_id=? order by date");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return MainHelper.makeFavoriteTrackORMList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addFavorite(int trackId, int userId) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into user_track (date, track_id, user_id) values ('now', ?,?)");
            statement.setInt(1, trackId);
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTrack(String type, String singer, int year,
                       Part filePart, String path) {
        try {
            PreparedStatement statement = connection.prepareStatement("insert into track (type, singer, year)" +
                    "values (?,?,?) returning id");
            statement.setString(1, type);
            statement.setString(2, singer);
            statement.setInt(3, year);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int trackId = rs.getInt("id");
            if (filePart.getSize() != 0) {
                savePic(filePart, path, trackId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void savePic(Part filePart, String path, int trackId) {
        File file = new File(path + File.separator + trackId);
        file.mkdirs();
        OutputStream out = null;
        InputStream filecontent = null;
        String ext = getFileName(filePart).substring(getFileName(filePart).lastIndexOf(".")).toLowerCase();
        String fileName = trackId + "/" + System.currentTimeMillis() + ext;
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
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("update track set photo_path=? where id=?");
            ps.setString(1, "/files/tracks/" + fileName);
            ps.setInt(2, trackId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


    public void sortTracks(String type, String singer) {
        String conditions = "";
        if (type.length() > 0) {
            conditions += "where c2.id = ?";
        }
        if (singer.length() > 0) {
            if (conditions.length() > 0) {
                conditions += "and t.pr = ?";
            } else {
                conditions += "where t.pr = ?";
            }
        }
    }

}
