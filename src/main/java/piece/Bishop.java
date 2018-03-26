package piece;

import constants.Constants;
import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    /**
     * Constructor: initialize a Bishop Object
     * @param board chessBoard we are using
     * @param player player that holds the piece
     */
    public Bishop(ChessBoard board, Player player) {
        super(Constants.BISHOP, board, player);

        if (player.equals(Player.WHITE)) {
            this.pieceImagePath = Constants.BISHOP_WHITE_PATH;
        }
        else {
            this.pieceImagePath = Constants.BISHOP_BLACK_PATH;
        }
    }


    /**
     * Get all possible move coordinates for bishop
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        ChessBoard chessBoard = this.chessBoard;
        List<Coordinate> coords = new ArrayList<>();

        int xCoord, yCoord;
        // go direction of left top
        for (xCoord = currentXCoord - 1, yCoord = currentYCoord + 1;
             xCoord >= 0 && yCoord < chessBoard.getHeight(); xCoord--, yCoord++) {
            // add to coords if valid; if this return true, then it meets other pieces.
            if (addToCoordinatesIfValid(coords, xCoord, yCoord))
                break;
        }
        // go direction of right top
        for (xCoord = currentXCoord + 1, yCoord = currentYCoord + 1;
             xCoord < chessBoard.getWidth() && yCoord < chessBoard.getHeight(); xCoord++, yCoord++) {
            if (addToCoordinatesIfValid(coords, xCoord, yCoord))
                break;
        }
        // go direction of left bottom
        for (xCoord = currentXCoord - 1, yCoord = currentYCoord - 1;
             xCoord >= 0 && yCoord >= 0; xCoord--, yCoord--) {
            if (addToCoordinatesIfValid(coords, xCoord, yCoord))
                break;
        }
        // go direction of right bottom
        for (xCoord = currentXCoord + 1, yCoord = currentYCoord - 1;
             xCoord < chessBoard.getWidth() && yCoord >= 0; xCoord++, yCoord--) {
            if (addToCoordinatesIfValid(coords, xCoord, yCoord))
                break;
        }
        return coords;
    }
}