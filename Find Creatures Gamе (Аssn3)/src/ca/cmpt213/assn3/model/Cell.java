package ca.cmpt213.assn3.model;

/**
 * Cell class models the information about a
 * single cell on the board. Data includes cell's position,
 * visibility, and symbol of Creature.
 */
public class Cell {
    private final int ROW;
    private final int COLUMN;
    private boolean isVisited;              // Visibility
    private char creatureSymbol;            // Creature inside a cell

    public Cell(int ROW, int COLUMN, boolean isVisited, char creatureSymbol) {
        this.ROW = ROW;
        this.COLUMN = COLUMN;
        this.isVisited = isVisited;
        this.creatureSymbol = creatureSymbol;
    }

    public int getROW() {
        return ROW;
    }

    public int getCOLUMN() {
        return COLUMN;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public char getCreatureSymbol() {
        return creatureSymbol;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public void setCreatureSymbol(char creatureSymbol) {
        this.creatureSymbol = creatureSymbol;
    }

}