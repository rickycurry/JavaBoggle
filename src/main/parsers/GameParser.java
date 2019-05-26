package parsers;

import model.Board;
import model.Game;
import model.WordList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameParser {

    static String saveFilePath = "./src/main/persistence/gamesave.json";

    // EFFECTS: parses JSONArray represented by input as a game and returns
    // it
    public static Game parse(String input) {
        JSONObject gameJson = new JSONObject(input);
        JSONArray wordListJson = gameJson.getJSONArray("found-words");
        JSONObject boardJson = gameJson.getJSONObject("board");
        int timeLeft = gameJson.getInt("seconds-left");

        WordList foundWords = WordListParser.parse(wordListJson.toString());
        Board board = BoardParser.parse(boardJson.toString());
        return new Game(board, foundWords, timeLeft);
    }

    // EFFECTS: parses saved game data as a game and returns it, or returns a
    // new game if no saved game is found
    public static Game parseSavedGame() {
        try {
            String fileString = new String(Files.readAllBytes(Paths.get(saveFilePath)));
            return parse(fileString);
        } catch (IOException e) {
            System.out.println("Saved file could not be found.");
            return new Game();
        }
    }

    // EFFECTS: returns true if there is a saved game
    public static boolean isSavedGame() {
        try {
            String fileString = new String(Files.readAllBytes(Paths.get(saveFilePath)));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
