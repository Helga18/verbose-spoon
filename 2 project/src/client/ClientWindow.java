package client;


import entities.Hero;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.WindowEvent;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ClientWindow {

    private final int step = 15;
    private static final String bulletIcon = "http://icons.iconarchive.com/icons/google/noto-emoji-food-drink/32/32382-hamburger-icon.png";
    private static final String enemyIcon = "http://icons.iconarchive.com/icons/google/noto-emoji-people-stories/64/10932-woman-zombie-icon.png";

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 3444;
    private Socket clientSocket;
    private Scanner inMessage;
    private PrintWriter outMessage;
    private AnimationTimer animationTimer;

    private ArrayList<Hero> enemies = new ArrayList<>();
    private String clientName = "";
    @FXML
    private TextField answer;
    @FXML
    private ImageView hero;
    @FXML
    private AnchorPane pane;


    public ClientWindow() {
        try {
            clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void enterAction(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && !this.answer.getText().equals("")) {
            clientName = answer.getText();
            this.answer.setVisible(false);
            start();
        }
    }

    private void start() {
        hero.setVisible(true);
        Random random = new Random();
        hero.setLayoutX(random.nextInt(300));
        hero.setLayoutY(random.nextInt(300));
        outMessage.println("newUser:" + clientName + ";" + hero.getLayoutX() + ";" + hero.getLayoutY());
        pane.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        up();
                        break;
                    case DOWN:
                        down();
                        break;
                    case LEFT:
                        left();
                        break;
                    case RIGHT:
                        right();
                        break;
                    case SPACE:
                        System.out.println("shoot");
                        shoot();
                        break;
                    default:
                        System.out.println("wrong key pressed");
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (inMessage.hasNext()) {
                            String message = inMessage.nextLine();
                            String target = message.split(":")[0];
                            String[] parts = message.split(":")[1].split(";");
                            if (target.equals("newUser")) {
                                addNewUser(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                            } else if (target.equals("old")) {
                                addNewUser(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                            } else if (target.equals("move")) {
                                moveEnemy(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                            } else if (target.equals("remove")) {
                                removeEnemy(parts[0]);
                            } else if (target.equals("shoot")){
                                shootEnemy(parts[0], Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void shootEnemy(String name, double x, double y) {
        Hero shootingEnemy = getHeroByName(name);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView bullet = new ImageView(new Image(bulletIcon));
                bullet.setLayoutX(x);
                bullet.setLayoutY(y);
                pane.getChildren().add(bullet);
                new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        bullet.setLayoutY(bullet.getLayoutY()-5);
                    }
                }.start();
            }
        });



    }

    private void shoot() {
        ImageView bullet = new ImageView(new Image(bulletIcon));
        bullet.setLayoutX(hero.getLayoutX());
        bullet.setLayoutY(hero.getLayoutY());
        pane.getChildren().add(bullet);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                bullet.setLayoutY(bullet.getLayoutY()-5);
                if(bullet.getLayoutY()<0){
                    pane.getChildren().removeAll(bullet);
                    System.out.println("out of border");
                    animationTimer.stop();
                }
                System.out.println("checking collision");
                if(isCollided(bullet.getLayoutX(), bullet.getLayoutY())){
                    System.out.println("collision");

                    pane.getChildren().removeAll(bullet);
                    animationTimer.stop();

                }

            }
        };
        animationTimer.start();
        outMessage.println("shoot:"+bullet.getLayoutX()+";"+bullet.getLayoutY());

    }

    private boolean isCollided(double x, double y) {
        for (Hero h: enemies){
            if(Math.abs(h.getView().getLayoutX()-x)<=10&&(Math.abs(h.getView().getLayoutY()-y))<=10){
                outMessage.println("remove:"+h.getName());
                return true;
            }
        }
        return false;
    }

    private void removeEnemy(String name) {
        if(name.equals(clientName)){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pane.getChildren().remove(hero);
                    Label label = new Label("YOU LOST");
                    label.setLayoutX(250);
                    label.setLayoutY(300);
                    pane.getChildren().add(label);
                }
            });
            return;
        }
        System.out.println("it's an enemy");
        Hero deletingEnemy = getHeroByName(name);
        if (deletingEnemy != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pane.getChildren().removeAll(deletingEnemy.getView());
                    enemies.remove(deletingEnemy);
                    if(enemies.size()==0) {
                        Label label = new Label("CONGRATULATIONS");
                        label.setLayoutX(250);
                        label.setLayoutY(300);
                        pane.getChildren().add(label);
                    }
                }
            });
        }
    }

    private void addNewUser(String name, double x, double y) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ImageView enemy = new ImageView(new Image(enemyIcon));
                enemy.setLayoutX(x);
                enemy.setLayoutY(y);
                pane.getChildren().add(enemy);
                enemies.add(new Hero(enemy.getX(), enemy.getY(), name, enemy));
            }
        });
    }

    private void moveEnemy(String name, double x, double y) {
        Hero movingEnemy = getHeroByName(name);
        if (movingEnemy != null) {
            movingEnemy.setX(x);
            movingEnemy.setY(y);
            movingEnemy.getView().setLayoutX(movingEnemy.getX());
            movingEnemy.getView().setLayoutY(movingEnemy.getY());
        }
    }

    private Hero getHeroByName(String name) {
        for (Hero hero : enemies) {
            if (hero.getName().equals(name)) return hero;
        }
        return null;
    }

    private void up() {
        double resultY = hero.getLayoutY() - step;
        if (resultY < 0) resultY = 0;
        hero.setLayoutY(resultY);
        outMessage.println("move:" + hero.getLayoutX() + ";" + hero.getLayoutY());
    }

    private void down() {
        double resultY = hero.getLayoutY() + step;
        if (resultY > pane.getHeight() - 48) resultY = pane.getHeight() - 48;
        hero.setLayoutY(resultY);
        outMessage.println("move:" + hero.getLayoutX() + ";" + hero.getLayoutY());
    }

    private void left() {
        double resultX = hero.getLayoutX() - step;
        if (resultX < 0) resultX = 0;
        hero.setLayoutX(resultX);
        outMessage.println("move:" + hero.getLayoutX() + ";" + hero.getLayoutY());
    }

    private void right() {
        double resultX = hero.getLayoutX() + step;
        if (resultX > pane.getWidth() - 48) resultX = pane.getWidth() - 48;
        hero.setLayoutX(resultX);
        outMessage.println("move:" + hero.getLayoutX() + ";" + hero.getLayoutY());
    }


    private EventHandler<WindowEvent> closeEventHandler = new EventHandler<WindowEvent>() {
        @Override
        public void handle(WindowEvent event) {
            outMessage.println("remove: nameOfClient;" );
        }
    };

    public EventHandler<WindowEvent> getCloseEventHandler() {
        return closeEventHandler;
    }
}
