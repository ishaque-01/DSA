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
    @FXML
    private Button audioBtn, videoBtn, exitBtn;
    @FXML
    private Stage stage;

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

        audioBtn.setFocusTraversable(false);
        videoBtn.setFocusTraversable(false);
        exitBtn.setFocusTraversable(false);
    }

    public void audio(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioFiles.fxml"));
        Parent root = loader.load();
        AudioFilesController afc = loader.getController();
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void video(ActionEvent e) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/VideoFiles.fxml"));
            Parent root = loader.load();
            VideoFilesController vfc = loader.getController();
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void exit(ActionEvent e) {
        System.exit(0);
    }
}
