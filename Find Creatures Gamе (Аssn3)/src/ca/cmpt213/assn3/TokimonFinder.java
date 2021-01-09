package ca.cmpt213.assn3;

import ca.cmpt213.assn3.UI.Play;
import ca.cmpt213.assn3.model.Spell;
import ca.cmpt213.assn3.model.Board;
import ca.cmpt213.assn3.model.Player;

/**
 * TokimonFinder class models the main method to
 * start an application. It supports taking arguments
 * for number of tokimons, fokimons and cheat mode.
 * Starts the game.
 */
public class TokimonFinder {

    public static void main(String[] args) {
        // Enforce the allowed number of arguments passed
        if (args.length > 3) {
            System.err.println("Sorry, please pass 0 to 3 number of arguments");
            System.exit(-1);
        }

        // Default values assigned
        int tokimonsCount = 10;
        int fokimonsCount = 5;
        boolean cheatsOn = false;

        for (String argument : args) {
            String number;
            // Number of tokis
            if (argument.startsWith("--numToki=")) {
                // The count of tokimons
                number = argument.substring(10, argument.length());
                // Check if the number is passed, otherwise default value is set
                try {
                    tokimonsCount = Integer.parseInt(number);
                    if (tokimonsCount < 1) {
                        System.out.println("Sorry, cannot have 1 > tokimons");
                        System.exit(-1);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("The number of Tokimons has wrong characters!");
                    System.exit(-1);
                }
            } else if (argument.startsWith("--numFoki=")) {
                // The count of fokimons
                number = argument.substring(10, argument.length());
                // Check if the number is passed, otherwise default value is set
                try {
                    fokimonsCount = Integer.parseInt(number);
                    if (fokimonsCount < 1) {
                        System.out.println("Sorry, cannot have 1 > fokimons");
                        System.exit(-1);
                    }
                } catch (NumberFormatException exception) {
                    System.out.println("The number of Fokimons has wrong characters!");
                    System.exit(-1);
                }
            }else if (argument.equals("--cheat")) {
                cheatsOn = true;
            }
        }

        // Check for impossible values
        if (tokimonsCount + fokimonsCount > 100) {
            System.out.println("Given number of creatures on board is impossible!");
            System.exit(-1);
        }

        Board gameBoard = new Board(tokimonsCount, fokimonsCount, cheatsOn);      // Board
        gameBoard.populateBoard();                                                // Populate the board
        Player player = new Player();                                             // Player
        Spell spell = new Spell();                                                // Spell

        // UI for the game
        Play game = new Play(gameBoard, player, spell);
        game.startGame();       // Start the game
    }
}
