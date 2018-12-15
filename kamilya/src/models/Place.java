package models;

public class Place {
    private int id;
    private String name;
    private String description;
    private int price;


    public Place(int id, String name,  String description, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }

}
