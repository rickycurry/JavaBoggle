package parsers;

import model.Word;
import model.WordList;
import model.exceptions.InvalidWordException;
import org.json.JSONArray;

public class WordListParser {

    // EFFECTS: parses JSONArray represented by input as a WordList and
    // returns it
    public static WordList parse(String input) {
        WordList wl = new WordList();
        JSONArray wordArray = new JSONArray(input);

        for (Object object : wordArray) {
            String string = (String) object;
            try {
                Word w = new Word(string);
                wl.add(w);
            } catch (InvalidWordException e) {
                // shouldn't be possible to get here
            }
        }

        return wl;
    }
}