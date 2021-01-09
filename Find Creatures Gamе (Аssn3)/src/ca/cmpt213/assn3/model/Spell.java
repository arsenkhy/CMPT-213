package ca.cmpt213.assn3.model;

import java.util.ArrayList;

/**
 * Spell class models the information about a
 * list of 3 spells. Data includes the list of spells.
 * It supports listing all spells, removal of a spell,
 * using a spell.
 */
public class Spell {
    private static ArrayList<String> spells;
    private static int spellsCounter = 0;

    // Default constructor with initial options
    public Spell() {
        spells = new ArrayList<>();
        // Add all 3 spells whenever defualt constructor is called
        spells.add("Jump the player to another grid location");
        spells.add("Randomly reveal the location of one of the Tokimons");
        spells.add("Randomly kill off one of the Fokimons");
        spellsCounter = 3;
    }

    public void listAllSpells() {
        int number = 1;
        for (String spell : spells) {
            System.out.println(number + ". " + spell);
            number++;
        }
    }

    public static int getSpellsCounter() {
        return spellsCounter;
    }

    public void removeAtIndex(int index){
        spells.remove(index);
        spellsCounter--;
    }

    public void useSpellAtIndex(int index, Player player, Board board) {
        if (spells.get(index).equals("Jump the player to another grid location")) {
            int[] randomLocation = Board.jumpToSomeLocation();          // Get random position from Board
            player.setRow(randomLocation[0]);                           // Set player to that position
            player.setColumn(randomLocation[1]);
        } else if (spells.get(index).equals("Randomly reveal the location of one of the Tokimons")){
            if (board.revealTokimon()) {                                // Reveal whether there are tokimons to reveal or not
                System.out.println("Tokimon has been revealed");
            } else {
                System.out.println("There is no more unrevealed Tokimons!");
            }
        } else {
            if (board.killFokimon()) {
                System.out.println("You murdered a Fokimon");           // Kill a fokimon if there is left some
            } else {
                System.out.println("There is no more unrevealed Fokimons!");
            }
        }
        removeAtIndex(index);       // Remove the spell when used
    }

    public boolean runOutOfSpells() {
        return spells.isEmpty();
    }
}
