import constants.Constants;
import piece.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;
import chessboard.ChessBoard;
import chessboard.Player;
import piece.Pawn;
import piece.Piece;
import piece.Queen;

import java.util.Arrays;
import java.util.List;

public class QueenTest {
    /**
     *  test queen possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Queen queen = new Queen(chessBoard, Player.WHITE);
        queen.setCoordinate(3, 0);
        List<Coordinate> coordinates = queen.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(1, 0),
                new Coordinate(2, 0), new Coordinate(4, 0),
                new Coordinate(5, 0), new Coordinate(3, 1),
                new Coordinate(6, 0), new Coordinate(7, 0),
                new Coordinate(3, 2), new Coordinate(3, 3),
                new Coordinate(3, 4), new Coordinate(3, 5),
                new Coordinate(3, 6), new Coordinate(3, 7),
                new Coordinate(2, 1), new Coordinate(1, 2),
                new Coordinate(0, 3), new Coordinate(4, 1),
                new Coordinate(5, 2), new Coordinate(6, 3),
                new Coordinate(7, 4), new Coordinate(0, 0));
        assertEquals(21, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put own pawn
        Piece friend = new Pawn(chessBoard, Player.WHITE);
        friend.setCoordinate(4, 0);
        coordinates = queen.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(1, 0),
                new Coordinate(2, 0), new Coordinate(3, 1),
                new Coordinate(3, 2), new Coordinate(3, 3),
                new Coordinate(3, 4), new Coordinate(3, 5),
                new Coordinate(3, 6), new Coordinate(3, 7),
                new Coordinate(2, 1), new Coordinate(1, 2),
                new Coordinate(0, 3), new Coordinate(4, 1),
                new Coordinate(5, 2), new Coordinate(6, 3),
                new Coordinate(7, 4), new Coordinate(0, 0));
        assertEquals(17, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));
    }
}