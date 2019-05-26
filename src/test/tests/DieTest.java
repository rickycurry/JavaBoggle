package tests;

import model.Die;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DieTest {

    private Die die;
    private ArrayList<String> letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f"));

    @BeforeEach
    public void runBeforeEach() {
        die = new Die(letters);
    }

    @Test
    public void testRoll() {
        int[] frequency = new int[6];
        for (int i = 0; i < 1000; i++) {
            String letter = die.roll();
            switch (letter) {
                case "a":  frequency[0] += 1;
                    break;
                case "b":  frequency[1] += 1;
                    break;
                case "c":  frequency[2] += 1;
                    break;
                case "d":  frequency[3] += 1;
                    break;
                case "e":  frequency[4] += 1;
                    break;
                case "f":  frequency[5] += 1;
                    break;
            }
        }
        double d0 = frequency[0];
        double d1 = frequency[1];
        assertEquals(d0, d1, 100);
        for (int i : frequency) {
            if (i == 0) {
                fail();
            }
        }
    }

    @Test
    public void testGetActiveFace() {
        String letter = die.getActiveFace();
        assertTrue(letters.contains(letter));
    }


}
