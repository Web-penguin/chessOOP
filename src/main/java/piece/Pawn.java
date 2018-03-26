package piece;

import constants.Constants;
import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    // this flag check if this is the first time for Pawn to move
    private boolean firstTimeMove;

    /**
     * Constructor: initialize a Pawn Object
     * @param board the chessBoard that we are currently using
     * @param player the player that holds the piece
     */
    public Pawn(ChessBoard board, Player player) {
        super(Constants.PAWN, board, player);
        this.firstTimeMove = true;   // it is pawn first move, it can take 2 squares up.
        if (player == Player.WHITE) {
            this.pieceImagePath = Constants.PAWN_WHITE_PATH;
        } else {
            this.pieceImagePath = Constants.PAWN_BLACK_PATH;
        }
    }

    /**
     * Assume no promotion for pawn
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        ChessBoard board = this.getChessBoard();

        List<Coordinate> coords = new ArrayList<>();
        // if white player then move +1, else move -1
        int possibleMove = (this.player == Player.WHITE) ? 1 : -1;
        if (currentYCoord + possibleMove >= board.getHeight() || currentYCoord + possibleMove < 0) {
            return coords;
        }

        if (board.getPieceAtCoordinate(currentXCoord, currentYCoord + possibleMove) == null) {
            if (this.firstTimeMove &&
                    board.getPieceAtCoordinate(currentXCoord, currentYCoord + possibleMove * 2) == null) {
                coords.add(new Coordinate(currentXCoord, currentYCoord + possibleMove * 2));
            }
            coords.add(new Coordinate(currentXCoord, currentYCoord + possibleMove));
        }

        List<Integer> rightAndLeftOffsetByX = Constants.PAWN_POSSIBLE_OFFSET;
        for (Integer offset : rightAndLeftOffsetByX) {
            // there is an opponent piece on the left or right side
            if (board.getPieceAtCoordinate(currentXCoord + offset, currentYCoord + possibleMove) != null &&
                    board.getPieceAtCoordinate(currentXCoord + offset, 
                            currentYCoord + possibleMove).player != this.player) {
                coords.add(new Coordinate(currentXCoord + offset, currentYCoord + possibleMove));
            }
        }

        return coords;
    }

    /**
     * Set coordinate for pawn object.
     * Change its firstTimeMove flag if necessary
     * @param x the x coordinate to put the piece
     * @param y the y coordinate to put the piece
     * @return success or not
     */
    public boolean setCoordinate(int x, int y) {
        // first time init
        this.firstTimeMove = this.xCoordinate == Constants.OUT_OF_BOARD || this.yCoordinate == Constants.OUT_OF_BOARD;
        return super.setCoordinate(x, y);
    }

    /**
     * Set coordinate for pawn object without modifying the first_time_move_flag
     * @param x the x coordinate to put the piece
     * @param y the y coordinate to put the piece
     */
    public void setCoordinateWithoutChangingFirstTimeMoveFlag(int x, int y) {
        super.setCoordinate(x, y);
    }
}