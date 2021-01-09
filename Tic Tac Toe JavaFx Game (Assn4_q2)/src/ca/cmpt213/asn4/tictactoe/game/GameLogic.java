package ca.cmpt213.asn4.tictactoe.game;

/**
 * GameLogic class models the information about a
 * single game of TicTacToe. Data includes the array of int
 * to store information about a board cells, number of moves made,
 * and knows when game is finished. It supports setting value to a cell,
 * knows when player has won, knows when game is finished.
 */
public class GameLogic {
    private int board [][];             // Board values
    private int movesCount;             // Number of players moves
    private boolean isFinished;         // When game is finished

    public GameLogic() {
        this.board = new int[4][4];         // New clear board
        this.movesCount = 0;
        initializeCell();
    }

    private void initializeCell() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;             // The value of "0" represents a never clicked cell
            }
        }
    }

    public void setCellValue(int row, int column) {
        try {
            // Has never been clicked
            if (board[row][column] == 0) {
                if (movesCount % 2 == 0) {          // The turn of X
                    board[row][column] = 1;         // The value of "1" represents cell occupied by X
                } else {
                    board[row][column] = 2;         // The value of "2" represents cell occupied by O
                }
                movesCount++;
            }
        } catch (NullPointerException e) {
            System.out.println("Index out of bounds!");
            System.exit(-1);
        }
    }

    public boolean hasWon(int row, int column) {
        return wonVertically(row, column) ||        // Same 4 icons vertically
                wonHorizontally(row, column) ||     // Same 4 icons horizontally
                wonDiagonally(row, column);         // Same 4 icons diagonally
    }

    private boolean wonDiagonally(int row, int column) {
        // Checking from left top to right bottom diagonal for the same icon
        int sameValue1 = 0;
        for (int i = 0; i < 4; i++) {
            if (board[i][i] == getCellValue(row, column)) {
                sameValue1++;
            }
            if (sameValue1 == 4) {
                return true;
            }
        }

        // Checking from left bottom to right top diagonal for the same icon
        int sameValue2 = 0;
        for (int i = 0; i < 4; i++) {
            if (board[i][3 - i] == getCellValue(row, column)) {
                sameValue2++;
            }
            if (sameValue2 == 4) {
                return true;
            }
        }

        return false;
    }

    private boolean wonHorizontally(int row, int column) {
        // Checking for each column at the same row for the same icon
        int sameValue = 0;
        for (int i = 0; i < 4; i++) {
            if (board[row][i] == getCellValue(row, column)) {
                sameValue++;
            }
            if (sameValue == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean wonVertically(int row, int column) {
        // Checking for each row at the same column for the same icon
        int sameValue = 0;
        for (int i = 0; i < 4; i++) {
            if (board[i][column] == getCellValue(row, column)) {
                sameValue++;
            }
            if (sameValue == 4) {
                return true;
            }
        }
        return false;
    }

    public int getMovesCount() {
        return movesCount;
    }

    public int getCellValue(int row, int column) {
        return board[row][column];
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
}
