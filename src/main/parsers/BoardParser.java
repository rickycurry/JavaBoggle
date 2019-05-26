package parsers;

import model.Board;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoardParser {

    // EFFECTS: parses JSONArray represented by input as a board and returns
    // it

    public static Board parse(String input) {
        JSONObject boardJson = new JSONObject(input);
        JSONArray boardLayoutArray = boardJson.getJSONArray("board-layout");

        List<String> boardLayout = new ArrayList<>();
        for (Object object : boardLayoutArray) {
            String faceShowing = (String) object;
            boardLayout.add(faceShowing);
        }

        return new Board(boardLayout);
    }
}
