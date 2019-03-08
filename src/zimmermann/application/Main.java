package zimmermann.application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private GridPane root = new GridPane();
    private Scene scene = new Scene(root, 200, 300);
    private Button exitButton = new Button("Exit");
    private Button loadButton = new Button("Load");
    private Button saveButton = new Button("Save");
    private ChangeListener<String> listener = (e, o, n) -> setDirty();
    private BuzzwordCounter buzzwordCounter = new BuzzwordCounter("Model", "View", "Controller");
    private BuzzwordView buzzwordView = new BuzzwordView(buzzwordCounter.getTotalBuzzwords());

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        wireBeans();

        createExitButton();
        createSaveButton();
        createLoadButton();
        createRoot();

        initPrimaryStage();
    }


    private void wireBeans() {
        buzzwordCounter.getTextProperty().bindBidirectional(buzzwordView.textProperty());
        buzzwordView.counterProperty().bind(buzzwordCounter.getCounterProperty());
    }


    private void createExitButton() {
        exitButton.setOnAction(e -> close());
        GridPane.setHalignment(exitButton, HPos.CENTER);
    }

    private void close() {
        primaryStage.close();
    }

    private void createLoadButton() {
        loadButton.setOnAction(e -> loadFile());
        GridPane.setHalignment(loadButton, HPos.CENTER);
    }

    private void loadFile() {
        FileChooser fileChooser = new FileChooser();
        // Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            loadFromFile(file);
        }
        saveButton.setDisable(true);
        buzzwordCounter.getTextProperty().addListener(listener);
    }

    private void loadFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder textbuffer = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                textbuffer.append(line);
                textbuffer.append('\n');
            }
            buzzwordCounter.getTextProperty().set(textbuffer.toString());
        } catch (IOException e) {
            e.printStackTrace(); // Erbärmliche Fehlerbehandlung
        }
    }

    private void createSaveButton() {
        saveButton.setOnAction(e -> saveFile());
        GridPane.setHalignment(saveButton, HPos.CENTER);
    }

    private void saveFile() {
        FileChooser fileChooser = new FileChooser();
        // Show open file dialog
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveToFile(file);
        }
    }

    private void saveToFile(File file) {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
            writer.write(buzzwordCounter.getTextProperty().get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPrimaryStage() {
        this.primaryStage.setTitle("Hallo Welt");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    private void createRoot() {
        root.setPadding(new Insets(5));
        root.setHgap(10);
        root.setVgap(10);
        ColumnConstraints c1 = new ColumnConstraints();
        c1.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().addAll(c1, c1, c1);
        root.add(buzzwordView, 0, 0, 3, 1);
        GridPane.setVgrow(buzzwordView, Priority.ALWAYS);
        root.add(exitButton, 0, 1);
        root.add(saveButton, 1, 1);
        root.add(loadButton, 2, 1);
    }

    private void setDirty() {
        saveButton.setDisable(false);
        buzzwordCounter.getTextProperty().removeListener(listener);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
