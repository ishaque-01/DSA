package com.example.musicplayer;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class VideoFilesController implements Initializable {

    @FXML
    private Button backBtn, addBtn, playBtn, createBtn, addPlaylist;
    @FXML
    private TextField statusField, playListName, playlistStatus;
    @FXML
    private TextArea filesList;
    @FXML
    private ChoiceBox<String> modes;

    private LinkedList<File> list = new LinkedList<>();

    private String playlistPath = "VideoPlaylists/";
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {
        modes.getItems().add("No Playlist Available!");
        modes.getSelectionModel().selectFirst();
        modes.setOnAction(event -> {
            String selectedValue = modes.getValue();
            if (selectedValue == null || selectedValue.equals("No Playlists Available") || selectedValue.equals("Playlists Available")) {
                addPlaylist.setDisable(true);
                return;
            } else {
                addPlaylist.setDisable(false);
                try {
                    getPlaylist(event);
                } catch (IOException exception) {
                    System.err.println("Error: " + exception.getMessage());
                }
            }
        });
        updateMode();

        modes.setFocusTraversable(false);
        statusField.setFocusTraversable(false);
        backBtn.setFocusTraversable(false);
        addBtn.setFocusTraversable(false);
        playBtn.setFocusTraversable(false);
        statusField.setFocusTraversable(false);
        filesList.setFocusTraversable(false);
        createBtn.setFocusTraversable(false);
        playListName.setFocusTraversable(false);
        playlistStatus.setFocusTraversable(false);
        addPlaylist.setFocusTraversable(false);
    }

    public void updateMode() {
        File directory = new File(playlistPath);
        if (directory.isDirectory() && directory.exists() && directory.listFiles().length > 0) {
            modes.getItems().clear();
            modes.getItems().add("Playlists Available");
            for (File f : directory.listFiles()) {
                modes.getItems().add(f.getName());
            }
            modes.getSelectionModel().selectFirst();
        } else {
            modes.getItems().clear();
            modes.getItems().add("No Playlists Available");
            modes.getSelectionModel().selectFirst();
        }
        addPlaylist.setDisable(true);
    }

    public void getPlaylist(ActionEvent e) throws FileNotFoundException {
        list.clear();
        filesList.setText("");
        if (!modes.getValue().equalsIgnoreCase("No Playlist Available!") && !modes.getValue().equalsIgnoreCase("Playlists Available")) {
            String path = playlistPath + modes.getValue();
            File playlist = new File(path);
            if (playlist.exists()) {
                addPlaylist.setDisable(false);
                Scanner scan = new Scanner(playlist);
                StringBuilder sb = new StringBuilder();
                int count = 1;
                while (scan.hasNextLine()) {
                    File newFile = new File(scan.nextLine());
                    if (!list.contains(newFile)) {
                        list.add(newFile);
                        sb.append(count++).append("- ").append(newFile.getName()).append("\n\n");
                    }
                    filesList.setText(sb.toString());
                }
            }
        } else {
            addPlaylist.setDisable(true);
        }
    }

    private boolean isRestrictedPlaylistName(String playlistName) {
        String[] restrictedNames = {"Playlists Available", "No Playlists Available"};
        for (String restricted : restrictedNames) {
            if (playlistName.equalsIgnoreCase(restricted)) {
                return true;
            }
        }
        return false;
    }

    public void addInPlaylist(ActionEvent e) throws IOException {
        String currPlaylistPath = playlistPath + modes.getValue();
        FileWriter writer = new FileWriter(currPlaylistPath);
        for (File f : list) {
            writer.write(f.getAbsolutePath() + "\n");
        }
        writer.close();
        playlistStatus.setText("Added in playlist!");
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            playlistStatus.setText("");
            playListName.setText("");
        });
        delay.play();
    }

    public void createPlaylist(ActionEvent e) {
        String playlistName = playListName.getText();
        String filesName = filesList.getText();
        if (isRestrictedPlaylistName(playlistName)) {
            playlistStatus.setText("Choose other name for playlist!");
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                playlistStatus.setText("");
                playListName.setText("");
            });
            delay.play();
            return;
        }
        if (playlistName.isBlank() || filesName.isBlank()) {
            showErrorMessage(playlistName, filesName);
            return;
        }
        String path = playlistPath + playlistName;
        File playlist = new File(path);
        if (playlist.exists()) {
            playlistStatus.setText("Use add button to add new files!");
        } else {
            try {
                if (playlist.createNewFile()) {
                    writeInPlaylist(playlist);
                    playlistStatus.setText("Playlist Successfully Created!");
                }
            } catch (IOException io) {
                System.err.println("Error in creating playlist");
            }
        }
        updateMode();
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            playlistStatus.setText("");
            playListName.setText("");
        });
        delay.play();
    }

    public void writeInPlaylist(File playlist) {
        try {
            FileWriter writer = new FileWriter(playlist);
            for (File f : list) {
                writer.write(f.getAbsolutePath() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error in over-writing");
        }
    }

    public void showErrorMessage(String playlistName, String filesName) {
        if (playlistName.isBlank() && filesName.isBlank()) {
            playlistStatus.setText("Add files and select name of playlist!");
        } else if (playlistName.isBlank() && !filesName.isBlank()) {
            playlistStatus.setText("Select name of playlist!");
        } else {
            playlistStatus.setText("Add files");
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            playlistStatus.setText("");
            playListName.setText("");
        });
        delay.play();
    }

    public void videoPlayer(ActionEvent e) throws IOException {
        if (!list.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/VideoPlayer.fxml"));
            Parent root = loader.load();

            VideoPlayerController videoPlayerController = loader.getController();
            videoPlayerController.setPlayListAndMode(list, "List-Loop");

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            statusField.setText("No Video Files Selected");
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> statusField.setText("Waiting For File....!!!"));
            delay.play();
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLFiles/Main.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addFiles(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Video Files");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4"));

        File selectedFile = fileChooser.showOpenDialog(((Node) e.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            if (!list.contains(selectedFile)) {
                list.add(selectedFile);
                printOnTextArea(filesList);
                statusField.setText("File Added Successfully....!!!");
            } else {
                statusField.setText("File Already Added....!!!");
            }
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> statusField.setText("Waiting For File....!!!"));
            delay.play();
        }
    }

    private void printOnTextArea(TextArea filesList) {
        filesList.setText("");
        int count = 1;
        StringBuilder listNames = new StringBuilder();
        for (File file : list) {
            listNames.append(count++).append("- ").append(file.getName()).append("\n\n");
        }
        filesList.setText(listNames.toString());
    }

    public void printOnTextArea(LinkedList<File> list) {
        this.list = list;
        filesList.setText("");
        int count = 1;
        StringBuilder listNames = new StringBuilder();
        for (File file : list) {
            listNames.append(count++).append("- ").append(file.getName()).append("\n\n");
        }
        filesList.setText(listNames.toString());
    }

}
