package tests;

import model.*;
import parsers.DiceParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private HashSet<DictionaryTree> emptyList = new HashSet<>();
    private DictionaryTree d4 = new DictionaryTree('d', true, emptyList);
    private DictionaryTree r3 = new DictionaryTree('r', false, new HashSet<>(Arrays.asList(d4)));
    private DictionaryTree i2 = new DictionaryTree('i', false, new HashSet<>(Arrays.asList(r3)));
    private DictionaryTree g3 = new DictionaryTree('g', true, emptyList);
    private DictionaryTree t3 = new DictionaryTree('t', true, emptyList);
    private DictionaryTree a2 = new DictionaryTree('a', false, new HashSet<>(Arrays.asList(t3, g3)));
    private DictionaryTree b1 = new DictionaryTree('b', false, new HashSet<>(Arrays.asList(a2, i2)));
    private DictionaryTree y3 = new DictionaryTree('y', true, emptyList);
    private DictionaryTree t3Left = new DictionaryTree('t', true, emptyList);
    private DictionaryTree n2 = new DictionaryTree('n', true, new HashSet<>(Arrays.asList(t3Left, y3)));
    private DictionaryTree t2 = new DictionaryTree('t', true, emptyList);
    private DictionaryTree a1 = new DictionaryTree('a', true, new HashSet<>(Arrays.asList(n2, t2)));
    private DictionaryTree topTree = new DictionaryTree(' ', false, new HashSet<>(Arrays.asList(a1, b1)));
    private ArrayList<String> boardLayout = new ArrayList<>(Arrays.asList("a", "t", "x", "x", "n", "x", "g", "t",
            "y", "x", "x", "a", "d", "r", "i", "b"));
    private ArrayList<String> boardLayoutNoWordsInDictionary = new ArrayList<>(Arrays.asList("x", "x", "x", "x", "x",
            "x", "x", "x", "x", "x", "x", "x", "x", "x", "x", "x"));
    private ArrayList<String> boardLayoutWordsButNoneValid = new ArrayList<>(Arrays.asList("a", "a", "a", "a", "a",
            "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"));
    private ArrayList<String> customBoardLayout = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g",
            "h", "i", "j", "k", "l", "m", "n", "o", "p"));


    @Test
    public void testSolveBoardWordsInDictionary() {
        board = new Board(boardLayout, topTree);
        HashSet<String> allWordsInDictionary = board.getAllWordsInDictionary();
        assertTrue(allWordsInDictionary.contains("at"));
        assertTrue(allWordsInDictionary.contains("ant"));
        assertTrue(allWordsInDictionary.contains("any"));
        assertTrue(allWordsInDictionary.contains("bat"));
        assertTrue(allWordsInDictionary.contains("bag"));
        assertTrue(allWordsInDictionary.contains("bird"));
        assertTrue(allWordsInDictionary.contains("a"));
        assertTrue(allWordsInDictionary.contains("an"));
        assertFalse(allWordsInDictionary.contains("atn"));
        assertEquals(8, allWordsInDictionary.size());
    }

    @Test
    public void testSolveBoardAllValidWords() {
        board = new Board(boardLayout, topTree);
        WordList allValidWords = board.getAllValidWords();
        assertFalse(allValidWords.getStrings().contains("at"));
        assertTrue(allValidWords.getStrings().contains("ant"));
        assertTrue(allValidWords.getStrings().contains("any"));
        assertTrue(allValidWords.getStrings().contains("bat"));
        assertTrue(allValidWords.getStrings().contains("bag"));
        assertTrue(allValidWords.getStrings().contains("bird"));
        assertFalse(allValidWords.getStrings().contains("a"));
        assertFalse(allValidWords.getStrings().contains("an"));
        assertEquals(5, allValidWords.getScore());
    }

    @Test
    public void testNoWordsInDictionary() {
        board = new Board(boardLayoutNoWordsInDictionary, topTree);
        assertTrue(board.getAllWordsInDictionary().isEmpty());
        assertTrue(board.getAllValidWords().getStrings().isEmpty());
    }

    @Test
    public void testWordsInDictionaryNoneValid() {
        board = new Board(boardLayoutWordsButNoneValid, topTree);
        assertFalse(board.getAllWordsInDictionary().isEmpty());
        assertTrue(board.getAllValidWords().getStrings().isEmpty());
    }

    @Test
    public void testFullDictionaryCustomBoard() {
        board = new Board(customBoardLayout, new DictionaryTree());
        WordList allValidWords = board.getAllValidWords();
        assertFalse(allValidWords.getStrings().isEmpty());
        assertEquals(17, board.getMaximumScore());
        assertTrue(allValidWords.getStrings().contains("mink"));
        assertTrue(allValidWords.getStrings().contains("fin"));
        assertTrue(allValidWords.getStrings().contains("knife"));
        assertFalse(allValidWords.getStrings().contains("in"));
    }

    @Test
    public void testDefaultConstructor() {
        board = new Board();
        assertFalse(board.getMaximumScore() == 0);
    }

    @Test
    public void testDiceParserException() {
        String oldPath = "./src/main/ca/ubc/cs/cpsc210/persistence/default_dice.txt";
        String newPath = "./src/main/ca/ubc/cs/cpsc210/persistence/default_dice_hijinx.txt";
        File file1 = new File(oldPath);
        File file2 = new File(newPath);
        assertTrue(file1.renameTo(file2));
        assertThrows(RuntimeException.class, () -> {
            List<Die> dice = DiceParser.parseDefault();
        });
        assertTrue(file2.renameTo(file1));
    }
}