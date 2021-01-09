package ca.cmpt213.assn3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Board class models the information about a
 * playing board. Data includes list of cells,
 * number of tokimons and fokimons, and cheat mode.
 * It supports computing spells, populate random cells,
 * process visited cells.
 */
public class Board {
    ArrayList<Cell> boardCells;
    private final int TOKI_NUM;
    private final int FOKI_NUM;
    private final boolean CHEAT_ON;

    private final int BOARD_DIMENSION = 10;
    private static int tokiFoundCounter = 0;

    public Board(int TOKI_NUM, int FOKI_NUM, boolean CHEAT_ON) {
        boardCells = new ArrayList<>();
        this.TOKI_NUM = TOKI_NUM;
        this.FOKI_NUM = FOKI_NUM;
        this.CHEAT_ON = CHEAT_ON;
    }

    public int getboardDimension() {
        return BOARD_DIMENSION;
    }

    public int getTokiNum() {
        return TOKI_NUM;
    }

    public static int getTokiFoundCounter() {
        return tokiFoundCounter;
    }

    public void incrementTokiCounter() {
        tokiFoundCounter++;
    }

    public Cell retrieveCell(int row, int column) {
        // Retrieve the cell according to its row and column
        for (Cell cell : boardCells) {
            if (cell.getROW() == row && cell.getCOLUMN() == column) {
                return cell;
            }
        }
        // Default return if such Cell does not exist
        return null;
    }

    public void revealAllCells() {
        for (Cell cell : boardCells) {
            cell.setVisited(true);
        }
    }

    public boolean processVisitedCell(Cell cell) {
        if (cell.getCreatureSymbol() == '$' && !cell.isVisited()) {          // Tokimon symbol
            incrementTokiCounter();
            // Make cell revealed when visited
            cell.setVisited(true);
            return true;
        } else if (cell.getCreatureSymbol() == 'X') {   // Fokimon symbol
            return false;
        }
        return true;
    }

    public void populateBoard() {
        // Create default cells
        for (int i = 0; i < BOARD_DIMENSION; i++) {
            for (int j = 0; j < BOARD_DIMENSION; j++) {
                boardCells.add(new Cell(
                        i,                  // Row
                        j,                  // Column
                        CHEAT_ON,           // The visibility of a cell's creature
                        ' '));  // Default value for the cell creature
            }
        }

        // Shuffle the list to randomize the cells
        Collections.shuffle(boardCells);

        //Assign the Tokis and Fokis for randomized cells
        for (int i = 0; i < TOKI_NUM; i++) {
            boardCells.get(i).setCreatureSymbol('$');
        }
        for (int i = TOKI_NUM; i < FOKI_NUM + TOKI_NUM; i++) {
            boardCells.get(i).setCreatureSymbol('X');
        }
    }

    public static int[] jumpToSomeLocation() {
        Random random = new Random();
        int randRow = random.nextInt(10);       // Random int, range [0,9]
        int randColumn = random.nextInt(10);    // Random int, range [0,9]
        int[] randLocation = {randRow, randColumn};
        return randLocation;
    }

    public boolean revealTokimon() {
        // Find unrevealed Tokimons
        for (Cell cell : boardCells) {
            // If is a Tokimon and not yet visited
            if (cell.getCreatureSymbol() == '$' && !cell.isVisited()) {
                cell.setVisited(true);
                incrementTokiCounter();
                return true;
            }
        }
        return false;       // All tokimons revealed
    }

    public boolean killFokimon() {
        // Find unrevealed Fokimons
        for (Cell cell : boardCells) {
            if (cell.getCreatureSymbol() == 'X' && !cell.isVisited()) {
                cell.setVisited(true);
                return true;
            }
        }
        return false;       // No fokimons found
    }
}
