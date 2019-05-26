package tests;

import model.*;
import parsers.*;
import persistence.Jsonifier;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static persistence.Jsonifier.*;
import static org.junit.jupiter.api.Assertions.*;

public class JsonifierTest {

    @Test
    public void testConstructor() {
        Jsonifier jsonifier = new Jsonifier();
    }

    @Test
    public void testDieToJson() {
        DieParser dieParser = new DieParser();
        Die d = new Die(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f")));
        JSONObject dieJson = dieToJson(d);
        // System.out.println(dieJson);
        Die parsedDie = DieParser.parse(dieJson.toString());
        assertTrue(parsedDie.getFaceList().contains("a"));
        assertTrue(parsedDie.getFaceList().contains("b"));
    }

    @Test
    public void testDiceToJson() {
        DiceParser diceParser = new DiceParser();
        Die d1 = new Die(new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f")));
        Die d2 = new Die(new ArrayList<>(Arrays.asList("g", "h", "i", "j", "k", "l")));
        List<Die> dice = new ArrayList<>(Arrays.asList(d1, d2));
        JSONArray diceJson = diceToJson(dice);

        List<Die> parsedDice = DiceParser.parse(diceJson.toString());
        Die parsedD1 = parsedDice.get(0);
        Die parsedD2 = parsedDice.get(1);
        assertTrue(parsedD1.getFaceList().contains("a"));
        assertFalse(parsedD1.getFaceList().contains("g"));
        assertTrue(parsedD2.getFaceList().contains("g"));
        assertFalse(parsedD2.getFaceList().contains("a"));
    }

    @Test
    public void testBoardToJson() {
        BoardParser boardParser = new BoardParser();
        ArrayList<String> customBoardLayout = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o", "p"));
        DictionaryTree fullDict = new DictionaryTree();
        Board board = new Board(customBoardLayout, fullDict);
        WordList allValidWords = board.getAllValidWords();
        assertTrue(allValidWords.getStrings().contains("knife"));
        assertEquals(17, allValidWords.getScore());
        JSONObject boardJson = boardToJson(board);

        Board parsedBoard = BoardParser.parse(boardJson.toString());
        WordList parsedAllValidWords = parsedBoard.getAllValidWords();
        assertTrue(parsedAllValidWords.getStrings().contains("knife"));
        assertEquals(17, parsedAllValidWords.getScore());
    }

    @Test
    public void testGameToJson() {
        GameParser gameParser = new GameParser();
        Game game = new Game();
        int max_score = game.getBoard().getMaximumScore();
        assertEquals(0, game.getCurrentScore());
        String firstWord = game.getBoard().getAllValidWords().getStrings().get(0);
        game.submitWord(firstWord);
        assertFalse(game.getCurrentScore() == 0);
        JSONObject gameJson = gameToJson(game);

        Game parsedGame = GameParser.parse(gameJson.toString());
        assertTrue(parsedGame.getFoundWords().getStrings().contains(firstWord));
        assertFalse(parsedGame.getCurrentScore() == 0);
    }
}
