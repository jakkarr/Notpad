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

public class NotPad extends Application {

    private boolean dirty = false;
    private TextArea ta;
    private BuzzwordCounter bCnt;
    private ProgressBar progressBar;
    private GridPane buttonrow;
    private GridPane root;
    private Scene scene;
    private Button exit;
    private Button save;
    private Button load;
    private Label countLabel;
    private Label bingoLabel;

    public static void main(String[] args) {
        launch(args);
    }

    private void stageInit() {
        root = new GridPane();
        root.setHgap(2);
        root.setVgap(2);
        root.setPadding(new Insets(5));

        createConstraints();

        scene = new Scene(root, 400, 400);
    }

    private void createProgressBar() {
        progressBar = new ProgressBar();
        progressBar.setProgress(0);
    }

    private void createTextArea() {
        ta = new TextArea();
        ta.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            dirty = true;
        });
        ta.prefWidthProperty().bind(scene.widthProperty().subtract(10));
    }

    private void addAllToRoot() {
        root.add(progressBar, 0, 0);
        root.add(ta, 0, 1);
        root.add(buttonrow, 0, 2);

    }

    @Override
    public void start(Stage primaryStage) {

        stageInit();

        createProgressBar();

        createTextArea();

        bCnt = new BuzzwordCounter("Java", "Smalltalk", "Javascript");

        wireBeans();

        createButtonRow();

        ColumnConstraints c = new ColumnConstraints();
        c.setPercentWidth(33);

        GridPane.setHalignment(exit, HPos.CENTER);
        GridPane.setHalignment(save, HPos.CENTER);
        GridPane.setHalignment(load, HPos.CENTER);
        GridPane.setHalignment(countLabel, HPos.CENTER);
        GridPane.setHalignment(bingoLabel, HPos.CENTER);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);
        GridPane.setVgrow(progressBar, Priority.ALWAYS);


        buttonrow.getColumnConstraints().addAll(c, c, c);

        addAllToRoot();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createButtonRow() {
        buttonrow = new GridPane();
        buttonrow.setHgap(3);
        buttonrow.setVgap(2);

        createButtons();
        createLabels();

        buttonrow.add(countLabel, 0,0);
        buttonrow.add(bingoLabel, 2, 0);
        buttonrow.add(exit, 0, 1);
        buttonrow.add(save, 1, 1);
        buttonrow.add(load, 2, 1);
    }

    private void createConstraints() {
        RowConstraints r0 = new RowConstraints();
        r0.setPercentHeight(10);
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(75);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(15);
        root.getRowConstraints().addAll(r0, r1, r2);
    }

    private void createLabels() {

        countLabel = new Label();
        countLabel.textProperty().bind(bCnt.counterProperty().asString());


        bingoLabel = new Label();
        countLabel.textProperty().addListener((obs, oldv, newv) -> {
            if (Integer.parseInt(newv) == bCnt.getTotalBuzzwords()) {
                bingoLabel.setText("BINGO");
            }else {
                bingoLabel.setText("");
            }
        });
    }

    private void createButtons() {
        exit = new Button("Exit");
        exit.setOnAction(e -> onExit(e));

        save = new Button("Save");
        save.setOnAction(e -> save(e));

        load = new Button("Load");
        load.setOnAction(e -> load(e));

    }

    private void wireBeans() {
        bCnt.counterProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldV, Number newV) -> {
            progressBar.setProgress(newV.doubleValue() / bCnt.getTotalBuzzwords());
        });
        ta.textProperty().addListener((ObservableValue<? extends String> observable, String oldV, String newV) -> {
            bCnt.setText(newV);
        });
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

    private void save(ActionEvent event) {
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
    }

    private void load(ActionEvent event) {
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
    }
}

