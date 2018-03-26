package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    /**
     * Constructor: initialize Queen Object
     * @param board the chessBoard that we are currently using
     * @param player player that holds the piece
     */
    public Queen(ChessBoard board, Player player) {
        super(Constants.QUEEN, board, player);
        if (player == Player.WHITE) {
            this.pieceImagePath = Constants.QUEEN_WHITE_PATH;
        }
        else {
            this.pieceImagePath = Constants.QUEEN_BLACK_PATH;
        }
    }

    /**
     * Get all possible move coordinates for queen
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        ChessBoard chessBoard = this.getChessBoard();
        List<Coordinate> coords = new ArrayList<>();
        List<Integer> possibleMoves = Constants.QUEEN_POSSIBLE_OFFSET;

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

        for (Integer possibleMove : possibleMoves) {
            // go left and right
            for (xCoord = currentXCoord + possibleMove;
                 xCoord < chessBoard.getWidth() && xCoord >= 0; xCoord = xCoord + possibleMove) {
                if (addToCoordinatesIfValid(coords, xCoord, currentYCoord))
                    break;
            }
            // go up and down
            for (yCoord = currentYCoord + possibleMove;
                 yCoord < chessBoard.getHeight() && yCoord >= 0; yCoord = yCoord + possibleMove) {
                if (addToCoordinatesIfValid(coords, currentXCoord, yCoord))
                    break;
            }
        }
        return coords;
    }

}
