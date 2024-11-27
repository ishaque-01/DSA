package com.example.musicplayer;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
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

import javax.swing.JOptionPane;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AudioFilesController implements Initializable {

    @FXML
    private Button backBtn, addBtn, playBtn, createBtn, addPlaylist;
    @FXML
    private TextField statusField, playListName, playlistStatus;
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
        modes.getSelectionModel().selectFirst();
//        :: method reference operator to link method
        modes.setOnAction(event -> {
            try {
                getPlaylist(event);
            } catch (IOException exception) {
                System.err.println("Error: " + exception);
            }
        });
        updateMode();

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


    public void updateMode() {
        File directory = new File("Playlists");
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
    }

    public void getPlaylist(ActionEvent e) throws FileNotFoundException {
        addPlaylist.setDisable(false);
        list.clear();
        filesList.setText("");
        if (!modes.getValue().equalsIgnoreCase("No Playlist Available!") && !modes.getValue().equalsIgnoreCase("Playlists Available")) {
            String path = "Playlists/" + modes.getValue();
            File playlist = new File(path);
            if (playlist.exists()) {
                Scanner scan = new Scanner(playlist);
                StringBuilder sb = new StringBuilder();
                int count = 1;
                while (scan.hasNextLine()) {
                    File newFile = new File(scan.nextLine());
                    if (!list.contains(newFile)) {
                        list.add(newFile);
                        int sub = 6 + newFile.getAbsolutePath().indexOf("Music/");
                        sb.append(count++).append("- ").append(newFile.getAbsolutePath().substring(sub)).append("\n\n");
                    }
                    filesList.setText(sb.toString());
                }
            }
        } else {
            System.out.println("In Else Part");
            System.out.println("list.isEmpty(): " + list.isEmpty());
            printOnTextArea(list);
        }

    }

    // -------------------------------In Process------------------------------------------------
    // Add Button in Process

    public void addInPlaylist(ActionEvent e) throws IOException {
//        String playlistName = modes.getValue();
//        File playlist = new File("Playlists/" + playlistName);
//        Scanner scan = new Scanner(playlist);
//        String currFile = "";
//        while(scan.hasNextLine()) {
//            currFile = scan.nextLine();
//        }
//        System.out.println("currFile: " + currFile);
//        int idx = list.indexOf(new File(currFile));
//        System.out.println("idx: " + idx);
//        PrintWriter writer = new PrintWriter(playlist);
//        for(File f:list) {
//            System.out.println("idx: " + list.indexOf(f));
//            if(idx < list.indexOf(f)) {
//                writer.println(f.getAbsolutePath());
//            }
//        }
//        writer.close();
    }

    // Create Button in process
    public void createPlaylist(ActionEvent e) {
        String playlistName = playListName.getText();
        String filesName = filesList.getText();
        if (playlistName.isBlank() || filesName.isBlank()) {
            showErrorMessage(playlistName, filesName);
            return;
        }
        String path = "Playlists/" + playlistName;
        File playlist = new File(path);
        if (playlist.exists()) {
            boolean checking = checkList(playlist);
            if (checking) {
                int answer = JOptionPane.showConfirmDialog(null, "Do you want to update playlist?", "Playlist Update Confirmation", JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    writeInPlaylist(playlist);
                    playlistStatus.setText("Playlist Updated!");
                }
            } else {
                playlistStatus.setText("Nothing to add in playlist!");
            }

        }
        try {
            if (playlist.createNewFile()) {
                writeInPlaylist(playlist);
                playlistStatus.setText("Playlist Successfully Created!");
            }
        } catch (IOException io) {
            System.err.println("Error in creating playlist");
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            playlistStatus.setText("");
            playListName.setText("");
        });
    }

    public boolean checkList(File playlist) {

        try {
            Scanner scan = new Scanner(playlist);
            int count = 0;
            while (scan.hasNextLine()) {
                count++;
            }
            scan.close();
            if (count < list.size()) return true;
        } catch (IOException e) {
            System.err.println("Error in reading playlist!");
        }
        return false;
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

    // -------------------------------In Process------------------------------------------------

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

    public void goBack(ActionEvent e) throws IOException {
        System.out.println("Changing scene");
        root = FXMLLoader.load(getClass().getResource("FXMLFiles/Main.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void addFiles(ActionEvent e) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Audio Files");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac", "*.m4a", "*.flac"));

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