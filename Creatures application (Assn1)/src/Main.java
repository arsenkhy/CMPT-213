import ca.cmpt213.ModelToki.TokiManager;
import ca.cmpt213.ModelToki.Tokimon;
import ca.cmpt213.UserInterface.UIText;

/**
 * Main class class to launch
 * application from. Models User
 * Interface class for user to
 * interact.
 */
public class Main {
    /**
     * Additional method for quicker testing purposes
     * @param shortList (required)
     */
    public static void populate(TokiManager shortList) {
        shortList.addNewToki(new Tokimon("Arsen", "Water", 4.5, 1.2));
        shortList.addNewToki(new Tokimon("Tokimon", "Fire", 3.5, 0.1));
        shortList.addNewToki(new Tokimon("Rather", "Electro", 3, 92));
        shortList.addNewToki(new Tokimon("Jeveux", "HelloWorld", 1, 32));
    }

    public static void main(String[] args) {
        TokiManager tokiManager = new TokiManager();
        /* populate(tokiManager);        // Can make use of initial populating */

        UIText show = new UIText(tokiManager);
        show.displayMainMenu();
    }
}
