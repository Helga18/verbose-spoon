package client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientWindow clientWindow = new ClientWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../client/gameField.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Shoot");
        primaryStage.getScene().getStylesheets().add("css/style.css");
        primaryStage.setOnCloseRequest(clientWindow.getCloseEventHandler());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
