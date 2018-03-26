package chess;

import chessboard.ChessBoard;
import constants.Constants;

public class Main {

    public static void main(String [] args) {
        ChessBoard chessBoard;
        GameView gameView;
        GameController gameController;
        chessBoard = new ChessBoard(Constants.BOARD_SIZE_IN_CELLS, Constants.BOARD_SIZE_IN_CELLS);
        gameView = new GameView(chessBoard);
        gameController = new GameController(chessBoard, gameView);

        // init game
        Game game = new Game(gameController, gameView);

        // start game
        game.startGame();
    }
}
