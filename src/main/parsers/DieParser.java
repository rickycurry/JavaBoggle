package parsers;

import model.Die;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DieParser {

    // EFFECTS: parses JSONObject represented by input as a die and returns it
    public static Die parse(String input) {
        JSONObject dieJson = new JSONObject(input);
        JSONArray faceListJsonArray = dieJson.getJSONArray("die-faces");
        List<String> faceList = new ArrayList<>();
        for (Object object : faceListJsonArray) {
            faceList.add((String) object);
        }
        Die die = new Die(faceList);
        return die;
    }
}