package chess;

public class Game {
    private GameView gameView;

    public Game(GameController gameController, GameView gameView) {
        this.gameView = gameView;
        this.gameView.setGameController(gameController);
    }

    public void startGame() {
        gameView.initWindow();
    }
}