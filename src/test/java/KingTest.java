import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import piece.Coordinate;
import org.junit.Test;
import piece.King;
import piece.Pawn;
import piece.Rook;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class KingTest {
    /**
     *  test king possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        King king = new King(chessBoard, Player.WHITE);
        king.setCoordinate(3, 0);
        List<Coordinate> coordinates = king.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(2, 0),
                new Coordinate(4, 0), new Coordinate(3, 1),
                new Coordinate(2, 1), new Coordinate(4, 1));
        assertEquals(5, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put an enemy
        Pawn enemy = new Pawn(chessBoard, Player.BLACK);
        enemy.setCoordinate(4, 1);
        coordinates = king.getPossibleMoveCoordinate();
        assertEquals(5, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put own piece
        Pawn friend = new Pawn(chessBoard, Player.WHITE);
        friend.setCoordinate(2, 0);
        coordinates = king.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(
                new Coordinate(4, 0), new Coordinate(3, 1),
                new Coordinate(2, 1), new Coordinate(4, 1));
        assertEquals(4, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));
    }

    /**
     * test if king in check
     */
    @Test
    public void testIsInCheck() {
        ChessBoard board = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        King king = new King(board, Player.WHITE);
        king.setCoordinate(3, 0);
        assertEquals(false, king.isInCheck());


        Rook rook = new Rook(board, Player.BLACK);
        rook.setCoordinate(3, 6);
        assertEquals(true, king.isInCheck());
    }
}