package com.example.musicplayer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLFiles/Main.fxml"));
            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());

            Image icon = new Image("logo.png");

            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.setTitle("Music Player");
            stage.setResizable(false);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    Platform.exit();
                    System.exit(0);
                }
            });

            Platform.runLater(stage::centerOnScreen);
        } catch (Exception IO) {
            IO.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}