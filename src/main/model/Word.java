package model;

import model.exceptions.InvalidWordException;

import java.util.Objects;

public class Word {

    private String word;
    private int score;

    public Word(String word) throws InvalidWordException {
        if (word.length() < 3) {
            throw new InvalidWordException();
        }
        this.word = word;
        setScore();
    }

    // MODIFIES: this
    // EFFECTS: sets word score based on length of word
    private void setScore() {
        int length = word.length();
        switch (length) {
            case 3: score = 1;
            break;
            case 4: score = 1;
            break;
            case 5: score = 2;
            break;
            case 6: score = 3;
            break;
            case 7: score = 5;
            break;
            default: score = 11;
            break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Word word1 = (Word) o;
        return Objects.equals(word, word1.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }

    // Getters
    public String getWord() {
        return word;
    }

    public int getScore() {
        return score;
    }
}
