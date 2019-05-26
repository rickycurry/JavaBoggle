package parsers;

import model.DictionaryTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import static model.DictionaryTree.addWordToTree;

public class DictionaryTreeParser {

    // EFFECTS: parses text file representing an english dictionary and returns
    // it as a DictionaryTree

    public static DictionaryTree parse() {
        HashSet<DictionaryTree> children = new HashSet<>();
        String fileName = "./src/main/dictionary/dictionary.txt";
        String line;
        DictionaryTree tree = new DictionaryTree(' ', false, children);

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println(line);
                addWordToTree(line, tree);
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.out.println("Error reading dictionary file.");
        }

        return tree;
    }
}