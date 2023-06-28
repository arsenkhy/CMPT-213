package ca.cmpt213.Cmpt_project.model;

import java.util.ArrayList;

/**
 * TokimonManager class models the information about a
 * tokimons. Data list of tokimons. It supports adding,
 * updating and deleting of tokimons in the list
 */
public class TokimonManager {
    private ArrayList<Tokimon> tokimons = new ArrayList<>();

    public void addToki(Tokimon tokimon) {
        tokimons.add(tokimon);
    }

    public ArrayList<Tokimon> getTokimons() {
        return tokimons;
    }

    public Tokimon getAtId(String id) {
        for (Tokimon tokimon : tokimons) {
            if (tokimon.getId() == Integer.parseInt(id)) {
                return tokimon;
            }
        }
        return null;            // not found
    }

    public void updateSomeTokimon(Tokimon newToki, String id) {
        int index = 0;
        for (Tokimon toki : tokimons) {
            if (toki.getId() == Integer.parseInt(id)) {
                // To share same id of an Tokimon
                newToki.setId(toki.getId());
                tokimons.set(index, newToki);
                return;
            }
            index++;
        }
    }

    public void deleteTokimon(String id) {
        int index = 0;
        for (Tokimon tokimon : tokimons) {
            if (tokimon.getId() == Integer.parseInt(id)) {
                tokimons.remove(index);
                return;
            }
            index++;
        }
    }
}
