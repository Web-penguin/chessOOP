import constants.Constants;
import chessboard.ChessBoard;
import chessboard.Player;
import org.junit.Test;
import piece.*;

import static org.junit.Assert.*;

public class PieceTest {

    /**
     * Test player id that could only be WHITE or BLACK
     */
    @Test
    public void testGetPlayer() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Piece p1 = new King(chessBoard, Player.WHITE);
        assertEquals(Player.WHITE, p1.getPlayer());


        Piece p2 = new King(chessBoard, Player.BLACK);
        assertEquals(Player.BLACK, p2.getPlayer());
    }

    /**
     * Test if piece name is correct
     */
    @Test
    public void testGetPieceName() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Piece piece;

        piece = new King(chessBoard, Player.WHITE);
        assertEquals(Constants.KING, piece.getPieceName());


        piece = new Queen(chessBoard, Player.WHITE);
        assertEquals(Constants.QUEEN, piece.getPieceName());


        piece = new Rook(chessBoard, Player.WHITE);
        assertEquals(Constants.ROOK, piece.getPieceName());


        piece = new Bishop(chessBoard, Player.WHITE);
        assertEquals(Constants.BISHOP, piece.getPieceName());


        piece = new Knight(chessBoard, Player.WHITE);
        assertEquals(Constants.KNIGHT, piece.getPieceName());


        piece = new Pawn(chessBoard, Player.WHITE);
        assertEquals(Constants.PAWN, piece.getPieceName());
    }
    /**
     * Test if piece image path is correct
     */
    @Test
    public void testGetPieceImagePath() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Piece piece;

        piece = new King(chessBoard, Player.WHITE);
        assertEquals(Constants.KING_WHITE_PATH, piece.getPieceImagePath());


        piece = new Queen(chessBoard, Player.WHITE);
        assertEquals( Constants.QUEEN_WHITE_PATH, piece.getPieceImagePath());


        piece = new Rook(chessBoard, Player.WHITE);
        assertEquals(Constants.ROOK_WHITE_PATH, piece.getPieceImagePath());


        piece = new Bishop(chessBoard, Player.WHITE);
        assertEquals(Constants.BISHOP_WHITE_PATH, piece.getPieceImagePath());


        piece = new Knight(chessBoard, Player.WHITE);
        assertEquals(Constants.KNIGHT_WHITE_PATH, piece.getPieceImagePath());


        piece = new Pawn(chessBoard, Player.WHITE);
        assertEquals(Constants.PAWN_WHITE_PATH, piece.getPieceImagePath());


        piece = new King(chessBoard, Player.BLACK);
        assertEquals(Constants.KING_BLACK_PATH, piece.getPieceImagePath());


        piece = new Queen(chessBoard, Player.BLACK);
        assertEquals(Constants.QUEEN_BLACK_PATH, piece.getPieceImagePath());


        piece = new Rook(chessBoard, Player.BLACK);
        assertEquals(Constants.ROOK_BLACK_PATH, piece.getPieceImagePath());


        piece = new Bishop(chessBoard, Player.BLACK);
        assertEquals(Constants.BISHOP_BLACK_PATH, piece.getPieceImagePath());


        piece = new Knight(chessBoard, Player.BLACK);
        assertEquals(Constants.KNIGHT_BLACK_PATH, piece.getPieceImagePath());


        piece = new Pawn(chessBoard, Player.BLACK);
        assertEquals(Constants.PAWN_BLACK_PATH, piece.getPieceImagePath());
    }

    /**
     * Test setCoordinate on board and out board
     */
    @Test
    public void testSetCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Piece piece;
        boolean result;

        piece = new King(chessBoard, Player.WHITE);

        // invalid area
        result = piece.setCoordinate(-1, 9);
        assertEquals(false, result);


        result = piece.setCoordinate(1, 8);
        assertEquals(false, result);


        result = piece.setCoordinate(10, 10);
        assertEquals(false, result);


        result = piece.setCoordinate(-1, 0);
        assertEquals(false, result);


        result = piece.setCoordinate(9, 4);
        assertEquals(false, result);


        result = piece.setCoordinate(-1, -1);
        assertEquals(false, result);


        result = piece.setCoordinate(4, -1);
        assertEquals(false, result);


        result = piece.setCoordinate(9, -4);
        assertEquals(false, result);


        // valid area
        result = piece.setCoordinate(0, 0);
        assertEquals(true, result);


        result = piece.setCoordinate(7, 7);
        assertEquals(true, result);


        result = piece.setCoordinate(6, 3);
        assertEquals(true, result);


        // test intersection of two pieces
        result = piece.setCoordinate(2, 2);
        assertEquals(true, result);
        Piece piece2 = new King(chessBoard, Player.BLACK);
        result = piece2.setCoordinate(2, 2);
        assertEquals(false, result);
    }

    /**
     * test remove itself
     */
    @Test
    public void testRemoveSelf() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Piece piece = new King(chessBoard, Player.WHITE);
        piece.setCoordinate(0, 0);
        piece.removeSelf();

        assertEquals(Constants.OUT_OF_BOARD, piece.getXCoordinate());
        assertEquals(Constants.OUT_OF_BOARD, piece.getYCoordinate());
    }

    /**
     * Test suicide move
     */
    @Test
    public void testIsSuicideMove() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        King whiteKing = new King(chessBoard, Player.WHITE);
        Rook whiteRook = new Rook(chessBoard, Player.WHITE);
        Rook blackRook = new Rook(chessBoard, Player.BLACK);

        whiteKing.setCoordinate(4, 1);
        whiteRook.setCoordinate(4, 2);
        blackRook.setCoordinate(4, 5);

        assertEquals(true, whiteRook.isSuicideMove(3, 2));
        assertEquals(false, whiteRook.isSuicideMove(4, 3));
    }


}