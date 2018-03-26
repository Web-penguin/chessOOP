package chessboard;

import constants.Constants;
import piece.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Chessboard class
 */
public class ChessBoard {
    private int width;
    private int height;
    private Piece pieces[][];
    private Piece whiteKing;
    private Piece blackKing;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private int turn;

    /**
     *
     * Construct chessBoard
     * @param width: set the width of chessboard
     * @param height: set the height of chessboard
     */
    public ChessBoard(int width, int height) {
        this.width = width;
        this.height = height;

        // initialize array to store pieces
        this.pieces = new Piece[height][];
        for (int i = 0; i < height; i++) {
            this.pieces[i] = new Piece[width];
        }

        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();

        this.whiteKing = null;
        this.blackKing = null;
        // if even then WHITE else BLACK
        this.turn = 0;
    }

    /**
     * add piece to whitePieces or blackPieces array list
     * @param piece we want to save.
     */
    public void addPieceToList(Piece piece) {
        if (piece.getPlayer() == Player.WHITE) {
            this.whitePieces.add(piece);
        }
        else {
            this.blackPieces.add(piece);
        }
    }

    /**
     * Getter: return whitePieces
     * @return whitePieces array list
     */
    public List<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    /**
     * Getter: return blackPieces
     * @return blackPieces array list
     */
    public List<Piece> getBlackPieces() {
        return this.blackPieces;
    }


    /**
     * Getter: get whiteKing from WHITE player
     * @return king piece from WHITE player
     */
    public Piece getWhiteKing() {
        return this.whiteKing;
    }

    /**
     * Setter: set whiteKing
     * @param piece whiteKing
     */
    public void setWhiteKing(Piece piece) {
        this.whiteKing = piece;
    }

    /**
     * Getter: get blackKing from BLACK player
     * @return king piece from BLACK player
     */
    public Piece getBlackKing() {
        return this.blackKing;
    }

    /**
     * Setter: set blackKing
     * @param piece blackKing
     */
    public void setBlackKing(Piece piece) {
        this.blackKing = piece;
    }

    /**
     * Return the piece at given coordinate
     * @param x the x coordinate
     * @param y the y coordinate
     * @return piece at (x, y) coordinate. If coordinate empty, return null.
     */
    public Piece getPieceAtCoordinate(int x, int y) {
        if (x >= this.width || x < 0 || y >= this.height || y < 0) // outside the boundary
            return null;
        return this.pieces[y][x];
    }

    /**
     * Store the piece at (x, y)
     * @param piece want to set
     * @param x coordinate
     * @param y coordinate
     */
    public void setPieceAtCoordinate(Piece piece, int x, int y) {
        this.pieces[y][x] = piece;
    }

    /**
     * Getter: get the width of chessboard
     * @return width of chessboard
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Getter: get the height of chessboard
     * @return height of chessboard
     */
    public int getHeight() {
        return this.height;
    }


    /**
     * Remove piece from chessboard
     * @param piece that want to remove
     */
    public void removePiece(Piece piece) {
        int x = piece.getXCoordinate();
        int y = piece.getYCoordinate();
        this.pieces[y][x] = null;
    }

    /**
     * increment turn
     */
    public void incrementTurn() {
        this.turn++;
    }

    /**
     * Getter: get turn
     * @return turn of the game
     */
    public int getTurn() {
        return this.turn;
    }

    /**
     * Get the player for this turn
     * @return the player for this turn
     */
    public Player getPlayerForThisTurn() {
        // if even then WHITE else BLACK
        if (this.turn % 2 == 0) {
            return Player.WHITE;
        }
        return Player.BLACK;
    }

    /**
     *
     * Check if player can move any piece
     * @param player player id
     * @return return true if player cannot move any piece else false
     */
    public boolean playerCannotMove(Player player) {
        List<Piece> playerPieces = ((player == Player.WHITE) ? this.getWhitePieces() : this.getBlackPieces());
        for (Piece piece : playerPieces) {
            // out of board
            if (piece.getXCoordinate() == Constants.OUT_OF_BOARD || piece.getYCoordinate() == Constants.OUT_OF_BOARD)
                continue;
            List<Coordinate> coords = piece.getPossibleMoveCoordinate();
            for (Coordinate coord : coords) {
                if (!piece.isSuicideMove(coord.getX(), coord.getY())) {
                    // can move
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Check if player in stalemate.
     * @param player player id
     * @return true if stalemate else return false.
     */
    public boolean isStalemate(Player player) {
        King king = player == Player.WHITE ? (King) this.whiteKing : (King) this.blackKing;
        // king is not in check and player cannot move
        return !king.isInCheck() && playerCannotMove(player);
    }


    /**
     * Generate standard chessBoard
     */
    public void generateStandardBoard() {
        Piece piece;
        List<Player> playerList = Arrays.asList(Player.WHITE, Player.BLACK);
        for (Player currentPlayer : playerList) {
            List<Piece> piecesQueue = Arrays.asList(
                    new Rook(this, currentPlayer),
                    new Knight(this, currentPlayer),
                    new Bishop(this, currentPlayer),
                    new Queen(this, currentPlayer),
                    new King(this, currentPlayer),
                    new Bishop(this, currentPlayer),
                    new Knight(this, currentPlayer),
                    new Rook(this, currentPlayer));
            for (int j = 0; j < piecesQueue.size(); j++) {
                int yFirstLineCoordinate;
                int ySecondLineCoordinate;
                if (currentPlayer.equals(Player.WHITE)) {
                    yFirstLineCoordinate = Constants.WHITE_FIRST_LINE_Y;
                    ySecondLineCoordinate = Constants.WHITE_SECOND_LINE_Y;
                } else {
                    yFirstLineCoordinate = Constants.BLACK_FIRST_LINE_Y;
                    ySecondLineCoordinate = Constants.BLACK_SECOND_LINE_Y;
                }
                piece = piecesQueue.get(j);
                piece.setCoordinate(j, yFirstLineCoordinate);
                piece = new Pawn(this, currentPlayer);
                piece.setCoordinate(j, ySecondLineCoordinate);
            }
        }
    }
}
