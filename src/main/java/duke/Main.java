package duke;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The JavaFX Application entry point for Duke.
 */
public class Main extends Application {

    private Duke duke = new Duke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            loadStylesheet(scene);
            configureStage(stage, scene);
            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStylesheet(Scene scene) {
        String stylesheet = Main.class.getResource("/view/styles.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle("Fzjjs");
        stage.setMinWidth(300);
        stage.setMinHeight(400);
    }
}
