package MVC;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

import java.awt.*;

public class View extends GridPane {
    private ProgressBar progressBar;
    private TextArea textArea;
    private Button exit;
    private Button save;
    private Button load;

    public View() {
        setHgap(3);
        setVgap(4);
    }
}
