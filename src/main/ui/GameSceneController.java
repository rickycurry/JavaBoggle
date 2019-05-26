package ui;

import model.Game;
import model.Word;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static parsers.GameParser.parseSavedGame;
import static persistence.Jsonifier.deleteSaveGame;
import static persistence.Jsonifier.saveGame;
import static ui.Main.showMenu;
import static java.util.Collections.reverse;
import static java.util.Collections.sort;

// Controller for game scene
public class GameSceneController implements Observer {

    private Game game;

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane letterGrid;

    @FXML
    private Text letter00;

    @FXML
    private Text letter10;

    @FXML
    private Text letter20;

    @FXML
    private Text letter30;

    @FXML
    private Text letter01;

    @FXML
    private Text letter11;

    @FXML
    private Text letter21;

    @FXML
    private Text letter31;

    @FXML
    private Text letter02;

    @FXML
    private Text letter12;

    @FXML
    private Text letter22;

    @FXML
    private Text letter32;

    @FXML
    private Text letter03;

    @FXML
    private Text letter13;

    @FXML
    private Text letter23;

    @FXML
    private Text letter33;

    @FXML
    private TextArea foundWordsTextArea;

    @FXML
    private HBox bottomHBox;

    @FXML
    private TextField wordEntryField;

    @FXML
    private Button submitButton;

    @FXML
    private Button resignButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button quitGameButton;

    @FXML
    private TextArea allWordsTextArea;

    @FXML
    private VBox topVBox;

    @FXML
    private Text scoreText;

    @FXML
    private Text timeText;

    // Constructor for controller where game is initialized and this is added
    // as an observer
    public GameSceneController(boolean load) {
        if (load) {
            game = parseSavedGame();
        } else {
            game = new Game();
        }
        game.addObserver(this);
    }

    // MODIFIES: this
    // EFFECTS: initializes all FXML fields
    public void initialize() {
        setLetters();
        scoreText.setText(scoreText());
        updateTime();
        submitButton.setOnAction(e -> handleSubmit());
        resignButton.setOnAction(e -> endGame());
        menuButton.setOnAction(e -> handleMenu());
        quitGameButton.setOnAction(e -> handleQuit());
        setFoundWordsTextArea();
        wordEntryField.setOnKeyPressed(ke -> {
            KeyCode keyCode = ke.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                handleSubmit();
            }
        });
        Platform.runLater(() -> wordEntryField.requestFocus());
    }

    // MODIFIES: this
    // EFFECTS: sets the text to the board dice values
    private void setLetters() {
        List<String> diceShowing = game.getBoard().getDiceShowing();
        letter00.setText(diceShowing.get(0));
        letter10.setText(diceShowing.get(1));
        letter20.setText(diceShowing.get(2));
        letter30.setText(diceShowing.get(3));
        letter01.setText(diceShowing.get(4));
        letter11.setText(diceShowing.get(5));
        letter21.setText(diceShowing.get(6));
        letter31.setText(diceShowing.get(7));
        letter02.setText(diceShowing.get(8));
        letter12.setText(diceShowing.get(9));
        letter22.setText(diceShowing.get(10));
        letter32.setText(diceShowing.get(11));
        letter03.setText(diceShowing.get(12));
        letter13.setText(diceShowing.get(13));
        letter23.setText(diceShowing.get(14));
        letter33.setText(diceShowing.get(15));
    }

    // MODIFIES: this
    // EFFECTS: initializes the found words text area, populating it with words
    // if loading from saved game
    private void setFoundWordsTextArea() {
        foundWordsTextArea.setText("Words found:");
        for (Word word : game.getFoundWords().getWords()) {
            foundWordsTextArea.appendText(wordToString(word));
        }
    }

    // MODIFIES: this
    // EFFECTS: submits the word in the word entry field and clears the field
    private void handleSubmit() {
        String word = wordEntryField.getText();
        word = word.toLowerCase();
        game.submitWord(word);
        wordEntryField.clear();
    }

    // MODIFIES: this
    // EFFECTS: returns to main menu, saving game if time remains
    private void handleMenu() {
        game.stopTimer();
        if (game.getTimeLeft() >= 1) {
            saveGame(game);
        } else {
            deleteSaveGame();
        }
        try {
            showMenu((Stage) borderPane.getScene().getWindow());
        } catch (IOException e) {
            System.out.println("There was a problem loading the main menu.");
        }
    }

    // MODIFIES: this
    // EFFECTS: quits the program, saving game if time remains
    private void handleQuit() {
        if (game.getTimeLeft() >= 1) {
            game.stopTimer();
            saveGame(game);
        } else {
            deleteSaveGame();
        }
        Platform.exit();
    }

    // MODIFIES: this
    // EFFECTS: updates the UI to reflect changes in game state
    public void update(Observable observable, Object o) {
        if (o instanceof Integer) {
            updateTime();

        } else {
            updateWord();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the UI timer and ends game if no time remaining
    private void updateTime() {
        int secondsLeft = game.getTimeLeft();
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String secsFormatted = String.format("%02d", seconds);
        timeText.setText(minutes + ":" + secsFormatted);
        if (secondsLeft <= 0) {
            endGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the most recent word to the found words text area and
    // updates the score accordingly
    private void updateWord() {
        scoreText.setText(scoreText());
        List<Word> foundWords = game.getFoundWords().getWords();
        Word lastWord = foundWords.get(foundWords.size() - 1);
        foundWordsTextArea.appendText(wordToString(lastWord));
    }

    // MODIFIES: this
    // EFFECTS: disables further word entries, and reveals missed words
    private void endGame() {
        deleteSaveGame();
        game.setTimeToZero();
        showAllWords();
        wordEntryField.setDisable(true);
        submitButton.setDisable(true);
        resignButton.setDisable(true);
    }

    // MODIFIES: this
    // EFFECTS: shows missed score and all words missed by the user
    private void showAllWords() {
        int missedScore = game.getBoard().getMaximumScore() - game.getCurrentScore();
        allWordsTextArea.setText("Missed score: " + missedScore);
        List<String> wordStrings = new ArrayList<>();
        for (Word w : game.getBoard().getAllValidWords().getWords()) {
            if (!game.getFoundWords().getWords().contains(w)) {
                wordStrings.add(wordToString(w));
            }
        }
        sort(wordStrings);
        reverse(wordStrings);

        String allWordsText = String.join("", wordStrings);
        allWordsTextArea.appendText(allWordsText);
        allWordsTextArea.setScrollTop(Double.MIN_VALUE);
    }

    // EFFECTS: produce a string for showing the score
    private String scoreText() {
        return "Score: " + game.getCurrentScore();
    }

    // EFFECTS: produce a string for showing word score and string
    private String wordToString(Word word) {
        return "\n" + word.getScore() + " " + word.getWord();
    }
}