package tests;

import model.Word;
import model.WordList;
import model.exceptions.InvalidWordException;
import parsers.WordListParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordListTest {

    private WordList wordList = new WordList();

    @BeforeEach
    public void runBeforeEach() {
        try {
            Word grape = new Word("grape");
            Word orange = new Word("orange");
            Word mango = new Word("mango");
            wordList.add(grape);
            wordList.add(orange);
            wordList.add(mango);
        } catch (InvalidWordException e) {
            // whoops
        }
    }

    @Test
    public void testScore() {
        assertEquals(7, wordList.getScore());
    }

    @Test
    public void testGetStrings() {
        assertTrue(wordList.getStrings().contains("orange"));
    }

    @Test
    public void testGetWords() {
        try {
            Word durian = new Word("durian");
            wordList.add(durian);
            assertTrue(wordList.getWords().contains(durian));
            assertTrue(wordList.getWords().contains(new Word("durian")));
            assertEquals(10, wordList.getScore());
        } catch (InvalidWordException e) {
            // dang
        }
    }

    @Test
    public void testWordListParserException() {
        String wordListJsonString = "[\"asd\", \"as\"]";
        WordList wordList = WordListParser.parse(wordListJsonString);
        assertTrue(wordList.getStrings().contains("asd"));
        assertFalse(wordList.getStrings().contains("as"));
    }
}
