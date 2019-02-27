package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class HelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello␣World!");

        StackPane root = new StackPane();


        Label label = createNewHelloWorldLabel();
        root.getChildren().add(label);

        Button exitButton = createNewExitButton();
        root.getChildren().add(exitButton);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    public Button createNewExitButton() {
        Button exitButton=new Button();
        exitButton.setText("Exit");
        return exitButton;
    }

    public Label createNewHelloWorldLabel(){
        Label label = new Label();
        label.setText("Hello␣World");
        return label;
    }
}
