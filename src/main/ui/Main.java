package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {

    private static String mainMenuFxmlPath = "./src/main/ui/mainMenu.fxml";
    private static String gameSceneFxmlPath = "./src/main/ui/gameScene.fxml";

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage stage) throws IOException {
        showMenu(stage);
    }

    // EFFECTS: shows the main menu screen
    public static void showMenu(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(mainMenuFxmlPath);

        VBox root = loader.load(fxmlStream);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Boggle!");
        stage.setResizable(false);
        stage.show();
    }

    // EFFECTS: shows the game screen
    public static void playGame(Stage stage, boolean load) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new GameSceneController(load));
        FileInputStream fxmlStream = new FileInputStream(gameSceneFxmlPath);

        BorderPane root = loader.load(fxmlStream);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
