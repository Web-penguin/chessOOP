import constants.Constants;
import org.junit.Test;

import static org.junit.Assert.*;
import chessboard.ChessBoard;
import chessboard.Player;
import piece.*;

public class ChessBoardTest {

    /**
     * test cannot move statement
     */
    @Test
    public void testPlayerCannotMove() {
        ChessBoard board = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        King king1 = new King(board, Player.WHITE);
        king1.setCoordinate(0, 0);
        assertEquals(false, board.playerCannotMove(Player.WHITE));


        Rook blackRook1 = new Rook(board, Player.BLACK);
        Rook blackRook2 = new Rook(board, Player.BLACK);
        Queen blackQueen = new Queen(board, Player.BLACK);
        Rook whiteRook = new Rook(board, Player.WHITE);

        blackRook1.setCoordinate(0, 5);
        blackRook2.setCoordinate(4, 0);
        blackQueen.setCoordinate(2, 2);
        whiteRook.setCoordinate(4, 1);
        assertEquals(true, board.playerCannotMove(Player.WHITE));

    }

    /**
     * test stalemate
     */
    @Test
    public void testIsStalemate() {
        ChessBoard board = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        King kingWhite = new King(board, Player.WHITE);
        King kingBlack = new King(board, Player.BLACK);
        Queen queenBlack = new Queen(board, Player.BLACK);

        kingWhite.setCoordinate(7, 7);
        kingBlack.setCoordinate(5, 6);
        queenBlack.setCoordinate(6, 5);
        assertEquals(true, board.isStalemate(Player.WHITE));


        kingWhite.setCoordinate(0, 0);
        assertEquals(false, board.isStalemate(Player.WHITE));


        kingBlack.setCoordinate(0, 0);
        Queen queenWhite = new Queen(board, Player.WHITE);
        queenWhite.setCoordinate(1, 2);
        kingWhite.setCoordinate(6, 4);
        assertEquals(false, board.isStalemate(Player.BLACK));

    }
}