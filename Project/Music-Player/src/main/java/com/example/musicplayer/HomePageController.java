package com.example.musicplayer;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private ImageView logoImg;
    private Button audioBtn, videoBtn, exitBtn;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void audio(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLFiles/AudioFiles.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("AudioStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void video(ActionEvent e) {
        System.out.println("video");
    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(logoImg);
        rotate.setByAngle(360);
        rotate.setDuration(Duration.millis(4000));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.play();
    }
}
