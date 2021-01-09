package ca.cmpt213.assn3.UI;

import ca.cmpt213.assn3.model.Board;
import ca.cmpt213.assn3.model.Cell;
import ca.cmpt213.assn3.model.Player;
import ca.cmpt213.assn3.model.Spell;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Play class models the UI for the game.
 * Data includes board, player, and spell objects
 * It supports displaying choices, taking inputs,
 * checking inputs, and displaying board
 */
public class Play {
    private final Board board;
    private Player player;
    private final Spell spell;

    public Play(Board board, Player player, Spell spell) {
        this.board = board;
        this.player = player;
        this.spell = spell;
    }

    public void startGame(){
        // Welcome to game
        System.out.println("*******************");
        System.out.println("***   Welcome   ***");
        System.out.println("*******************\n");

        // Setup the player
        player = getPlayerAtInitialPostion();

        boolean foundAllTokimons;
        // While game not finished
        while(true) {
            boolean isLandedOnTokimon = board.retrieveCell(
                    player.getRow(), player.getColumn()).getCreatureSymbol() == '$';
            // Tokimon found
            if (isLandedOnTokimon) {
                System.out.println("\t.::TOKIMON!::.");
                System.out.println("\tCongratulations!");
            }

            // Checking if landed on the Fokimon
            boolean hasNotFoundFokimon = board.processVisitedCell(
                    board.retrieveCell(
                    player.getRow(), player.getColumn()));
            // Found all of the Tokimons
            foundAllTokimons = board.getTokiNum() == Board.getTokiFoundCounter();

            displayRemaining();             // Display for number of left spells and Tokimons
            displayBoard();                 // Display board

            if (hasNotFoundFokimon) {       // Not landed on fokimon
                if (!foundAllTokimons) {    // Not found all tokimons
                    displayChoice();        // Display the turn
                } else {
                    break;                  // Found all Tokimons
                }
            } else {
                break;                      // Found fokimon
            }
        }

        finishPlay(foundAllTokimons);       // Display the final statement and revealed board
    }

    private void finishPlay(boolean won) {
        if (won) {
            System.out.println("\n.::WINNER WINNER CHICKEN DINNER::.");
            System.out.println("Congratulations you found all Tokimons!\n");
        } else {
            System.out.println("\nYou lost! You found a Fokimon(((. Try again!\n");
        }
        board.revealAllCells();         // Reveal all creatures
        displayBoard();                 // Displaying the board
    }

