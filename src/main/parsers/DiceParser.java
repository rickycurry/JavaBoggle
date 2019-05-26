package parsers;

import model.Die;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DiceParser {

    // EFFECTS: parses JSONArray represented by input as a list of dice and
    // returns it
    public static List<Die> parse(String input) {
        List<Die> dice = new ArrayList<>();
        JSONArray diceArray = new JSONArray(input);

        for (Object object : diceArray) {
            JSONObject dieJson = (JSONObject) object;
            Die die = DieParser.parse(dieJson.toString());
            dice.add(die);
        }

        return dice;
    }

    // EFFECTS: parses default dice file as list of dice and returns it
    public static List<Die> parseDefault() {
        String defaultDicePath = "./src/main/persistence/default_dice.txt";
        try {
            String diceJson = new String(Files.readAllBytes(Paths.get(defaultDicePath)));
            return DiceParser.parse(diceJson);
        } catch (IOException e) {
            System.out.println("Error reading default dice file.");
            throw new RuntimeException();
        }
    }
}