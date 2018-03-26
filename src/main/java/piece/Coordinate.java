package piece;

import constants.Constants;

/**
 * Coordinate Classx x, y for Piece
 */
public class Coordinate {
    // coordinates
    private int x;
    private int y;

    /**
     * Constructor: set default out of chessBoard
     */
    public Coordinate() {
        this.x = Constants.OUT_OF_BOARD;
        this.y = Constants.OUT_OF_BOARD;
    }

    /**
     * Constructor: set x and y
     * @param x coordinate
     * @param y coordinate
     */
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter, return x
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter, return Y
     * @return the y coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     *
     * @param object that should be checked
     * @return true if equals else false
     */
    @Override
    public boolean equals(Object object) {
        return ((this.x == ((Coordinate)object).x) && (this.y == ((Coordinate)object).y));
    }
}
