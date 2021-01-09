package ca.cmpt213.UserInterface;

import ca.cmpt213.ModelToki.TokiManager;
import ca.cmpt213.ModelToki.Tokimon;
import java.util.Scanner;

/**
 * UIText class models the application interface
 * between user and code. Prints the list of options,
 * list of Tokimons and takes input from user.
 *
 * Invariant 1: Cannot take inputs for strength
 *              that exceed range of [0, 100].
 * Invariant 2: Cannot take negative inputs
 *              for height and weight.
 */
public class UIText {
    private TokiManager tokimonList;
    Scanner in = new Scanner(System.in);            // Taking inputs from user

    // Constructor
    public UIText(TokiManager tokimonList) {
        this.tokimonList = tokimonList;
    }

    // Return the existence of tokimon in the List by chosen index
    private boolean isValidIndex(int index) {
        return (index <= TokiManager.getCounter())
                && index > 0;
    }

    private boolean isNegative(double userInput) {
        return userInput < 0;
    }

    // Checking if input breaks class invariant 1
    private boolean strengthExceedsRange(Tokimon tokimon, int difference) {
        int newStrength = difference + tokimon.getStrength();
        return  newStrength > 100 || newStrength < 0;
    }

    private void displaySelection() {
        tokimonList.displayAllTokis();
        System.out.print("(Enter 0 to cancel)" + "\n> ");
    }

    /**
     * Displays the main menu.
     * Takes user inputs.
     */
    public void displayMainMenu() {
        // The initial print title
        System.out.println("* * * * * * * * * * * * * * * * ");
        System.out.println("* Welcome to Tokimon Tracker! *");
        System.out.println("* * * * * * * * * * * * * * * * ");

        finishLoop:
        while (true) {
            // Displaying the main menu for choosing the options
            System.out.println("\n.:: Main Menu ::.\n");
            System.out.print("1. List Tokimons\n" +
                    "2. Add a new Tokimon\n" +
                    "3. Remove a Tokimon\n" +
                    "4. Change Tokimon strength\n" +
                    "5. DEBUG: Dump objects (toString)\n" +
                    "6. Exit\n" + "> ");
            int choice = in.nextInt();      // User chosen option

            // switch statement to check user choice
            switch (choice) {

                // performs printing of all Tokimons
                case 1:
                    tokimonList.displayAllTokis();
                    break;

                // performs the creation and addition of new Tokimon to tracker
                case 2:
                    // Taking user input
                    System.out.print("Enter Tokimon's name: ");
                    String name = in.next();
                    System.out.print("Enter Tokimon's type: ");
                    String type = in.next();

                    System.out.print("Enter Tokimon's height: ");
                    double height = in.nextDouble();

                    // Prompting user if input is invalid
                    while (isNegative(height)) {
                        System.out.print("Please enter non-negative height: ");
                        height = in.nextDouble();
                    }

                    System.out.print("Enter Tokimon's weight: ");
                    double weight = in.nextDouble();

                    // Prompting user if input is invalid
                    while (isNegative(weight)) {
                        System.out.print("Please enter non-negative weight: ");
                        weight = in.nextDouble();
                    }

                    // Addition to the list
                    tokimonList.addNewToki(new Tokimon(name, type, height, weight));
                    System.out.println("\tThe " + name + " is successfully added!");
                    break;

                // performs the removal of chosen Tokimon from the tracker
                case 3:
                    // Show available tokimons and user option retrieval
                    displaySelection();
                    int option = in.nextInt();

                    // Check if input meets requirements
                    if (option == 0) {
                        break;          // removal cancelled
                    } else if (isValidIndex(option)) {
                        // removal of corresponded Tokimon
                        tokimonList.deleteToki(option - 1);
                        System.out.println("The tokimon has been removed!");
                        break;
                    } else {
                        System.out.println("The option does not exist!");
                        break;
                    }

                // performs the change of a particular Tokimon's strength
                case 4:
                    // Show available tokimons and user option retrieval
                    displaySelection();
                    int input = in.nextInt();

                    // checking if input is correct
                    if (input == 0) {
                        break;          // option cancelled
                    } else if (isValidIndex(input)) {
                        System.out.print("By how much?: ");
                        int difference = in.nextInt();

                        // retrieval of Tokimon at a corresponded index
                        int indexOfTokimon = input - 1;
                        Tokimon currentTokimon = tokimonList.getTokimonList().get(indexOfTokimon);

                        // prompting user if input does not meet class invariant requirement
                        while (strengthExceedsRange(currentTokimon, difference)) {
                            System.out.print("Strength cannot exceed range: 0 to 100!\nBy how much?: ");
                            difference = in.nextInt();
                        }

                        // Changing the Tokimon's strength
                        tokimonList.alterToki(indexOfTokimon, difference);
                        System.out.println(currentTokimon.getName() + " now has strength "
                                    + currentTokimon.getStrength() + "!");
                        break;
                    } else {
                        System.out.println("The option does not exist!");
                        break;
                    }

                // performs the display toString() on each Tokimon
                case 5:
                    System.out.println("All Tokimon objects: ");
                    int listing = 0;        //order of tokimons
                    for (Tokimon tokimon : tokimonList.getTokimonList()) {
                        System.out.println(++listing + ". " + tokimon);
                    }
                    break;

                // performs the exit of the application
                case 6:
                    System.out.println("\nGoodbye!");
                    break finishLoop;

                // performs the prompt if chosen option does not exist
                default:
                    System.out.println("ERROR! The option you entered does not exist(((");
            }
        }
    }
}
