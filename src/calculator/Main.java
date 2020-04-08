package calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Application main class
 */
public class Main extends Application {

    /**
     * Program entry point
     * @param args Arguments passed by command line
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Functions called by JavaFX framework at the start of application.
     * <br> Load scene from calculator.fxml file, set basic properties and show window.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Calculator");
        primaryStage.setMinHeight(320);
        primaryStage.setMinWidth(320);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
