package models;

import javafx.scene.Node;

public class Hero {
    private String name;
    private double x;
    private double y;

    private Node view;



    public Node getView() {
        return view;
    }

    public Hero(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public Hero(double x, double y, String name, Node view) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.view = view;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 5);
    }
}
