package ca.cmpt213.assn3.model;

/**
 * Player class models the information about a
 * player of the game. Data includes position of
 * a player, and symbol. It supports moving player
 * on the board.
 */
public class Player {
    private int row;
    private int column;
    private final char PLAYER_SYMBOL = '@';

    // Default constructor
    public Player(){}

    public Player(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public char getSymbol() {
        return PLAYER_SYMBOL;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean moveUp() {
        if (row > 0) {
            row--;              // Moved
            return true;
        } else {
            return false;       // No space available
        }
    }

    public boolean moveDown() {
        if (row < 9) {
            row++;              // Moved
            return true;
        } else {
            return false;       // No space available
        }
    }

    public boolean moveLeft() {
        if (column > 0) {
            column--;           // Moved
            return true;
        } else {
            return false;       // No space available
        }
    }

    public boolean moveRight() {
        if (column < 9) {
            column++;           // Moved
            return true;
        } else {
            return false;       // No space available
        }
    }
}
