package model;

import model.exceptions.InvalidWordException;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends Observable {

    private static final int GAME_LENGTH = 180;
    private int secondsLeft;
    private Board board;
    private WordList foundWords;
    private Timer timer;

    public Game() {
        board = new Board();
        secondsLeft = GAME_LENGTH;
        foundWords = new WordList();
        initializeTimer();
    }

    // Alternate constructor for loading from saved game
    public Game(Board board, WordList wordsFound, int timeLeft) {
        this.board = board;
        secondsLeft = timeLeft;
        foundWords = wordsFound;
        initializeTimer();
    }

    // MODIFIES: this
    // EFFECTS: starts the game timer, which notifies observers every second
    public void initializeTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        // MODIFIES: this
        // EFFECTS: counts down the timer and notifies UI every second
        public void run() {
            secondsLeft--;
            setChanged();
            notifyObservers(secondsLeft);
            if (secondsLeft <= 0) {
                timer.cancel();
            }
        }
    };

    // MODIFIES: this
    // EFFECTS: stops the game timer
    public void stopTimer() {
        timer.cancel();
    }

    // MODIFIES: this
    // EFFECTS: adds a user-submitted word to list of found words after
    // checking against list of all valid words, returns true if word is valid,
    // false otherwise
    public boolean submitWord(String string) {
        try {
            Word word = new Word(string);
            if (board.getAllValidWords().getWords().contains(word) && !foundWords.getWords().contains(word)) {
                foundWords.add(word);
                setChanged();
                notifyObservers(foundWords);
                return true;
            }
            return false;
        } catch (InvalidWordException e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: reduces remaining time to zero
    public void setTimeToZero() {
        timer.cancel();
        secondsLeft = 0;
    }

    // Getters
    public int getCurrentScore() {
        return foundWords.getScore();
    }

    public WordList getFoundWords() {
        return foundWords;
    }

    public Board getBoard() {
        return board;
    }

    public int getTimeLeft() {
        return secondsLeft;
    }
}
