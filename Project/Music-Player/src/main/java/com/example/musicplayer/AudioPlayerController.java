package com.example.musicplayer;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
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
import java.util.*;

public class AudioPlayerController implements Initializable {

    private Media media;
    private MediaPlayer mediaPlayer;
    private File currentFile;
    int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private RotateTransition rotate;
    private LinkedList<File> list = new LinkedList<>();
    private String mode = "";
    private boolean isPlaying = true;
    private Timer timer;
    private TimerTask task;

    @FXML
    private ImageView logo, playPause;
    @FXML
    private Button prevBtn, nextBtn, playBtn, backBtn, playingQueue, shuffle;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volume;
    @FXML
    private Label fileName, runningTime, endTime, playLabel;
    @FXML
    private ProgressBar progressBar;

    double volumeValue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rotate = new RotateTransition();
        rotate.setNode(logo);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setDuration(Duration.seconds(6));
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.play();
        
        playPause.setImage(new Image("pause.png"));
        playLabel.setText("Pause");
        for (int speed : speeds) {
            speedBox.getItems().add(speed + "%");
        }

        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(mediaPlayer.isMute()) {
                    mediaPlayer.setMute(false);
                }
                mediaPlayer.setVolume(newValue.doubleValue() * 0.01);
                volumeValue = newValue.doubleValue() * 0.01;
            }
        });

        volume.setOnKeyReleased(e -> {
            volume.getParent().requestFocus();
        });

        startTime();

        progressBar.setOnMousePressed(event -> {
            double xAxis = event.getX();
            double barWidth = progressBar.getWidth();
            double progress = xAxis / barWidth;
            double duration = media.getDuration().toSeconds();
            double newTime = progress * duration;
            mediaPlayer.seek(Duration.seconds(newTime));
            progressBar.setProgress(progress);
        });

        Platform.runLater(() -> {
            progressBar.getScene().setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case M:
                        mediaPlayer.setMute(!mediaPlayer.isMute());
                        break;
                    case N:
                        nextMedia(null);
                        break;
                    case P:
                        prevMedia(null);
                        break;
                    case SPACE:
                        playPauseMedia(null);
                        break;
                    case UP:
                        adjustVolume("up");
                        break;
                    case DOWN:
                        adjustVolume("down");
                        break;
                    case LEFT:
                        mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() - 10));
                        break;
                    case RIGHT:
                        mediaPlayer.seek(Duration.seconds(mediaPlayer.getCurrentTime().toSeconds() + 10));
                        break;
                }
            });
        });

        backBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);
        prevBtn.setFocusTraversable(false);
        playBtn.setFocusTraversable(false);
        speedBox.setFocusTraversable(false);
        volume.setFocusTraversable(false);
        playingQueue.setFocusTraversable(false);
        shuffle.setFocusTraversable(false);
    }

    public void setPlayListAndMode(LinkedList<File> list, String mode) {
        this.list = list;
        this.mode = mode;

        switch (mode) {
            case "List-Loop":
                playList(list);
                break;
            case "Single-Loop":
                playSingle(list);
                break;
            case "Shuffle":
                playShuffle(list);
        }
    }

    public void playSingle(LinkedList<File> list) {
        currentFile = list.getFirst();
        playCurrentFile(currentFile);
    }

    public void playList(LinkedList<File> list) {
        currentFile = list.getFirst();
        playCurrentFile(currentFile);
    }

    public void playShuffle(LinkedList<File> list) {
        LinkedList<File> shuffledList = new LinkedList<>(list);
        Collections.shuffle(shuffledList);
        for (File f:shuffledList) {
            System.out.println(f.getName());
        }
        currentFile = list.getFirst();
        playCurrentFile(currentFile);
    }

    private void playCurrentFile(File file) {
        media = new Media(currentFile.toURI().toString());
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        mediaPlayer = new MediaPlayer(media);
        fileName.setText(currentFile.getName());
        mediaPlayer.play();

        rotate.jumpTo(Duration.millis(0));
        startTime();
        changeSpeed(null);
        mediaPlayer.setVolume(volume.getValue() * 0.01);

        mediaPlayer.setOnReady(() -> {
            double end = media.getDuration().toSeconds();
            int minutes = (int) (end / 60);
            int seconds = (int) (end % 60);
            endTime.setText(String.format("%02d:%02d", minutes, seconds));
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            if(mode.equals("Single-Loop")) {
              mediaPlayer.seek(Duration.ZERO);
              mediaPlayer.play();
              rotate.jumpTo(Duration.millis(0));
            } else {
                int currIdx = list.indexOf(currentFile);
                if (currIdx == list.size() - 1) {
                    currentFile = list.getFirst();
                } else {
                    currentFile = list.get(currIdx + 1);
                }
                playCurrentFile(currentFile);
            }
        });
    }

    public void nextMedia(ActionEvent e) {
        isPlaying = true;
        playPause.setImage(new Image("pause.png"));
        playLabel.setText("Pause");
        rotate.play();

        int currIdx = list.indexOf(currentFile);
        if(currIdx == list.size() - 1) {
            currentFile = list.getFirst();
        } else {
            currentFile = list.get(currIdx + 1);
        }
        playCurrentFile(currentFile);
    }

    public void prevMedia(ActionEvent e) {
        isPlaying = true;
        playPause.setImage(new Image("pause.png"));
        playLabel.setText("Pause");
        rotate.play();

        int currIdx = list.indexOf(currentFile);
        if(currIdx == 0) {
            currentFile = list.getLast();
        } else {
            currentFile = list.get(currIdx - 1);
        }
        playCurrentFile(currentFile);
    }

    public void goingBack(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioFiles.fxml"));
        Parent root = loader.load();
        AudioFilesController afc = loader.getController();
        afc.printOnTextArea(list);
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeSpeed(ActionEvent e) {
        if (speedBox.getValue() != null) {
            double speed = Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01;
            mediaPlayer.setRate(speed);
        }
    }

    public void playPauseMedia(ActionEvent e) {
        if (isPlaying) {
            Image icon = new Image("play.png");
            playPause.setImage(icon);
            rotate.pause();
            cancelTimer();
            mediaPlayer.pause();
            playLabel.setText("PLAY");
            isPlaying = false;
        } else {
            Image icon = new Image("pause.png");
            playPause.setImage(icon);
            rotate.play();
            mediaPlayer.play();
            playLabel.setText("PAUSE");
            isPlaying = true;
        }
    }

    public void showPlayList(ActionEvent e) {
        System.out.println("Showing List");
    }

    public void changeMode(ActionEvent e) {
        System.out.println("Changed Mode!");
    }

    public void startTime() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
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

    public void adjustVolume(String value) {
        if(mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
        }
        double currVol = mediaPlayer.getVolume();
        double updatedVol = 0.0;
        if(value.equalsIgnoreCase("up")) {
            updatedVol = Math.min(currVol + 0.1, 1.0);
        } else if(value.equalsIgnoreCase("down")) {
            updatedVol = Math.max(currVol - 0.1, 0.0);
        }
        mediaPlayer.setVolume(updatedVol);
        volumeValue = updatedVol;
        volume.setValue(updatedVol * 100);
    }
}