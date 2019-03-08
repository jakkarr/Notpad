package MVC;

import javafx.beans.property.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class View extends GridPane {
    private DoubleProperty ratio = new SimpleDoubleProperty();
    private IntegerProperty counter = new SimpleIntegerProperty();
    private ProgressBar progressBar;
    private TextArea textArea;
    private Button exit;
    private Button save;
    private Button load;

    public View() {
        super();
        init();
        createTextArea();
        createProgressBar();
        createButtonRow();

    }

    public void init() {
        ColumnConstraints cc = new ColumnConstraints();
        cc.setPercentWidth(33);
        getColumnConstraints().addAll(cc, cc, cc);
        setPadding(new Insets(5));
        setHgap(3);
        setVgap(4);
    }

    public StringProperty getTextAreaStringProperty() {
        return textArea.textProperty();
    }

    private void createProgressBar() {
        progressBar = new ProgressBar(0);
        progressBar.prefWidthProperty().bind(widthProperty());
        add(progressBar, 0, 0, 3, 1);
        ratio.addListener((observable, oldValue, newValue) ->
                progressBar.setProgress(newValue.doubleValue()));
    }

    private void createTextArea() {
        textArea = new TextArea();
        add(textArea, 0, 1, 3, 1);
    }

    public DoubleProperty ratioProperty() {
        return ratio;
    }

    public void createButtonRow() {
        int rowNumber = 3;
        createExitButton(rowNumber);
        createSaveButton(rowNumber);
        createLoadButton(rowNumber);
    }

    public void createExitButton(int rowNumber) {
        exit = new Button("exit");
        GridPane.setHalignment(exit, HPos.CENTER);
        add(exit, 0, rowNumber);

    }
    public void createSaveButton(int rowNumber) {
        save = new Button("save");
        GridPane.setHalignment(save, HPos.CENTER);
        add(save, 1, rowNumber);
    }
    public void createLoadButton(int rowNumber) {
        load = new Button("load");
        GridPane.setHalignment(load, HPos.CENTER);
        add(load, 2, rowNumber);
    }

}
