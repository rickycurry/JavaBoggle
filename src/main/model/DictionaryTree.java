package model;

import model.exceptions.LetterNotInChildrenException;
import model.exceptions.NodeNotFoundException;
import parsers.DictionaryTreeParser;

import java.util.HashSet;

public class DictionaryTree {

    private char letter;
    private boolean value;
    private HashSet<DictionaryTree> children;

    // Default constructor, using full English spell-check dictionary
    public DictionaryTree() {
        DictionaryTree tree = DictionaryTreeParser.parse();
        letter = tree.getLetter();
        value = tree.getValue();
        children = tree.getChildren();
    }
    
    public DictionaryTree(char letter, boolean value, HashSet<DictionaryTree> children) {
        this.letter = letter;
        this.value = value;
        this.children = children;
    }

    // EFFECTS: returns true if word is found in tree, false otherwise
    public static boolean containsWord(String string, DictionaryTree tree) {
        try {
            DictionaryTree stringNode = fetchNode(string, tree);
            return stringNode.getValue();
        } catch (NodeNotFoundException e) {
            return false;
        }
    }

    // EFFECTS: return true if children contains a dict tree containing the
    // appropriate letter key, false otherwise
    public boolean containsLetter(char letter) {
        if (!hasChildren()) {
            return false;
        }
        for (DictionaryTree tree : children) {
            if (tree.getLetter() == letter) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return child tree corresponding to given letter
    public DictionaryTree getTreeByLetter(char letter) throws LetterNotInChildrenException {
        if (!children.isEmpty()) {
            for (DictionaryTree tree : children) {
                if (tree.getLetter() == letter) {
                    return tree;
                }
            }
        }
        throw new LetterNotInChildrenException();
    }

    // EFFECTS: returns true if given substring exists in tree and has children, otherwise false
    public static boolean hasChildrenForSubstring(String string, DictionaryTree tree) {
        try {
            DictionaryTree stringNode = fetchNode(string, tree);
            return stringNode.hasChildren();
        } catch (NodeNotFoundException e) {
            return false;
        }
    }

    // EFFECTS: returns the DictionaryTree node associated with the given string and tree,
    // or throws NodeNotFoundException if not found
    public static DictionaryTree fetchNode(String string, DictionaryTree tree) throws NodeNotFoundException {
        if (string.length() == 0) {
            return tree;
        } else {
            char firstLetter = string.charAt(0);
            try {
                return fetchNode(string.substring(1), tree.getTreeByLetter(firstLetter));
            } catch (LetterNotInChildrenException e) {
                throw new NodeNotFoundException();
            }
        }
    }

    // MODIFIES: tree
    // EFFECTS: adds the given string to the tree
    public static void addWordToTree(String string, DictionaryTree tree) {
        if (string.length() == 0) {
            tree.setValue(true);
        } else {
            char firstLetter = string.charAt(0);
            if (!tree.containsLetter(firstLetter)) {
                // System.out.println(firstLetter + " not in children.");
                tree.addChild(firstLetter, false, new HashSet<>());
            }
            try {
                addWordToTree(string.substring(1), tree.getTreeByLetter(firstLetter));
            } catch (LetterNotInChildrenException e) {
                System.out.println("Something went wrong with addWordToTree.");
            }
        }
    }

    // EFFECTS: returns true if the tree has children, otherwise false
    public boolean hasChildren() {
        return !children.isEmpty();
    }
    
    // Getters
    public char getLetter() {
        return letter;
    }

    public boolean getValue() {
        return value;
    }

    public HashSet<DictionaryTree> getChildren() {
        return children;
    }

    // Setters
    public void setValue(boolean bool) {
        value = bool;
    }

    public void addChild(char letter, boolean value, HashSet<DictionaryTree> children) {
        this.children.add(new DictionaryTree(letter, value, children));
    }
}