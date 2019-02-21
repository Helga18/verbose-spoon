package entities;

public class Track {
    private int id;
    private String type;
    private int year;
    private String singer;
    private String photo_path;


    public Track(int id, String type, String singer, int year) {
        this.id = id;
        this.singer = singer;
        this.type = type;
        this.year = year;
        this.photo_path = photo_path;
    }

    public int getId() {
        return id;
    }


    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }


    public String getSinger() {
        return singer;
    }

    public String getPhoto_path() {
        return photo_path;
    }
}
