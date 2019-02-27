package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class CustomMain extends Application {

    private boolean dirty = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setHgap(1);
        root.setVgap(2);
        root.setPadding(new Insets(5));


        TextArea ta = new TextArea();
        ta.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            dirty = true;
        });

        root.add(ta, 0, 0);

        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(85);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(15);
        root.getRowConstraints().addAll(r1, r2);

        GridPane buttonrow = new GridPane();
        buttonrow.setHgap(3);
        buttonrow.setVgap(1);

        root.add(buttonrow, 0, 1);

        Button exit = new Button("Exit");
        exit.setOnAction(e -> onExit(e));

        Button save = new Button("Save");
        save.setOnAction(e -> {
            if (dirty) {
                FileChooser fc = new FileChooser();
                FileChooser.ExtensionFilter extFilter =
                        new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
                fc.getExtensionFilters().add(extFilter);

                File file = fc.showSaveDialog(null);
                if (file != null) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(ta.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        Button load = new Button("Load");
        load.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);
            if (file != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))){
                    ta.setText("");
                    while (br.ready()) {
                        ta.appendText(br.readLine() + "\n");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });


        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(33);

        GridPane.setHalignment(exit, HPos.CENTER);
        GridPane.setHalignment(save, HPos.CENTER);
        GridPane.setHalignment(load, HPos.CENTER);

        buttonrow.add(exit, 0, 0);
        buttonrow.add(save, 1, 0);
        buttonrow.add(load, 2, 0);

        buttonrow.getColumnConstraints().addAll(c, c, c);

        Scene scene = new Scene(root, 400, 400);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    private void onExit(ActionEvent event) {
        if (dirty) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Sie haben ungespeicherte Änderungen.");
            alert.setContentText("Sind Sie sicher, dass die das Programm schließen wollen?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
}
