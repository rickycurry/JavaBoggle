package model;

import java.util.List;
import java.util.Random;

public class Die {

    private List<String> faceList;
    private String activeFace;

    public Die(List<String> faceList) {
        this.faceList = faceList;
        activeFace = this.roll();
    }

    // MODIFIES: this
    // EFFECTS: randomly selects a die face and returns its string
    public String roll() {
        int randomIndex = new Random().nextInt(faceList.size());
        activeFace = faceList.get(randomIndex);
        return activeFace;
    }

    // Getter
    public String getActiveFace() {
        return activeFace;
    }

    public List getFaceList() {
        return faceList;
    }
}
