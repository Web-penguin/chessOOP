package chess;

import chessboard.ChessBoard;
import chessboard.Player;
import constants.Constants;
import piece.Coordinate;
import piece.King;
import piece.Piece;
import javax.swing.*;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ArrayList;

/**
 * Game Controller
 */
public class GameController {
    private ChessBoard chessBoard;
    private GameView gameView;
    private Piece chosenPiece;
    private boolean isGameStart;
    private String playerOneName;
    private String playerTwoName;
    private String message;

    public GameController(ChessBoard chessBoard, GameView gameView) {
        this.chessBoard = chessBoard;
        this.gameView = gameView;
        this.chosenPiece = null;
        this.isGameStart = false;
        this.playerOneName = Constants.WHITE_NAME;
        this.playerTwoName = Constants.BLACK_NAME;
        this.message = Constants.WELCOME_MESSAGE;
    }

    /**
     * Is player's king in check
     * @param player player id
     * @return true if king is in check.
     */
    private boolean playersKingIsInCheck(Player player) {
        Piece king;
        if (player == Player.WHITE) {
            king = this.chessBoard.getWhiteKing();
        }
        else {
            king = this.chessBoard.getBlackKing();
        }
        return ((King)king).isInCheck();
    }

    /**
     * check is checkmate or stalemate
     * @return "checkmate" if checkmate, "stalemate" if stalemate else null
     */
    public String isCheckmateOrStalemate() {
        Player currentPlayer = this.chessBoard.getTurn() % 2 == 0 ? Player.WHITE : Player.BLACK;
        if (this.chessBoard.playerCannotMove(currentPlayer)) {
            King king = (this.chessBoard.getTurn() % 2 == 0)
                    ? (King)this.chessBoard.getWhiteKing() : (King)this.chessBoard.getBlackKing();
            if (king == null) {
                return null;
            }
            if (king.isInCheck()) {
                return Constants.CHECKMATE;
            }
            else {
                return Constants.STALEMATE;
            }
        }
        return null;
    }

    /**
     * Game is over
     */
    public void gameIsOver(String status) {
        if (!this.isGameStart) {
            return;
        }
        Player currentPlayer = this.getPlayerForThisTurn();
        this.isGameStart = false;
        if (status.equals(Constants.CHECKMATE)) {
            this.message = Constants.CHECKMATE;
        }
        else {
            this.message = Constants.STALEMATE;
        }
        StringBuilder currentMessage = new StringBuilder(this.message);
        currentMessage.append(" ");
        currentMessage.append(currentPlayer == Player.WHITE ? this.playerTwoName : playerOneName);
        currentMessage.append(Constants.WIN);
        this.message = currentMessage.toString();

        this.gameView.menuView.drawMessage(this.message);
    }

    /**
     * Return possible move coordinates for piece
     * @param piece we want to move.
     * @return coordinate list
     */
    private List<Coordinate> showPossibleMovesForPiece(Piece piece) {
        List<Coordinate> usefulCoordinates = new ArrayList<>();
        List<Coordinate> possibleMoveCoordinate = piece.getPossibleMoveCoordinate();

        for (Coordinate coord : possibleMoveCoordinate) {
            //  player cannot make suicide move
            if (this.chosenPiece.isSuicideMove(coord.getX(), coord.getY())) {
                continue;
            }

            // add this coord to usefulCoordinates
            usefulCoordinates.add(coord);
        }
        return usefulCoordinates;
    }

    /**
     * update turns and redraw the canvas
     * @param panel appropriate panel
     */
    private void makeProcessActionsAfterMove(JPanel panel) {

        this.chosenPiece = null;
        this.chessBoard.incrementTurn();

        StringBuilder currentMessage =
                new StringBuilder(this.playersKingIsInCheck(this.getPlayerForThisTurn())
                        ? Constants.CHECK : Constants.EMPTY_STRING);

        currentMessage.append((this.getPlayerForThisTurn() == Player.WHITE
                ? this.playerOneName : this.playerTwoName) + Constants.TURN);

        this.updateMessage(currentMessage.toString());
        panel.repaint();
    }

    /**
     * If player can move piece to opponent piece, redraw the chessboard if valid else do nothing
     * @param opponentPiece opponent piece
     */
    private void movePieceToOpponentPieceLocationIfValid(JPanel panel, Piece opponentPiece) {
        // check which pieces under attack
        List<Coordinate> coords = this.chosenPiece.getPossibleMoveCoordinate();
        if (coords != null) {
            for (Coordinate coord : coords) {
                // player cannot make suicide move.
                if (this.chosenPiece.isSuicideMove(coord.getX(), coord.getY())) {
                    continue;
                }
                // opponent piece here
                if (coord.getX() == opponentPiece.getXCoordinate()
                        && coord.getY() == opponentPiece.getYCoordinate()) {

                    // remove opponent piece
                    opponentPiece.removeSelf();

                    // move the chosen piece to coordinate of opponent's piece
                    this.chosenPiece.setCoordinate(coord.getX(), coord.getY());

                    makeProcessActionsAfterMove(panel);
                    return;
                }
            }
        }
    }

    /**
     * Move player's piece to empty cell if not a suicide move
     * @param x coord to move to
     * @param y coord to move to
     */
    private void movePlayerPieceToEmptyTileIfValid(JPanel panel, int x, int y) {
        List<Coordinate> coords = this.chosenPiece.getPossibleMoveCoordinate();
        for (Coordinate coord : coords) {
            // can't do suicide move
            if (this.chosenPiece.isSuicideMove(coord.getX(), coord.getY())) {
                continue;
            }
            // player can move only on possible coordinates
            if (coord.getX() == x && coord.getY() == y) {
                this.chosenPiece.setCoordinate(x, y);

                makeProcessActionsAfterMove(panel);
                return;
            }
        }
    }

