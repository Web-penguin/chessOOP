import constants.Constants;
import piece.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;

import chessboard.ChessBoard;
import chessboard.Player;
import piece.Pawn;
import piece.Piece;
import piece.Rook;

import java.util.Arrays;
import java.util.List;

public class RookTest {
    /**
     *  test rook possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Rook rook = new Rook(chessBoard, Player.WHITE);
        rook.setCoordinate(0, 0);
        List<Coordinate> coordinates = rook.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(1, 0),
                new Coordinate(2, 0), new Coordinate(3, 0),
                new Coordinate(4, 0), new Coordinate(5, 0),
                new Coordinate(6, 0), new Coordinate(7, 0),
                new Coordinate(0, 1), new Coordinate(0, 2),
                new Coordinate(0, 3), new Coordinate(0, 4),
                new Coordinate(0, 5), new Coordinate(0, 6),
                new Coordinate(0, 7));
        assertEquals(14, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put enemy
        Piece enemy = new Pawn(chessBoard, Player.BLACK);
        enemy.setCoordinate(1, 0);
        coordinates = rook.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(1, 0),
                new Coordinate(0, 1), new Coordinate(0, 2),
                new Coordinate(0, 3), new Coordinate(0, 4),
                new Coordinate(0, 5), new Coordinate(0, 6),
                new Coordinate(0, 7));
        assertEquals(8, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put an friend on left
        Piece friend = new Pawn(chessBoard, Player.WHITE);
        friend.setCoordinate(0, 1);
        coordinates = rook.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(1, 0));
        assertEquals(1, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));
    }
}