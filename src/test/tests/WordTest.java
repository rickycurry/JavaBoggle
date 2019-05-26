package tests;

import model.Word;
import model.exceptions.InvalidWordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordTest {

    private Word word;

    @Test
    public void testTooShort() {
        try {
            word = new Word("hi");
            fail("Shouldn't pass");
        } catch (InvalidWordException e) {
            // should get caught here
        }
    }

    @Test
    public void testScoreThreeLetters() {
        try {
            word = new Word("ant");
            assertEquals(1, word.getScore());
            assertEquals("ant", word.getWord());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testScoreFourLetters() {
        try {
            word = new Word("fish");
            assertEquals(1, word.getScore());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testScoreFiveLetters() {
        try {
            word = new Word("bison");
            assertEquals(2, word.getScore());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testScoreSixLetters() {
        try {
            word = new Word("iguana");
            assertEquals(3, word.getScore());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testScoreSevenLetters() {
        try {
            word = new Word("raccoon");
            assertEquals(5, word.getScore());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testScoreEightLetters() {
        try {
            word = new Word("reindeer");
            assertEquals(11, word.getScore());
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testHashCode() {
        try {
            word = new Word("alabama");
            int code = word.hashCode();
        } catch (InvalidWordException e) {
            fail();
        }
    }

    @Test
    public void testEquals() {
        try {
            word = new Word("mediocrity");
            assertFalse(word.equals(null));
            assertFalse(word.equals(new Object()));
        } catch (InvalidWordException e) {
            fail();
        }
    }

}
