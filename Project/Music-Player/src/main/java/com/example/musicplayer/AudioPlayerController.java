package com.example.musicplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class AudioPlayerController {

    @FXML
    private ImageView logo;

    @FXML
    private Button prevBtn, playBtn, nextBtn;

    @FXML
    private Slider volume;

    private LinkedList<File> list;
    private Boolean isPlaying = true;
    private String mode = "";
//    private File directory;
    private Timer timer;
    private TimerTask task;
    private Media media;
    private MediaPlayer mediaPlayer;


    public AudioPlayerController() {
    }

    public void setPlayListAndMode(LinkedList<File> list, String mode) {
        this.list = list;
        this.mode = mode;
//        if (mode.equalsIgnoreCase("Single-Loop")) {
//            playList();
//        } else if (mode.equalsIgnoreCase("List-Loop")) {
//
//        } else if (mode.equalsIgnoreCase("Shuffle")) {
//
//        }
    }

//    public void playList() {
//        for (File file : list) {
//            currentFile = file;
//            if (currentFile.exists()) {
//                if (currentFile.exists()) {
//                    try {
//                        URI uri = new URI("file", "", currentFile.getAbsolutePath(), "");
//                        System.out.println("Media URI: " + uri.toString());
//                        media = new Media(uri.toString());
//                        mediaPlayer = new MediaPlayer(media);
//                    } catch (URISyntaxException e) {
//                        System.out.println("URISyntaxException: " + e.getMessage());
//                    } catch (Exception e) {
//                        System.out.println("Unexpected Exception: " + e.getMessage());
//                    }
//                } else {
//                    System.out.println("File does not exist.");
//                }
//                System.out.println("Playing: " + file.getName() + "\n\n\n");
//            }
//        }
//    }

    public void playMedia(ActionEvent e) {
        System.out.println("Playing");
    }

    public void pauseMedia(ActionEvent e) {
        System.out.println("Paused");
    }

    public void getFile(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Audio Files");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac", "*.m4a", "*.flac")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());

        if(selectedFile != null) {
            System.out.println("Absolute Path: " + selectedFile.getAbsolutePath());
            System.out.println("getName(): " + selectedFile.getName());
            System.out.println("Selected File: " + selectedFile);
            System.out.println("File exists: " + selectedFile.exists());
            System.out.println("File can read: " + selectedFile.canRead());

            try {
                media = new Media(selectedFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } catch(MediaException ee) {
                System.out.println("MediaException: " + ee.getMessage());
                System.out.println("Cause: " + ee.getCause().getMessage());
            }
        }
    }

    public void prevMedia() {

    }
}
