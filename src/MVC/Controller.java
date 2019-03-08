package MVC;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller extends Application {
    private View view;
    private Model model;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        view = new View();
        model = new Model("Java", "bums");
        Scene scene = new Scene(view, 300, 300);
        primaryStage.setScene(scene);
        view.getTextAreaStringProperty().bindBidirectional(model.textProperty());
        view.ratioProperty().bind(model.buzzwordRatioProperty());
        primaryStage.show();
    }
}
