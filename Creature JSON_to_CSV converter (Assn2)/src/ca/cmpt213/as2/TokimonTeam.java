package ca.cmpt213.as2;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.Collections;

/**
 * TokimonTeam class models the information about a
 * team of tokimons. Data includes the ArrayList of
 * information about a tokimon, and team association.
 * It supports adding new tokimon, and sorting them.
 */
public class TokimonTeam {
    private ArrayList<ArrayList<String>> tokimonList;       // The list of tokimons in the team
    private final String association;                       // The association is the name substring
                                                            // of the first tokimon added to the team

    public TokimonTeam(ArrayList<ArrayList<String>> tokimonList, String association) {
        this.tokimonList = tokimonList;
        this.association = association;
    }

    public ArrayList<ArrayList<String>> getTokimonList() {
        return tokimonList;
    }

    public String getAssociation() {
        return association;
    }

    public void add(ArrayList<String> tokimon) {
        tokimonList.add(tokimon);           // Add tokimon information to the list
    }

    // The comparator for sort() to know what attributes compare objects on
    Comparator<ArrayList<String>> sortingList = new Comparator<ArrayList<String>>() {
        @Override
        public int compare(ArrayList<String> o1, ArrayList<String> o2) {
            return o1.get(1).trim().toLowerCase().compareTo(           // Get "from" id of first
                    o2.get(1).trim().toLowerCase());                   // Compare to "from" id of second
        }
    };

    public void sortTeam() {
        // Performs sorting of a tokimon team on "from" id
        tokimonList.sort(sortingList);
    }

}
