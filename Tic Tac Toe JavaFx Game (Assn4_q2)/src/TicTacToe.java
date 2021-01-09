import ca.cmpt213.asn4.tictactoe.ui.BoardLayout;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * TicTacToe class is the class with the main method
 * It supports starting the application.
 */
public class TicTacToe extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BoardLayout boardLayout = new BoardLayout(primaryStage);        // Make Board's UI
        boardLayout.startStage();                                       // Starts the application
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}
