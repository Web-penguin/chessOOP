import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import piece.Coordinate;
import org.junit.Test;
import piece.Pawn;
import piece.Piece;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PawnTest {
    /**
     *  test pawn possible moves
     */
    @Test
    public void testGetPossibleMoveCoordinate() {
        ChessBoard chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);

        Pawn pawn = new Pawn(chessBoard, Player.WHITE);
        pawn.setCoordinate(0, 0);
        List<Coordinate> coordinates = pawn.getPossibleMoveCoordinate();
        List<Coordinate> validCoordinates = Arrays.asList(new Coordinate(0, 1), new Coordinate(0, 2));
        assertEquals(2, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        pawn.setCoordinate(0, 1);
        coordinates = pawn.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(0, 2));
        assertEquals(1, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));

        // friend pawn
        Pawn friend = new Pawn(chessBoard, Player.WHITE);
        friend.setCoordinate(0, 2);
        coordinates = pawn.getPossibleMoveCoordinate();
        assertEquals(0, coordinates.size());


        // opponent pawn under attack
        Piece opponentPiece = new Pawn(chessBoard, Player.BLACK);
        opponentPiece.setCoordinate(1, 2);
        coordinates = pawn.getPossibleMoveCoordinate();
        validCoordinates = Arrays.asList(new Coordinate(1, 2));
        assertEquals(1, coordinates.size());
        assertTrue(validCoordinates.containsAll(coordinates) && coordinates.containsAll(validCoordinates));


        // top left
        pawn.setCoordinate(0, 7);
        coordinates = pawn.getPossibleMoveCoordinate();
        assertEquals(0, coordinates.size());
    }
}