    private void displayChoice() {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\t.::Options::.");
            System.out.println("1. Move from current position");
            System.out.println("2. Use a spell");
            System.out.print("Your choice> ");
            try {
                int choice = input.nextInt();
                if (choice == 1) {
                    movePlayer();               // Moving of the player
                    break;
                } else if (choice == 2){
                    useSpell();                 // Use of a spell
                    break;
                } else {
                    System.out.println("PLease enter valid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("ERROR! Do not pass illegal characters\n");
                displayChoice();            // Display until right input is given
                break;
            }
        }
    }

    private void movePlayer() {
        Scanner input = new Scanner(System.in);

        loop : while (true) {
            System.out.println("\nW - move up, S - move down, A - move left, D - move right");
            System.out.print("Enter your choice> ");
            String choice = input.nextLine().toUpperCase();
            switch (choice){
                case "W":
                    if (!player.moveUp()) {         // Checking the ability to move
                        System.out.println("We wish the board would be bigger too)))");
                        break;
                    } else { break loop; }          // Player moved
                case "S":
                    if (!player.moveDown()) {
                        System.out.println("We wish the board would be bigger too)))");
                        break;
                    } else { break loop; }
                case "A":
                    if (!player.moveLeft()) {
                        System.out.println("We wish the board would be bigger too)))");
                        break;
                    } else { break loop; }
                case "D":
                    if (!player.moveRight()) {
                        System.out.println("We wish the board would be bigger too)))");
                        break;
                    } else { break loop; }
                default:
                    System.out.println("Invalid input! Repeat!");
                    break;
            }
        }
    }

    private void useSpell() {
        Scanner input = new Scanner(System.in);

        loop: while (true) {
            // If there is no more spells left
            if (spell.runOutOfSpells()) {
                System.out.println("\nYou are out of the available spells(\n");
                break loop;
            }

            spell.listAllSpells();          // All choices
            System.out.print("Please select> ");
            String choice = input.nextLine();

            // Check the number of available spells to avoid runtime
            if (Spell.getSpellsCounter() == 3) {
                switch (choice) {
                    case "1":
                        spell.useSpellAtIndex(0, player, board);
                        break loop;
                    case "2":
                        spell.useSpellAtIndex(1, player, board);
                        break loop;
                    case "3":
                        spell.useSpellAtIndex(2, player, board);
                        break loop;
                    default:
                        System.out.println("Invalid input! Re-enter!");
                        break;
                }
            } else if (Spell.getSpellsCounter() == 2) {
                switch (choice) {
                    case "1":
                        spell.useSpellAtIndex(0, player, board);
                        break loop;
                    case "2":
                        spell.useSpellAtIndex(1, player, board);
                        break loop;
                    default:
                        System.out.println("Invalid input! Re-enter!");
                        break;
                }
            } else {
                switch (choice) {
                    case "1":
                        spell.useSpellAtIndex(0, player, board);
                        break loop;
                    default:
                        System.out.println("Invalid input! Re-enter!");
                        break;
                }
            }
        }
    }

    private Player getPlayerAtInitialPostion() {
        // Get user initial position on the board
        System.out.print("Please enter initial position> ");
        Scanner input = new Scanner(System.in);
        String initialPosition = input.next().trim().toUpperCase();

        // Setting up the all possible user inputs
        ArrayList<String> allPossibleInputs = new ArrayList<>();
        char firstLetter = 'A';
        for (int i = 0; i < board.getboardDimension(); i++) {
            for (int j = 1; j < 11; j++) {
                allPossibleInputs.add(firstLetter + "" + j);
            }
            firstLetter++;
        }

        // Check the correctness of input
        while (!allPossibleInputs.contains(initialPosition)) {
            System.out.print("Wrong input. Ex: 'B5'. Please re-enter> ");
            initialPosition = input.next().trim().toUpperCase();
        }

        int rowIndex = initialPosition.charAt(0) - 65;                          // The row index on the board
        int columnIndex = Integer.parseInt(initialPosition.substring(1)) - 1;   // The column index on the board

        return new Player(rowIndex, columnIndex);
    }

    private void displayRemaining() {
        // Setup the view of Tokimons count
        System.out.println(".::You already collected " + Board.getTokiFoundCounter() + " Tokimons::.");
        System.out.println(".::You need to collect " + (board.getTokiNum() -
                Board.getTokiFoundCounter()) + " more::.");
        System.out.println(".::You have " + Spell.getSpellsCounter() + " spells remaining\n");
    }

    private void displayBoard() {
        // Setup print for columns
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLetter = 'A';
        for (int i = 0; i < board.getboardDimension(); i++) {
            // Setup print for rows
            System.out.print(rowLetter++);
            for (int j = 0; j < board.getboardDimension(); j++) {
                Cell currentCell = board.retrieveCell(i, j);                 // Get Cell
                if (player.getRow() == i && player.getColumn() == j) {       // Display player
                    System.out.print(" " + (player.getSymbol() + "" + currentCell.getCreatureSymbol()).trim());
                } else if (currentCell.isVisited()) {                        // Display the cell that has been visited
                    System.out.print(" " + currentCell.getCreatureSymbol());
                }
                else {
                    System.out.print(" " + "~");                    // The cell has not been visited yet
                }
            }
            System.out.println();       // New row
        }
    }
}
