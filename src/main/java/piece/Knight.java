package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    /**
     * Constructor: initialize a Knight Object
     * @param board chessBoard that we are currently using
     * @param player player that holds the piece
     */
    public Knight(ChessBoard board, Player player) {
        super(Constants.KNIGHT, board, player);
        if (player == Player.WHITE) {
            this.pieceImagePath = Constants.KNIGHT_WHITE_PATH;
        } else {
            this.pieceImagePath = Constants.KNIGHT_BLACK_PATH;
        }
    }

    /**
     *  Get all possible move coordinates for knight
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        ChessBoard board = this.getChessBoard();
        List<Coordinate> coords = new ArrayList<>();
        List<Integer> possibleMovesByCoordinates = Constants.KNIGHT_POSSIBLE_OFFSET;
        for (Integer possibleMovesByXCoordinate : possibleMovesByCoordinates) {
            for (Integer possibleMovesByYCoordinate : possibleMovesByCoordinates) {
                if (!(possibleMovesByXCoordinate.equals(possibleMovesByYCoordinate) ||
                        possibleMovesByXCoordinate.equals(-1 * possibleMovesByYCoordinate))) {
                    int x = this.xCoordinate + possibleMovesByXCoordinate;
                    int y = this.yCoordinate + possibleMovesByYCoordinate;
                    if (x >= 0 && y >= 0 && x < board.getWidth() && y < board.getHeight()) {
                        // add to coords if the piece can move to that coordinate
                        addToCoordinatesIfValid(coords, x, y);
                    }
                }
            }
        }
        return coords;
    }
}