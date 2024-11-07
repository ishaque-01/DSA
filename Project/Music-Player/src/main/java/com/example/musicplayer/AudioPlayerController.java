package com.example.musicplayer;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collections;

public class AudioPlayerController implements Initializable {
//Next Icon --->>> https://www.freepik.com/icon/next_6497576#fromView=search&page=2&position=8&uuid=c4e79157-cb64-422a-acf8-81fba8d178f7
//Prev Icon --->>>

    @FXML
    private ImageView logo, playPause;
    @FXML
    private Button prevBtn, playBtn, nextBtn;
    @FXML
    private Slider volume;
    @FXML
    private Label playLabel;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ProgressBar progressBar;


    int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private LinkedList<File> list = new LinkedList<>();
    private Boolean isPlaying = true;
    private String mode = "";
    private File currentFile;
    private Timer timer;
    private TimerTask task;
    private Media media;
    private MediaPlayer mediaPlayer;
    RotateTransition rotate;
    private int currIdx;

    public AudioPlayerController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rotate = new RotateTransition();
        rotate.setNode(logo);
        rotate.setByAngle(360);
        rotate.setDuration(Duration.millis(4000));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();

        for (int speed : speeds) {
            speedBox.getItems().add(Integer.toString(speed)+ "%");
        }
        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volume.getValue() * 0.01);
            }
        });
        startingTime();
        progressBar.setOnMousePressed(event -> {
            double mouseX = event.getX();
            double progressBarWidth = progressBar.getWidth();
            double progress = mouseX / progressBarWidth;
            double duration = media.getDuration().toSeconds();
            double newTime = progress * duration;
            mediaPlayer.seek(Duration.seconds(newTime));
            progressBar.setProgress(progress);
        });

        progressBar.widthProperty().addListener((observable, oldValue, newValue) -> {
            progressBar.setOnMousePressed(event -> {
                double mouseX = event.getX();
                double progressBarWidth = newValue.doubleValue();
                double progress = mouseX / progressBarWidth;
                double duration = media.getDuration().toSeconds();
                double newTime = progress * duration;
                mediaPlayer.seek(Duration.seconds(newTime));
                progressBar.setProgress(progress);
            });
        });
    }

    public void setPlayListAndMode(LinkedList<File> list, String mode) {
        this.list = list;
        this.mode = mode;
        switch (mode) {
            case "Single-Loop":
                playSingle(list);
                break;
            case "List-Loop":
                playList(list);
                break;
            case "Shuffle":
                playShuffle(list);
                break;
            default:
                System.out.println("Please Choose");
                break;
        }
    }

    private void playSingle(LinkedList<File> list) {
        currentFile = list.getFirst();
        media = new Media(currentFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        if (mediaPlayer.getMedia().getMetadata().get("image") != null) {
            Image thumbnail = (Image) mediaPlayer.getMedia().getMetadata().get("image");
            logo.setImage(thumbnail);
        }
    }

    public void playList(LinkedList<File> list) {
        currentFile = list.getFirst();
        playFile(currentFile);
    }

    public void playShuffle(LinkedList<File> list) {
        LinkedList<File> shuffled = new LinkedList<>(list);
        Collections.shuffle(shuffled);
        currentFile = shuffled.getFirst();
        playFile(currentFile);
    }

    private void playFile(File file) {
        media = new Media(file.toURI().toString());
        if (mediaPlayer.getMedia().getMetadata().get("image") != null) {
            Image thumbnail = (Image) mediaPlayer.getMedia().getMetadata().get("image");
            logo.setImage(thumbnail);
        }
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(media);
        Image icon = new Image("play.png");
        playPause.setImage(icon);
        playLabel.setText("PAUSE");
        isPlaying = true;
        rotate.play();
        mediaPlayer.play();
        startingTime();
        mediaPlayer.setOnEndOfMedia(() -> {
            nextMedia(null);
        });
        if (mediaPlayer != null) {
            mediaPlayer.play();
            startingTime();
        } else {
            System.out.println("Failed to initialize mediaPlayer");
        }
    }

    public void playMedia(ActionEvent e) {
        if(isPlaying) {
            cancelTimer();
            rotate.pause();
            mediaPlayer.pause();
            Image icon = new Image("pause.png");
            playPause.setImage(icon);
            playLabel.setText("PLAY");
            isPlaying = false;
        } else {
//            changeSpeed(null);
            mediaPlayer.setVolume(volume.getValue() * 0.01);
            startingTime();
            rotate.play();
            mediaPlayer.play();
            Image icon = new Image("play.png");
            playPause.setImage(icon);
            playLabel.setText("PAUSE");
            isPlaying = true;
        }
    }

    public void prevMedia(ActionEvent e) {
        int currentIndex = list.indexOf(currentFile);
        if (currentIndex == 0) {
            currentFile = list.getLast();
        } else {
            currentFile = list.get(currentIndex - 1);
        }

        media = new Media(currentFile.toURI().toString());
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);

        if (mode.equals("Single-Loop")) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        Image icon = new Image("play.png");
        playPause.setImage(icon);
        playLabel.setText("PAUSE");
        isPlaying = true;
        rotate.play();
        mediaPlayer.play();
        startingTime();
    }

    public void nextMedia(ActionEvent e) {
        int currentIndex = list.indexOf(currentFile);
        if (currentIndex == list.size() - 1) {
            currentFile = list.getFirst();
        } else {
            currentFile = list.get(currentIndex + 1);
        }

        media = new Media(currentFile.toURI().toString());
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);

        if (mode.equals("Single-Loop")) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        Image icon = new Image("play.png");
        playPause.setImage(icon);
        playLabel.setText("PAUSE");
        isPlaying = true;
        mediaPlayer.play();
        rotate.play();
        startingTime();
    }

    public void startingTime() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    double end = media.getDuration().toSeconds();
                    progressBar.setProgress(current / end);
                    if (current / end >= 1) {
                        cancelTimer();
                        progressBar.setProgress(0.0);
                    }
                });
            }
        };
//        delay = 1sec after 1 sec timer += 1sec
        timer.schedule(task, 1000, 1000);
    }

    public void cancelTimer() {
        isPlaying = false;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void changeSpeed(ActionEvent e) throws IOException {
        mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0,speedBox.getValue().length() - 1)) * 0.01);
    }
}