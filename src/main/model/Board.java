package model;

import model.exceptions.*;
import parsers.DiceParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static model.DictionaryTree.containsWord;
import static model.DictionaryTree.hasChildrenForSubstring;

public class Board {

    //private static final int totalDice = 16;

    private WordList allValidWords = new WordList();
    private HashSet<String> wordsInDictionary = new HashSet<>();
    private List<String> diceShowing = new ArrayList<>();
    private List<int[]> adjacentDice = initializeDice();
    private DictionaryTree dictionary = new DictionaryTree();


    public Board() {
        List<Die> dice = DiceParser.parseDefault();

        Collections.shuffle(dice);
        for (Die d : dice) {
            diceShowing.add(d.roll());
        }

        solve();
    }

    // A constructor for testing, where you can specify the exact letter
    // configuration and custom dictionary
    public Board(List<String> boardLayout, DictionaryTree dictionary) {
        diceShowing = boardLayout;

        this.dictionary = dictionary;

        solve();
    }

    // A constructor for reconstructing a board from a JSON
    public Board(List<String> boardLayout) {
        diceShowing = boardLayout;

        solve();
    }

    // MODIFIES: this
    // EFFECTS: populates list of adjacent dice
    private List<int[]> initializeDice() {
        // eventually this will live in an external file...?
        // also: may eventually refactor into two-dimensional array which
        // contains the dice, making board traversal simpler
        List<int[]> adjacentDice = new ArrayList<>();
        adjacentDice.add(new int[]{1, 4, 5});
        adjacentDice.add(new int[]{0, 2, 4, 5, 6});
        adjacentDice.add(new int[]{1, 3, 5, 6, 7});
        adjacentDice.add(new int[]{2, 6, 7});
        adjacentDice.add(new int[]{0, 1, 5, 8, 9});
        adjacentDice.add(new int[]{0, 1, 2, 4, 6, 8, 9, 10});
        adjacentDice.add(new int[]{1, 2, 3, 5, 7, 9, 10, 11});
        adjacentDice.add(new int[]{2, 3, 6, 10, 11});
        adjacentDice.add(new int[]{4, 5, 9, 12, 13});
        adjacentDice.add(new int[]{4, 5, 6, 8, 10, 12, 13, 14});
        adjacentDice.add(new int[]{5, 6, 7, 9, 11, 13, 14, 15});
        adjacentDice.add(new int[]{6, 7, 10, 14, 15});
        adjacentDice.add(new int[]{8, 9, 13});
        adjacentDice.add(new int[]{8, 9, 10, 12, 14});
        adjacentDice.add(new int[]{9, 10, 11, 13, 15});
        adjacentDice.add(new int[]{10, 11, 14});

        return adjacentDice;
    }

    // MODIFIES: this
    // EFFECTS: populates allValidWords with any valid words from the board
    public void solve() {
        try {
            solveBoard();
        } catch (NoWordsInDictionaryException e) {
            System.out.println("No words on board found in dictionary.");
        }

        try {
            addDictionaryWordsToValidWords();
        } catch (NoValidWordsException e) {
            System.out.println("No valid words found.");
        }
    }

    // REQUIRES: diceShowing is not empty
    // MODIFIES: this
    // EFFECTS: finds all dictionary words on the board
    public void solveBoard() throws NoWordsInDictionaryException {
        for (int i = 0; i < getDiceShowing().size(); i++) {
            searchTree(i, diceShowing.get(i), new ArrayList<>());
        }
        if (wordsInDictionary.size() == 0) {
            throw new NoWordsInDictionaryException();
        }
    }

    // REQUIRES: index < getDiceShowing().size()
    // MODIFIES: this
    // EFFECTS: traverses board and dictionary tree to find all valid strings
    // on board, adds all strings found to wordsInDictionary
    public void searchTree(int index, String subString, ArrayList<Integer> visited) {
        visited.add(index);
        if (containsWord(subString, dictionary)) {
            wordsInDictionary.add(subString);
            // System.out.println(subString);
        }
        if (hasChildrenForSubstring(subString, dictionary)) {
            for (int adjacentIndex : adjacentDice.get(index)) {
                ArrayList<Integer> newVisited = new ArrayList<>();
                newVisited.addAll(visited);
                if (!visited.contains(adjacentIndex)) {
                    String newLetter = diceShowing.get(adjacentIndex);
                    searchTree(adjacentIndex, subString.concat(newLetter), newVisited);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds all strings in wordsInDictionary to allValidWords
    public void addDictionaryWordsToValidWords() throws NoValidWordsException {
        if (wordsInDictionary.size() == 0) {
            throw new NoValidWordsException();
        }

        for (String word : wordsInDictionary) {
            try {
                allValidWords.add(new Word(word));
            } catch (InvalidWordException e) {
                // System.out.println('"' + word + "\" is too short.");
            }
        }
    }


    // Getters
    public List<String> getDiceShowing() {
        return diceShowing;
    }

    public WordList getAllValidWords() {
        return allValidWords;
    }

    public HashSet<String> getAllWordsInDictionary() {
        return wordsInDictionary;
    }

    public int getMaximumScore() {
        return allValidWords.getScore();
    }
}
