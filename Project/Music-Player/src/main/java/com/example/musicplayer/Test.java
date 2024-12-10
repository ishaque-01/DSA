package com.example.musicplayer;
//  /home/isaacc/Music/BuggedSaver/Citylights - JJ47 x Talha Anjum x Maria Unera (Prod. _umairmusicxx )(MP3_160K).wav


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Test extends Application {

    private MediaPlayer mediaPlayer;
    @Override
    public void start(Stage primaryStage) {
        // Specify the audio file path
//        String audioPath = "file:/home/isaacc/Music/BuggedSaver/Citylights - JJ47 x Talha Anjum x Maria Unera (Prod. _umairmusicxx )(MP3_160K).wav"; // Update with your audio file path

        MediaView mediaView = new MediaView();

        // Create buttons for selecting a file and controlling playback
        Button openButton = new Button("Open Video File");
        Button playButton = new Button("Play");
        Button stopButton = new Button("Stop");
        Label statusLabel = new Label("Status: No video loaded");

        // Set action for the open button
        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Video Files");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp3", "*.wav", "*.mov"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            System.out.println("Selected file: " + selectedFile.toURI().toString());
            if (selectedFile != null) {
                // Load the selected video file
                Media media = new Media(selectedFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                statusLabel.setText("Status: Video loaded - " + selectedFile.getName());
            }
        });

        // Set action for the play button
        playButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        });

        // Set action for the stop button
        stopButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
        });

        // Set up the layout
        VBox root = new VBox(10, openButton, playButton, stopButton, mediaView, statusLabel);
        root.setStyle("-fx-padding: 20;");

        // Create a Scene
        Scene scene = new Scene(root, 800, 600);

        // Set the Scene to the Stage
        primaryStage.setTitle("JavaFX Video Player with FileChooser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}