import constants.Constants;
import piece.Bishop;
import chessboard.ChessBoard;
import chessboard.Player;
import piece.Coordinate;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;


public class BishopTest {
    /**
     * test bishop possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        // put bishop at left bottom corner
        Bishop bishop = new Bishop(chessBoard, Player.WHITE);
        bishop.setCoordinate(0, 0);
        List<Coordinate> coordinates = bishop.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(1, 1),
                new Coordinate(2, 2), new Coordinate(3, 3),
                new Coordinate(5, 5), new Coordinate(4, 4),
                new Coordinate(6, 6), new Coordinate(7, 7));
        assertEquals(7, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // put opponent bishop at one possible move coordinate
        Bishop opponentPiece = new Bishop(chessBoard, Player.BLACK);
        opponentPiece.setCoordinate(5, 5);
        coordinates = bishop.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(1, 1),
                new Coordinate(2, 2), new Coordinate(3, 3),
                new Coordinate(4, 4), new Coordinate(5, 5));
        assertEquals(5, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));

    }
}