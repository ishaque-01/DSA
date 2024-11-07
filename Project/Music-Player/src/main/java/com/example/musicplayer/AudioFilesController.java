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
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class AudioFilesController implements Initializable {

    @FXML
    private Button backBtn, addBtn, playBtn;
    @FXML
    private TextField statusField;
    @FXML
    private TextArea filesList;
    @FXML
    private ChoiceBox<String> modes;

    private String modeSelected = "Single-Loop";
    private String[] listModes = {"Select Mode", "Single-Loop", "List-Loop", "Shuffle"};

    private LinkedList<File> list = new LinkedList<>();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        statusField.setFocusTraversable(false);

        modes.getItems().addAll(listModes);
        modes.getSelectionModel().selectFirst();
//        :: method reference operator to link method
        modes.setOnAction(this::getMode);


    }

    public void audioPlayer(ActionEvent e) throws IOException {

        if(!list.isEmpty()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFiles/AudioPlayer.fxml"));
            Parent root = loader.load();

            AudioPlayerController audioPlayerController = loader.getController();
            audioPlayerController.setPlayListAndMode(list, modeSelected);

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

    public void getMode(ActionEvent e) {
        modeSelected = modes.getValue().toString();
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

    private void printOnTextArea(TextArea filesList) {
        filesList.setText("");
        int count = 1;
        StringBuilder listNames = new StringBuilder();
        for (File file : list) {
            listNames.append(count++).append("- ").append(file.getName()).append("\n\n");
        }
        filesList.setText(listNames.toString());
    }

}