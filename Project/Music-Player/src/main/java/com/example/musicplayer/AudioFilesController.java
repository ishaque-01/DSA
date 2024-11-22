package com.example.musicplayer;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AudioFilesController implements Initializable {

    @FXML
    private Button backBtn, addBtn, playBtn, createBtn;
    @FXML
    private TextField statusField, playListName,  playlistStatus;
    @FXML
    private TextArea filesList;
    @FXML
    private ChoiceBox<String> modes;

    private LinkedList<File> list = new LinkedList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;

    public AudioFilesController(LinkedList<File> list) {
        this.list = list;
    }

    public AudioFilesController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statusField.setFocusTraversable(false);

        modes.getItems().add("No Playlist Available!");
//        modes.getSelectionModel().selectFirst();
//        :: method reference operator to link method
        modes.setOnAction(e -> {
            try {
                getPlaylist(e);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        updateModes();

        modes.setFocusTraversable(false);
        backBtn.setFocusTraversable(false);
        addBtn.setFocusTraversable(false);
        playBtn.setFocusTraversable(false);
        statusField.setFocusTraversable(false);
        filesList.setFocusTraversable(false);
        createBtn.setFocusTraversable(false);
        playListName.setFocusTraversable(false);
        playlistStatus.setFocusTraversable(false);
    }


    public void audioPlayer(ActionEvent e) throws IOException {

        if (!list.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioPlayer.fxml"));
            Parent root = loader.load();

            AudioPlayerController audioPlayerController = loader.getController();
            audioPlayerController.setPlayListAndMode(list, "List-Loop");

            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            statusField.setText("No Audio Files Selected");
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> statusField.setText("Waiting For File....!!!"));
            delay.play();
        }
    }

    public void getPlaylist(ActionEvent e) throws FileNotFoundException {
        list.clear();
        if(!modes.getValue().equalsIgnoreCase("playlist available") && !modes.getValue().equalsIgnoreCase("No Playlists Available")) {
            String path = "Playlists/" + modes.getValue();
            filesList.setText("");
            StringBuilder sb = new StringBuilder();
            File listFile = new File(path);
            Scanner scan = new Scanner(listFile);
            int count = 1;
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                int startIdx = line.indexOf("Music/");
                File playlistFile = new File(line);
                list.add(playlistFile);
                sb.append(count++).append("- ").append(line.substring(startIdx + 6, line.length() - 1)).append("\n\n");
            }
            scan.close();
            filesList.setText(sb.toString());
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        System.out.println("Changing scene");
        root = FXMLLoader.load(getClass().getResource("FXMLFiles/Main.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("MainStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void addFiles(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Audio Files");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac", "*.m4a", "*.flac")
        );

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

    public void createPlaylist(ActionEvent e) {
        if(playListName.getText().equalsIgnoreCase("") || list.isEmpty()) {
            playlistStatus.setText("Please Enter File Name! or Select Song First!");
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> playlistStatus.setText(""));
            delay.play();
        } else {
            String path = "Playlists/" + playListName.getText();
            File newFile = new File(path);

            File directory = newFile.getParentFile();
            if(!directory.exists()){
                directory.mkdirs();
            }

            try {
                if(newFile.createNewFile()) {
                    playlistStatus.setText("Playlist Created!");
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(event -> playlistStatus.setText(""));
                    delay.play();
                } else {
                    playlistStatus.setText("Playlist Updated!");
                    PauseTransition delay = new PauseTransition(Duration.seconds(2));
                    delay.setOnFinished(event -> playlistStatus.setText(""));
                    delay.play();
                }
                FileWriter writer = new FileWriter(newFile);
                for(File f:list) {
                    writer.write( f.getAbsolutePath());
                }
                writer.close();
            } catch (IOException io) {
                System.err.println("Error Occurred");
            } finally {
                updateModes();
            }
        }
    }

    public void updateModes() {
        modes.getItems().clear();

        File myDirectory = new File("Playlists");

        if(myDirectory.exists() && myDirectory.isDirectory()) {
            for(File f: Objects.requireNonNull(myDirectory.listFiles())) {
                modes.getItems().add(f.getName());
            }
        }
        if(modes.getItems().isEmpty()) {
            modes.getItems().add("No Playlists Available");
        } else {
            modes.getItems().addFirst("PlayList Available");
        }
        modes.getSelectionModel().selectFirst();
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