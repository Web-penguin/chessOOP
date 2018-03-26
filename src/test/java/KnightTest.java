import constants.Constants;
import piece.Coordinate;
import org.junit.Test;

import static org.junit.Assert.*;
import chessboard.ChessBoard;
import chessboard.Player;
import piece.Knight;
import piece.Pawn;
import piece.Piece;

import java.util.Arrays;
import java.util.List;

public class KnightTest {
    /**
     *  test knight possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Knight knight = new Knight(chessBoard, Player.WHITE);

        knight.setCoordinate(3, 3);
        List<Coordinate> coordinates = knight.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(5, 4),
                new Coordinate(4, 5), new Coordinate(1, 2),
                new Coordinate(2, 1), new Coordinate(5, 2),
                new Coordinate(2, 5), new Coordinate(1, 4),
                new Coordinate(4, 1));
        assertEquals(8, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        knight.setCoordinate(0, 3);
        coordinates = knight.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(2, 4),
                new Coordinate(1, 5), new Coordinate(1, 1),
                new Coordinate(2, 2));
        assertEquals(4, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put enemy at one possible move coordinate
        Piece enemy = new Pawn(chessBoard, Player.BLACK);
        enemy.setCoordinate(1, 5);
        coordinates = knight.getPossibleMoveCoordinate();
        assertEquals(4, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));
        enemy.removeSelf();


        // put own piece at one possible move coordinate
        Piece friend = new Pawn(chessBoard, Player.WHITE);
        friend.setCoordinate(1, 5);
        coordinates = knight.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(2, 4),
                new Coordinate(1, 1), new Coordinate(2, 2));
        assertEquals(3, knight.getPossibleMoveCoordinate().size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));
    }
}