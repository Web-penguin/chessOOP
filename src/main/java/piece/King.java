package piece;

import constants.Constants;
import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    /**
     * Constructor: Initialize a King Object
     * @param board chessBoard we are currently using
     * @param player player that holds the piece
     */
    public King(ChessBoard board, Player player) {
        super(Constants.KING, board, player);
        if (player == Player.WHITE) {
            this.pieceImagePath = Constants.KING_WHITE_PATH;
            if (board.getWhiteKing() == null) {
                board.setWhiteKing(this);
            }
        }
        else {
            this.pieceImagePath = Constants.KING_BLACK_PATH;
            if (board.getBlackKing() == null) {
                board.setBlackKing(this);
            }
        }
    }

    /**
     * Get all possible move coordinates for king
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public List<Coordinate> getPossibleMoveCoordinate() {
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        List<Coordinate> coords = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // look all available positions without (0, 0) offset
                if (!(i == 0 && j == 0)) {
                    addToCoordinatesIfValid(coords, currentXCoord + i, currentYCoord + j);
                }
            }
        }
        return coords;
    }

    /**
     * Check if king is in check
     * @return true if king is in check else false
     */
    public boolean isInCheck() {
        List<Piece> opponentPieces;

        // get opponent pieces
        if (this.player == Player.WHITE) {
            opponentPieces = this.chessBoard.getBlackPieces();
        }
        else {
            opponentPieces = this.chessBoard.getWhitePieces();
        }
        for (Piece piece : opponentPieces) {
            // invalid coord
            if (piece.xCoordinate == Constants.OUT_OF_BOARD || piece.yCoordinate == Constants.OUT_OF_BOARD)
                continue;
            List<Coordinate> coords = piece.getPossibleMoveCoordinate();
            for (Coordinate coord : coords) {
                // opponent next move could reach the king
                if (coord.getX() == this.xCoordinate && coord.getY() == this.yCoordinate) {
                    return true;
                }
            }
        }
        return false;
    }
}