package piece;

import constants.Constants;
import chessboard.ChessBoard;
import chessboard.Player;
import java.util.List;

public abstract class Piece {
    private String pieceName;

    protected int xCoordinate;
    protected int yCoordinate;

    protected Player player;
    protected ChessBoard chessBoard;

    public String pieceImagePath;

    /**
     * Initialize a main.piece object
     * @param pieceName set the main.piece name
     * @param chessBoard chessBoard where we put this piece
     * @param player the player id
     */
    public Piece(String pieceName, ChessBoard chessBoard, Player player) {
        this.pieceName = pieceName;
        this.xCoordinate = Constants.OUT_OF_BOARD;
        this.yCoordinate = Constants.OUT_OF_BOARD;
        this.chessBoard = chessBoard;
        this.player = player;
        this.chessBoard.addPieceToList(this);
    }

    /**
     * Set the coordinate of piece on chess chessBoard
     * @param x coordinate to put the piece
     * @param y coordinate to put the piece
     * @return if can put the piece at that coordinate return true else false
     */
    public boolean setCoordinate(int x, int y) {
        // invalid coordinate
        if (x < 0 || x >= this.chessBoard.getWidth()
                || y < 0 || y >= this.chessBoard.getHeight()
                || this.chessBoard.getPieceAtCoordinate(x, y) != null) {
            return false;
        }
        // main.piece is not just initialized.
        if (this.xCoordinate != Constants.OUT_OF_BOARD && this.yCoordinate != Constants.OUT_OF_BOARD) {
            this.removeSelf(); // remove self from current coordinate
        }
        this.xCoordinate = x;
        this.yCoordinate = y;
        // save this main.piece to chessBoard
        this.chessBoard.setPieceAtCoordinate(this, x, y);
        return true;
    }

    /**
     * Piece removes itself from chessBoard
     */
    public void removeSelf() {
        this.chessBoard.removePiece(this);
        this.xCoordinate = Constants.OUT_OF_BOARD;
        this.yCoordinate = Constants.OUT_OF_BOARD;
    }

    /**
     * Getter, get chessboard
     * @return chessBoard
     */
    public ChessBoard getChessBoard() {
        return this.chessBoard;
    }

    /**
     * Setter, set player
     * @param player player id
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Getter, return player
     * @return player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Getter, return the x coordinate of this piece
     * @return x coordinate of this piece
     */
    public int getXCoordinate() {
        return this.xCoordinate;
    }

    /**
     * Getter, return the y coordinate of this piece
     * @return y coordinate of this piece
     */
    public int getYCoordinate() {
        return  this.yCoordinate;
    }

    /**
     * Getter, return the piece name
     * @return piece name
     */
    public String getPieceName() {
        return this.pieceName;
    }

    /**
     * Getter, return piece image
     * @return image path of this piece
     */
    public String getPieceImagePath() {
        return this.pieceImagePath;
    }

    /**
     * If the piece can move to that coordinate, save (x, y) to coords
     * @param coords coordinates list
     * @param x coordinate to check
     * @param y coordinate to check
     * @return true if the piece at that coordinate is opponent piece or player piece to not go through
     */
    public boolean addToCoordinatesIfValid(List<Coordinate> coords, int x, int y) {
        // invalid coordinate
        if (x < 0 || y < 0 || x >= this.chessBoard.getWidth() || y >= this.chessBoard.getHeight()) {
            return false;
        }
        // the square is not occupied by any piece
        else if (this.chessBoard.getPieceAtCoordinate(x, y) == null) {
            coords.add(new Coordinate(x, y));
            return false;
        }
        // opponent piece
        else if (this.chessBoard.getPieceAtCoordinate(x, y).player != this.player) {
            coords.add(new Coordinate(x, y));
            return true;
        }
        // own piece
        else
            return true;
    }

    /**
     *
     * Check if this is a suicide move after that kins can be in check
     * @param moveToX the x coordinate we want to move our main.piece to
     * @param moveToY he y coordinate we want to move our main.piece to
     * @return return true if this move will cause king being checked.
     */
    public boolean isSuicideMove(int moveToX, int moveToY) {
        Player currentPlayer = this.player;
        int currentXCoord = this.xCoordinate;
        int currentYCoord = this.yCoordinate;
        boolean isSuicide = false;
        // get king
        Piece king =
                (currentPlayer == Player.WHITE ? this.chessBoard.getWhiteKing() : this.chessBoard.getBlackKing());

        // get main.piece that need to be removed
        Piece removePiece = this.chessBoard.getPieceAtCoordinate(moveToX, moveToY);

        // get opponent's pieces
        List<Piece> opponentPieces =
                (currentPlayer == Player.WHITE ? this.chessBoard.getBlackPieces() : this.chessBoard.getWhitePieces());

        // remove self
        if (removePiece != null) {
            removePiece.removeSelf();
        }
        // not to change pawns firstTimeMove
        if (this.pieceName.equals(Constants.PAWN)) {
            ((Pawn)this).setCoordinateWithoutChangingFirstTimeMoveFlag(moveToX, moveToY);
        }
        else {
            // move main.piece to that coordinate;
            this.setCoordinate(moveToX, moveToY);
        }

        for (Piece opponentPiece : opponentPieces) {
            // invalid coordinate
            if (opponentPiece.getXCoordinate() == Constants.OUT_OF_BOARD
                    || opponentPiece.getYCoordinate() == Constants.OUT_OF_BOARD)
                continue;
            List<Coordinate> coords = opponentPiece.getPossibleMoveCoordinate();
            for (Coordinate coord : coords) {
                if (coord.getX() == king.getXCoordinate() && coord.getY() == king.getYCoordinate()) {
                    isSuicide = true;
                    break;
                }
            }
            if (isSuicide) {
                break;
            }
        }

        // restore removePiece and main.piece
        if (this.pieceName.equals(Constants.PAWN))
            ((Pawn)this).setCoordinateWithoutChangingFirstTimeMoveFlag(currentXCoord, currentYCoord);
        else
            this.setCoordinate(currentXCoord, currentYCoord);
        if (removePiece != null) {
            if (removePiece.getPieceName().equals(Constants.PAWN))
                ((Pawn)removePiece).setCoordinateWithoutChangingFirstTimeMoveFlag(moveToX, moveToY);
            else
                removePiece.setCoordinate(moveToX, moveToY);
        }
        return isSuicide;
    }

    /**
     * Get possible move coordinates for this main.piece
     * @return List<Coordinate> Object that contains all possible move coordinates.
     */
    public abstract List<Coordinate> getPossibleMoveCoordinate();
}
