package tests;

import model.Board;
import model.Game;
import model.Word;
import model.WordList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static parsers.GameParser.isSavedGame;
import static parsers.GameParser.parseSavedGame;
import static persistence.Jsonifier.deleteSaveGame;
import static persistence.Jsonifier.saveGame;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    public void runBeforeEach() {
        game = new Game(new Board(), new WordList(), 3);
    }

    @Test
    public void testGetTimeRemaining() {
        assertEquals(3, game.getTimeLeft());
    }

    @Test
    public void testGetBoard() {
        assertFalse(game.getBoard().getMaximumScore() == 0);
    }

    @Test
    public void testSubmitWordSuccessExpected() {
        WordList allValidWords = game.getBoard().getAllValidWords();
        Word firstWord = allValidWords.getWords().get(0);
        String firstString = allValidWords.getStrings().get(0);
        assertFalse(game.getFoundWords().getStrings().contains(firstString));
        game.submitWord(firstString);
        assertTrue(game.getFoundWords().getStrings().contains(firstString));
        assertEquals(firstWord.getScore(), game.getCurrentScore());
    }

    @Test
    public void testSubmitWordNotOnBoard() {
        assertFalse(game.submitWord("battleaxe")); // what are the odds???
    }

    @Test
    public void testSubmitWordAlreadySubmitted() {
        WordList allValidWords = game.getBoard().getAllValidWords();
        String firstWord = allValidWords.getStrings().get(0);
        assertFalse(game.getFoundWords().getStrings().contains(firstWord));
        assertTrue(game.submitWord(firstWord));
        assertTrue(game.getFoundWords().getStrings().contains(firstWord));
        assertFalse(game.submitWord(firstWord));
    }

    @Test
    public void testSubmitWordTooShort() {
        game.submitWord("ab");
        assertTrue(game.getFoundWords().getWords().isEmpty());
    }

    @Test
    public void testTimer() {
        try {
            Thread.sleep(4000);
            assertEquals(0, game.getTimeLeft());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    public void testStopTimer() {
        try {
            Thread.sleep(1100);
            game.stopTimer();
            Thread.sleep(1500);
            assertEquals(2, game.getTimeLeft());
        } catch (InterruptedException e) {
            fail();
        }
    }

    @Test
    public void testParseSavedGame() {
        List<Word> allValidWords = game.getBoard().getAllValidWords().getWords();
        assertTrue(game.submitWord(allValidWords.get(0).getWord()));
        assertFalse(game.getFoundWords().getWords().contains(allValidWords.get(1)));
        saveGame(game);

        Game loadedGame = parseSavedGame();
        assertTrue(loadedGame.getFoundWords().getWords().contains(allValidWords.get(0)));
        assertFalse(loadedGame.getFoundWords().getWords().contains(allValidWords.get(1)));
    }

    @Test
    public void testDeleteGame() {
        List<Word> allValidWords = game.getBoard().getAllValidWords().getWords();
        assertTrue(game.submitWord(allValidWords.get(0).getWord()));
        saveGame(game);

        deleteSaveGame();

        // parseSavedGame returns new game if no saved game found
        Game loadedGame = parseSavedGame();
        assertFalse(loadedGame.getFoundWords().getWords().contains(allValidWords.get(0)));
    }

    @Test
    public void testIsSavedGame() {
        deleteSaveGame();
        assertFalse(isSavedGame());
        saveGame(game);
        assertTrue(isSavedGame());
    }

    @Test
    public void testSetTimeToZero() {
        assertEquals(3, game.getTimeLeft());
        game.setTimeToZero();
        assertEquals(0, game.getTimeLeft());
    }
}
