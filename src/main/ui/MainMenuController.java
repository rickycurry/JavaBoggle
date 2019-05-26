package ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static parsers.GameParser.isSavedGame;
import static ui.Main.playGame;

public class MainMenuController {

    @FXML
    private VBox mainMenu;

    @FXML
    private Button mainMenuNewGameButton;

    @FXML
    private Button mainMenuLoadGameButton;

    @FXML
    private Button mainMenuQuitButton;

    // MODIFIES: this
    // EFFECTS: initializes all FXML fields
    public void initialize() {
        mainMenuNewGameButton.setOnAction(e -> handlePlayGame(false));

        mainMenuLoadGameButton.setDisable(!isSavedGame());
        mainMenuLoadGameButton.setOnAction(e -> handlePlayGame(true));

        mainMenuQuitButton.setOnAction(e -> Platform.exit());
    }

    // EFFECTS: starts a new game or loads a saved game, depending on value of
    // load
    @FXML
    private void handlePlayGame(boolean load) {
        try {
            playGame((Stage) mainMenu.getScene().getWindow(), load);
        } catch (IOException e) {
            System.out.println("Something went wrong building game scene.");
            Platform.exit();
        }
    }
}