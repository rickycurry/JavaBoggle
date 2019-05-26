package persistence;

import model.Board;
import model.Die;
import model.Game;
import model.WordList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Jsonifier {

    private static String gameSavePath = "./src/main/persistence/gamesave.json";

    // EFFECTS: returns JSONObject representing Die
    public static JSONObject dieToJson(Die d) {
        JSONObject dieJson = new JSONObject();

        dieJson.put("die-faces", d.getFaceList());
        return dieJson;
    }

    // EFFECTS: returns JSONArray representing list of Die
    public static JSONArray diceToJson(List<Die> dice) {
        JSONArray diceArray = new JSONArray();
        for (Die d : dice) {
            diceArray.put(dieToJson(d));
        }

        return diceArray;
    }

    // EFFECTS: returns JSONArray representing WordList
    public static JSONArray wordListToJson(WordList wl) {
        JSONArray wordArray = new JSONArray();
        for (String s : wl.getStrings()) {
            wordArray.put(s);
        }

        return wordArray;
    }

    // EFFECTS: returns JSONObject representing Board
    public static JSONObject boardToJson(Board b) {
        JSONObject boardJson = new JSONObject();

        boardJson.put("board-layout", b.getDiceShowing());

        return boardJson;
    }

    // EFFECTS: returns JSONObject representing Game
    public static JSONObject gameToJson(Game g) {
        JSONObject gameJson = new JSONObject();

        gameJson.put("board", boardToJson(g.getBoard()));
        gameJson.put("found-words", wordListToJson(g.getFoundWords()));
        gameJson.put("seconds-left", g.getTimeLeft());

        return gameJson;
    }

    // EFFECTS: saves game as file
    public static void saveGame(Game g) {
        JSONObject gameJson = gameToJson(g);
        try {
            FileWriter fileWriter = new FileWriter(gameSavePath);
            fileWriter.write(gameJson.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("File could not be saved.");
        }
    }

    // MODIFIES: saved game file
    // EFFECTS: deletes saved game data
    public static void deleteSaveGame() {
        File saveFile = new File(gameSavePath);
        saveFile.delete();
    }
}
