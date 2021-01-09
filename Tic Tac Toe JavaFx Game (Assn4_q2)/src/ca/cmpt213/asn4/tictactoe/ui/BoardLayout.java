package ca.cmpt213.asn4.tictactoe.ui;

import ca.cmpt213.asn4.tictactoe.game.GameLogic;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * BoardLayout class models the UI of the game
 * Data includes the Stage, Grid pane, game logic,
 * and status label. It supports creating new board
 * and makes game interactive.
 */
public class BoardLayout {
    private final Stage primaryStage;       // Stage
    private GridPane imageCells;            // Grid pane of the cells
    private GameLogic gameLogic;            // The logic
    private Label status;                   // Label for the status of the game

    public BoardLayout(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void startStage () {
        //Setup the new game
        gameLogic = new GameLogic();

        // Cells for the board
        imageCells = new GridPane();

        // Set empty cells and new board
        setNewGridPane();

        // Label for the initial prompt
        status = new Label("X, it's your turn");

        // New game button
        Button newGameButton = new Button("NEW GAME");
        newGameButton.setPrefSize(110,50);
        newGameButton.setOnAction(new ButtonHandler());

        // Create vertical box layout
        VBox vbox = new VBox(30, imageCells, status, newGameButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30));

        Scene scene = new Scene(vbox);              // New scene with vbox

        primaryStage.setScene(scene);               // Set scene
        primaryStage.setTitle("4x4 Tic tac Toe");   // Title
        primaryStage.show();                        // Show Stage
    }

    private void setNewGridPane() {
        ArrayList<ImageView> listOfCells = new ArrayList<>();               // List of the image views
        final String cellImage = "file:img/cell.PNG";                       // Image file location
        for (int i = 0; i < 16; i++) {
            ImageView imageView = new ImageView(new Image(cellImage));
            imageView.setFitWidth(80);                                      // Size
            imageView.setPreserveRatio(true);                               // Preserve image sizes
            // Add handler for the board cells clicks
            imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new ImageCellHandler());
            listOfCells.add(imageView);
        }

        // Add image views to a grid pane
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                imageCells.add(listOfCells.get(index++), i, j);
            }
        }

        // Gaps between cells
        imageCells.setVgap(20);
        imageCells.setHgap(20);

        // Gap from scene's edges
        imageCells.setPadding(new Insets(30));

        // Set gridPane aligned
        imageCells.setAlignment(Pos.CENTER);
    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            setNewGridPane();                       // New board
            gameLogic = new GameLogic();            // New game
            status.setText("X, it's your turn");    // Status of the game
        }
    }

    public class ImageCellHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle (MouseEvent event) {
            // Check if there is already a winner
            if (gameLogic.isFinished()) {
                return;
            }

            ImageView cell = (ImageView)event.getSource();      // Clicked image view
            int row = GridPane.getRowIndex(cell);               // Position: row
            int column = GridPane.getColumnIndex(cell);         // Position: column

            // If cell has not been visited yet
            if (gameLogic.getCellValue(row, column) == 0) {
                // The X has clicked
                if (gameLogic.getMovesCount() % 2 == 0) {
                    ((ImageView) event.getSource()).setImage(new Image("file:img/cross.png"));
                    status.setText("O, it's your turn");
                } else { // The O has clicked
                    ((ImageView) event.getSource()).setImage(new Image("file:img/circle.png"));
                    status.setText("X, it's your turn");
                }
                gameLogic.setCellValue(row, column);                  // Process the cell value

                if (gameLogic.hasWon(row, column)) {
                    if (gameLogic.getCellValue(row, column) == 1) {
                        status.setText("X have won! You are allergic to loses!");
                    }
                    else {
                        status.setText("O have won! Better luck next time, X)!");
                    }
                    // Finish the game
                    gameLogic.setFinished(true);
                } else if (gameLogic.getMovesCount() == 16) {               // A draw case
                    status.setText("It's a draw! We all winners here!");
                }
            }
        }
    }
}
