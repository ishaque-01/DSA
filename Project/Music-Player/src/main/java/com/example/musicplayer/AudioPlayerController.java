package com.example.musicplayer;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
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

    @FXML
    private ImageView logo, playPause;
    @FXML
    private Button prevBtn, playBtn, nextBtn, goBack, playingQueue;
    @FXML
    private Slider volume;
    @FXML
    private Label playLabel, fileName, runningTime, endTime;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private ProgressBar progressBar;

    Stage stage;

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
        mediaPlayer.setOnReady(() -> {
            double durationInSeconds = mediaPlayer.getMedia().getDuration().toSeconds();
            int minutes = (int) durationInSeconds / 60;
            int seconds = (int) durationInSeconds % 60;
            endTime.setText(String.format("%d:%02d", minutes, seconds));
        });
        fileName.setText(currentFile.getName());
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
//        if (mediaPlayer.getMedia().getMetadata().get("image") != null) {
//            Image thumbnail = (Image) mediaPlayer.getMedia().getMetadata().get("image");
//            logo.setImage(thumbnail);
//        }

        mediaPlayer = new MediaPlayer(media);
        fileName.setText(currentFile.getName());
        Image icon = new Image("play.png");
        playPause.setImage(icon);
        playLabel.setText("PAUSE");
        isPlaying = true;
        rotate.jumpTo(Duration.millis(0));
        rotate.play();
        mediaPlayer.play();
        startingTime();

        mediaPlayer.setOnReady(() -> {
            double durationInSeconds = mediaPlayer.getMedia().getDuration().toSeconds();
            int minutes = (int) durationInSeconds / 60;
            int seconds = (int) durationInSeconds % 60;
            endTime.setText(String.format("%d:%02d", minutes, seconds));
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            int currentIndex = list.indexOf(currentFile);
            if (currentIndex == list.size() - 1) {
                currentFile = list.getFirst();
            } else {
                currentFile = list.get(currentIndex + 1);
            }
            playFile(currentFile);
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
            Image icon = new Image("play.png");
            playPause.setImage(icon);
            playLabel.setText("PLAY");
            isPlaying = false;
        } else {
//            changeSpeed(null);
            mediaPlayer.setVolume(volume.getValue() * 0.01);
            startingTime();
            rotate.play();
            mediaPlayer.play();
            Image icon = new Image("pause.png");
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
        rotate.jumpTo(Duration.millis(0));
        rotate.play();
        mediaPlayer.play();
        startingTime();
        fileName.setText(currentFile.getName());
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

        fileName.setText(currentFile.getName());
        if (mode.equals("Single-Loop")) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        Image icon = new Image("play.png");
        playPause.setImage(icon);
        playLabel.setText("PAUSE");
        isPlaying = true;
        mediaPlayer.play();
        rotate.jumpTo(Duration.millis(0));
        rotate.play();
        startingTime();
        fileName.setText(currentFile.getName());
    }

    public void startingTime() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    double current = mediaPlayer.getCurrentTime().toSeconds();
                    double end = media.getDuration().toSeconds();
                    progressBar.setProgress(current / end);

                    int minutes = (int) current / 60;
                    int seconds = (int) current % 60;
                    runningTime.setText(String.format("%d:%02d", minutes, seconds));

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

    public void goingBack(ActionEvent e) throws IOException {
        System.out.println("Going back");
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioFiles.fxml"));
        Parent root = loader.load();
        AudioFilesController afc = loader.getController();
        afc.updateTextArea(list);
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("AudioStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void showPlayList(ActionEvent e) {
        System.out.println("showPlayList");
    }

    public void changeSpeed(ActionEvent e) throws IOException {
        mediaPlayer.setRate(Integer.parseInt(speedBox.getValue().substring(0,speedBox.getValue().length() - 1)) * 0.01);
    }
}