    /**
     * @return Player for this turn.
     */
    private Player getPlayerForThisTurn() {
        return this.chessBoard.getPlayerForThisTurn();
    }

    /**
     * Check user mouse click, and update gui.
     */
    public void checkUserClick(Graphics2D graphics2D, double clickedXCoord, double clickedYCoord) {
        // we don't check user mouse click if not started
        if (!this.isGameStart) {
            return;
        }

        Piece piece;
        if (clickedXCoord >= 0 && clickedYCoord >= 0) {
            // convert to board cells
            int x = (int) (clickedXCoord / Constants.CELL_SIZE);
            int y = this.chessBoard.getHeight() - 1 - (int) (clickedYCoord / Constants.CELL_SIZE);
            piece = this.chessBoard.getPieceAtCoordinate(x, y);

            // if piece exist, show possible moves
            if (piece != null) {
                // if this player owner of this piece
                if (piece.getPlayer() == this.chessBoard.getPlayerForThisTurn()) {
                    this.chosenPiece = piece;
                    this.gameView.chessBoardView.drawPossibleMovesForPiece(graphics2D,
                            this.showPossibleMovesForPiece(piece));
                }
                // if clicked on opponent piece
                else {
                    // nothing if no piece in player's hand
                    if (this.chosenPiece == null) {
                        return;
                    }
                    this.movePieceToOpponentPieceLocationIfValid(this.gameView, piece);
                }
            }
            // clicked on empty slot with piece in the hand
            else if (this.chosenPiece != null) {
                this.movePlayerPieceToEmptyTileIfValid(this.gameView, x, y);
            }
        }
    }

    /**
     * Update game message
     * @param message getting message
     */
    private void updateMessage(String message) {
        // set message
        this.message = message;
        // redraw menu
        this.gameView.menuView.drawMessage(this.message);
    }

    /**
     * Start a new game
     */
    private void startNewGame() {
        ChessBoard newBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);
        this.gameView.chessBoardView.clickedXCoord = Constants.OUT_OF_BOARD;
        this.gameView.chessBoardView.clickedYCoord = Constants.OUT_OF_BOARD;

        // GameView, GameController rebind
        this.chessBoard = newBoard;
        this.gameView.chessBoard = newBoard;
        this.gameView.chessBoardView.chessBoard = newBoard;

        this.chessBoard.generateStandardBoard();
        this.gameView.redraw();

        this.isGameStart = true;
        this.message = (this.playerOneName) + Constants.TURN;
        // redraw menu for game
        this.gameView.menuView.drawMessage(this.message);
    }

    /**
     * Clicked start button
     */
    public void clickedStartButton() {
        if (this.isGameStart) {
            JOptionPane.showMessageDialog(null, Constants.GAME_ALREADY_STARTED);
            return;
        }
        this.startNewGame();
    }

    /**
     * Clicked restart button
     */
    public void clickedRestartButton() {
        if (!this.isGameStart) {
            JOptionPane.showMessageDialog(null, Constants.GAME_NOT_STARTED);
            return;
        }
        int entry = JOptionPane.showConfirmDialog(null,
                this.playerOneName + Constants.WISH, Constants.SELECT_TITLE, JOptionPane.YES_NO_OPTION);
        if (entry == JOptionPane.NO_OPTION) {
            // player doesn't agree to restart the game
            return;
        }
        entry = JOptionPane.showConfirmDialog(null,
                this.playerTwoName + Constants.WISH, Constants.SELECT_TITLE, JOptionPane.YES_NO_OPTION);
        if (entry == JOptionPane.NO_OPTION) {
            return;
        }

        this.startNewGame();
    }

    /**
     * Clicked forfeit button
     */
    public void clickedForfeitButton() {
        if (!this.isGameStart) {
            JOptionPane.showMessageDialog(null, Constants.GAME_NOT_STARTED);
            return;
        }
        Player currentPlayer = this.getPlayerForThisTurn();
        int entry = JOptionPane.showConfirmDialog(null,
                (currentPlayer == Player.WHITE ? this.playerOneName : this.playerTwoName)
                        + Constants.FORFEIT, Constants.SELECT_TITLE, JOptionPane.YES_NO_OPTION);
        // player want to forfeit
        if (entry == JOptionPane.YES_OPTION) {
            this.message = (currentPlayer == Player.WHITE ? this.playerTwoName : playerOneName)
                    + Constants.WIN;
            this.isGameStart = false;

            // redraw menu
            this.gameView.menuView.drawMessage(this.message);
        }
    }

    /**
     * Update Player Name
     * @param button with WHITE or BLACK name
     */
    public void updatePlayerName(JButton button) {
        if (this.isGameStart) {
            JOptionPane.showMessageDialog(null, Constants.CHANGE_NOT_NOW);
            return;
        }
        String newName =
                JOptionPane.showInputDialog(null, Constants.NEW_NAME_FOR + button.getText());
        if (newName != null && newName.length() > 0) {
            // update WHITE name
            if (button.getText().equals(this.playerOneName)) {
                // WHITE and BLACK can't have the same name
                if (this.playerTwoName.equals(newName)) {
                    JOptionPane.showMessageDialog(null, Constants.INVALID_NAME + newName);
                    return;
                }
                this.playerOneName = newName;
            }
            // update BLACK name
            else {
                if (this.playerOneName.equals(newName)) { // invalid name, cuz player1 and player2 will have the same name
                    JOptionPane.showMessageDialog(null, Constants.INVALID_NAME + newName);
                    return;
                }
                this.playerTwoName = newName;
            }
            // update player name button
            button.setText(newName);
        }
    }
}
