package com.example.musicplayer;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayerController implements Initializable {

    private Media media;
    private MediaPlayer mediaPlayer;
    private File currentFile;
    int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    private RotateTransition rotate;
    private LinkedList<File> list = new LinkedList<>();
    private String mode = "", highlightFile = "";
    private boolean isPlaying = true;
    private Timer timer;
    private TimerTask task;
    double volumeValue;

    @FXML
    private Button backBtn, shuffle, prevBtn, playBtn, nextBtn, playingQueue, exitQueue;
    @FXML
    private Label switchMode, fileName, endTime, runningTime;
    @FXML
    private ImageView playPause;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volume;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Pane listPane;
    @FXML
    private ListView<String> playingList;
    @FXML
    private MediaView mediaView;


    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        playPause.setImage(new Image("pause.png"));
        for (int speed : speeds) {
            speedBox.getItems().add(speed + "%");
        }

        volume.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (mediaPlayer.isMute()) {
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

        playingList.setStyle("-fx-background-color: #2F2F2F; -fx-text-fill: #e5e5e5;");
        playingList.setCellFactory(listView -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (item.equals(highlightFile)) {
                        setStyle("-fx-background-color:  #2F2F2F; -fx-text-fill:  #e5e5e5;");
                    } else {
                        setStyle("-fx-background-color:  #161617; -fx-text-fill:  #e5e5e5;");
                    }
                }
            }
        });
        listPane.setVisible(false);

        backBtn.setFocusTraversable(false);
        nextBtn.setFocusTraversable(false);
        prevBtn.setFocusTraversable(false);
        playBtn.setFocusTraversable(false);
        speedBox.setFocusTraversable(false);
        volume.setFocusTraversable(false);
        playingQueue.setFocusTraversable(false);
        shuffle.setFocusTraversable(false);
        playingList.setFocusTraversable(false);
        listPane.setFocusTraversable(false);
        exitQueue.setFocusTraversable(false);
    }

    public void setPlayListAndMode(LinkedList<File> list, String mode) {
        this.list = list;
        this.mode = mode;

        switchMode.setText(mode);
        PauseTransition delay = new PauseTransition(Duration.seconds(4));
        delay.setOnFinished(e -> {
            switchMode.setVisible(false);
        });
        delay.play();

        for (File f : list) {
            playingList.getItems().add(f.getName());
        }

        switch (mode) {
            case "List-Loop":
                playList(list);
                break;
            case "Single-Loop":
                playSingle(list);
                break;
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

    public void nextMedia (ActionEvent e) {
        isPlaying = true;
        playPause.setImage(new Image("pause.png"));

        int currIdx = list.indexOf(currentFile);
        if (currIdx == list.size() - 1) {
            currentFile = list.getFirst();
        } else {
            currentFile = list.get(currIdx + 1);
        }
        playCurrentFile(currentFile);
    }

    public void playPauseMedia(ActionEvent e) {
        if (isPlaying) {
            Image icon = new Image("play.png");
            playPause.setImage(icon);
            cancelTimer();
            mediaPlayer.pause();
            isPlaying = false;
        } else {
            Image icon = new Image("pause.png");
            playPause.setImage(icon);
            mediaPlayer.play();
            isPlaying = true;
        }
    }

    public void prevMedia(ActionEvent e) {
        isPlaying = true;
        playPause.setImage(new Image("pause.png"));

        int currIdx = list.indexOf(currentFile);
        if (currIdx == 0) {
            currentFile = list.getLast();
        } else {
            currentFile = list.get(currIdx - 1);
        }
        playCurrentFile(currentFile);
    }

    private void playCurrentFile(File file) {
        media = new Media(currentFile.toURI().toString());
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        mediaPlayer = new MediaPlayer(media);
        fileName.setText(currentFile.getName());
        mediaPlayer.play();
//        highlightFile(file.getName());
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
            if (mode.equalsIgnoreCase("Single-Loop")) {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
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

    public void changeSpeed(ActionEvent e) {
        if (speedBox.getValue() != null) {
            double speed = Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01;
            mediaPlayer.setRate(speed);
        }
    }

    public void showPlayList(ActionEvent e) {
        listPane.setVisible(!listPane.isVisible());
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

    public void goingBack(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioFiles.fxml"));
        Parent root = loader.load();
        AudioFilesController afc = loader.getController();
        afc.printOnTextArea(list);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeMode(ActionEvent e) {
        if(switchMode.getText().equalsIgnoreCase("list-loop")) {
            mode = "Single-Loop";
            switchMode.setVisible(true);
            switchMode.setText(mode);
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(event -> {
                switchMode.setVisible(false);
            });
            delay.play();
        } else if(switchMode.getText().equalsIgnoreCase("Single-Loop")) {
            mode = "list-loop";
            switchMode.setVisible(true);
            switchMode.setText(mode);
            PauseTransition delay = new PauseTransition(Duration.seconds(4));
            delay.setOnFinished(event -> {
                switchMode.setVisible(false);
            });
            delay.play();
        }
    }

    public void adjustVolume(String value) {
        if (mediaPlayer.isMute()) {
            mediaPlayer.setMute(false);
        }
        double currVol = mediaPlayer.getVolume();
        double updatedVol = 0.0;
        if (value.equalsIgnoreCase("up")) {
            updatedVol = Math.min(currVol + 0.1, 1.0);
        } else if (value.equalsIgnoreCase("down")) {
            updatedVol = Math.max(currVol - 0.1, 0.0);
        }
        mediaPlayer.setVolume(updatedVol);
        volumeValue = updatedVol;
        volume.setValue(updatedVol * 100);
    }
}
