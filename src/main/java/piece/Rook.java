package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    /**
     * Constructor: initialize a Rook Object
     * @param board the chessBoard that we are currently using
     * @param player the player that holds the piece
     */
    public Rook(ChessBoard board, Player player) {
        super(Constants.ROOK, board, player);
        if (player == Player.WHITE) {
            this.pieceImagePath = Constants.ROOK_WHITE_PATH;
        }
        else {
            this.pieceImagePath = Constants.ROOK_BLACK_PATH;
        }
    }

    /**
     *Get all possible move coordinates for rook
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        ChessBoard chessBoard = this.getChessBoard();
        List<Coordinate> coords = new ArrayList<>();
        List<Integer> possibleMoves = Constants.ROOK_POSSIBLE_OFFSET;

        int newCoordinate;
        for (Integer possibleMove : possibleMoves) {
            // go left and right
            for (newCoordinate = currentXCoord + possibleMove;
                 newCoordinate >= 0 && newCoordinate < chessBoard.getWidth();
                 newCoordinate = newCoordinate + possibleMove) {
                // add to coords if valid; if this return true, then it meets other pieces.
                if (addToCoordinatesIfValid(coords, newCoordinate, currentYCoord))
                    break;
            }
            // go up and down
            for (newCoordinate = currentYCoord + possibleMove;
                 newCoordinate >= 0 && newCoordinate < chessBoard.getHeight();
                 newCoordinate = newCoordinate + possibleMove) {
                if (addToCoordinatesIfValid(coords, currentXCoord, newCoordinate))
                    break;
            }
        }
        return coords;
    }
}
