package model;

import java.util.ArrayList;
import java.util.List;

public class WordList {

    private int score;
    private List<Word> words;
    private List<String> strings;

    public WordList() {
        score = 0;
        words = new ArrayList<>();
        strings = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds word to list and increments score by appropriate amount
    public void add(Word word) {
        words.add(word);
        score += word.getScore();
        strings.add(word.getWord());
    }

    // Getters
    public int getScore() {
        return score;
    }

    public List<Word> getWords() {
        return words;
    }

    public List<String> getStrings() {
        return strings;
    }
}
