package ca.cmpt213.ModelToki;

import java.util.ArrayList;
import java.util.List;

/**
 * TokiManager class models the list of Tokimons.
 * Data includes the ArrayList to store Tokimons
 * and counter to track number of objects in the list.
 */
public class TokiManager {
    private List<Tokimon> tokimonList = new ArrayList<>();
    private static int counter = 0;

    public static int getCounter() {
        return counter;
    }

    public List<Tokimon> getTokimonList() {
        return tokimonList;
    }

    public void addNewToki(Tokimon newToki) {
        tokimonList.add(newToki);

        // add to the number of tokimons that are in the list
        counter++;
    }

    // removal of Tokimon from list at particular index
    public void deleteToki(int index) {
        tokimonList.remove(index);
        counter--;
    }

    // changes the strength of a Tokimon
    public void alterToki(int index, int value) {
        // Calculating new strength value
        int newStrength = tokimonList.get(index).getStrength() + value;

        // Setting new strength value
        tokimonList.get(index).setStrength(newStrength);
    }

    // Displaying all Tokimons in the list
    public void displayAllTokis() {
        System.out.println("~ Tokimons ~");
        int order = 0;          // order of a Tokimon
        for (Tokimon tokimon : tokimonList) {
            System.out.println(++order + ". " + tokimon.getName() + ", " +
                        tokimon.getType() + " ability, " +
                        tokimon.getHeight() + "m, " +
                        tokimon.getWeight() + "kg, " +
                        tokimon.getStrength() + " strength");
        }
    }
}
