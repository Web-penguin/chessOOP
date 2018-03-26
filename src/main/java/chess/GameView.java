package chess;

import chessboard.*;
import constants.Constants;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 *
 * Draw GUI for game
 * Game View is ChessboardView + MenuView
 */
public class GameView extends JPanel {
    protected ChessBoard chessBoard;
    protected GameController gameController;
    protected ChessboardView chessBoardView;
    protected MenuView menuView;

    private int boardWidth;
    private int boardHeight;

    private int menuWidth;
    private int menuHeight;

    private int viewWidth;
    private int viewHeight;

    /**
     * Constructor: init game view
     * @param chessBoard that we are using now
     */
    public GameView(ChessBoard chessBoard) {
        // set layout for game view
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        this.chessBoard = chessBoard;

        this.boardWidth = this.chessBoard.getWidth() * Constants.CELL_SIZE;
        this.boardHeight = this.chessBoard.getHeight() * Constants.CELL_SIZE;

        this.menuWidth = Constants.MENU_WIDTH;
        this.menuHeight = this.boardHeight;

        this.viewWidth = this.boardWidth + this.menuWidth;
        this.viewHeight = this.menuHeight;

        this.chessBoardView = null;

        // set game view size
        this.setPreferredSize(new Dimension(this.viewWidth, this.viewHeight));
    }

    /**
     * Bing game controller to game view
     * @param gameController that we want use.
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }


    /**
     * Redraw the whole game view
     */
    public void redraw() {
        this.chessBoardView.repaint();
        this.menuView.repaint();
    }

    /**
     * Initialize game window
     */
    public void initWindow() {
        this.chessBoardView =
                new ChessboardView(this.chessBoard, this.gameController, this.boardWidth, this.boardHeight);
        this.add(chessBoardView, BorderLayout.CENTER);
        this.menuView = new MenuView(this.menuWidth, this.menuHeight, this);
        this.add(this.menuView);

        JFrame gameFrame = new JFrame(Constants.CHESS_TITLE);
        gameFrame.getContentPane().setPreferredSize(new Dimension(this.viewWidth, this.viewHeight));
        // disable resizable
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setVisible(true);
        // set close operation
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // draw canvas
        gameFrame.add(this);
    }
}
