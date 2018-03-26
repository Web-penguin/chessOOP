package chess;

import chessboard.ChessBoard;
import constants.Constants;
import piece.Coordinate;
import piece.Piece;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Chessboard View class to draw chessboard
 */
public class ChessboardView extends JPanel {
    protected ChessBoard chessBoard;
    private int cellSize;
    private GameController gameController;

    // for coordinates that mouse click
    protected double clickedXCoord = Constants.OUT_OF_BOARD;
    protected double clickedYCoord = Constants.OUT_OF_BOARD;

    /**
     * Constructor: chessBoardView Initialize
     * @param chessBoard appropriate chessBoard
     * @param gameController appropriate gameController
     * @param boardWidth pixel width
     * @param boardHeight pixel height
     */
    public ChessboardView(ChessBoard chessBoard, GameController gameController,
                          int boardWidth, int boardHeight) {
        this.chessBoard = chessBoard;
        this.gameController = gameController;
        this.cellSize = Constants.CELL_SIZE;
        this.setPreferredSize(new Dimension(boardWidth, boardHeight));

        // Mouse press event
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);

                // save mouse click (x, y) coordinate
                clickedXCoord = mouseEvent.getPoint().getX();
                clickedYCoord = mouseEvent.getPoint().getY();

                repaint();
            }
        });
    }

    /**
     * Draw tile on chessBoard.
     * @param x coordinate
     * @param y =coordinate
     * @param color to draw
     */
    private void drawTileForBoard(Graphics2D graphics2D, int x, int y, Color color) {
        graphics2D.setColor(color);
        graphics2D.fillRect(x, y, this.cellSize, this.cellSize);
    }

    /**
     * Draw Piece on Chessboard
     * @param graphics2D appropriate graphics
     * @param piece we want to draw
     */
    private void drawPiece(Graphics2D graphics2D, Piece piece) {
        int pieceXCoord = piece.getXCoordinate();
        int pieceYCoord = piece.getYCoordinate();
        // convert to pixels
        int x = pieceXCoord * this.cellSize;
        int y = (this.chessBoard.getHeight() - pieceYCoord - 1) * this.cellSize;
        try {
            BufferedImage image = ImageIO.read(new File(piece.getPieceImagePath()));
            graphics2D.drawImage(image, x, y, this.cellSize, this.cellSize, null);
        } catch (Exception e) {
            System.out.println("ERROR: Cannot load image file");
            System.exit(Constants.NULL_CODE);
        }
    }

    /**
     * Draw tiles that piece can move to
     * @param graphics2D appropriate graphics
     * @param coords list of given coordinates
     */
    public void drawPossibleMovesForPiece(Graphics2D graphics2D, List<Coordinate> coords) {
        Piece piece;
        Color color;
        int x, y;
        for (Coordinate coord : coords) {
            // empty possible move
            if (this.chessBoard.getPieceAtCoordinate(coord.getX(), coord.getY()) == null) {
                color = Constants.EMPTY_SLOT_COLOR;
            }
            // move on tile with opponent piece
            else {
                color = Constants.OPPONENT_SLOT_COLOR;
            }
            // convert to pixels
            x = coord.getX() * this.cellSize;
            y = (this.chessBoard.getHeight() - 1 - coord.getY()) * this.cellSize;

            drawTileForBoard(graphics2D, x, y, color);

            piece = this.chessBoard.getPieceAtCoordinate(coord.getX(), coord.getY());
            if (piece != null)
                drawPiece(graphics2D, piece);
        }
    }
    /**
     * Draw current chessboard by tiles
     * @param clickedXCoord x coordinate that clicked
     * @param clickedYCoord y coordinate that clicked
     */
    private void drawBoard(Graphics2D graphics2D, double clickedXCoord, double clickedYCoord) {

        // counter for cell type identification
        int count = 0;
        // top-left x, y for tile
        int x, y;
        Piece piece;
        Color color;

        // draw cells
        for (int i = 0; i < this.chessBoard.getHeight(); i++) {
            for (int j = 0; j < this.chessBoard.getWidth(); j++) {
                // convert to pixels
                x = j * this.cellSize;
                y = i * this.cellSize;

                if (count % 2 == 0) {
                    color = Constants.WHITE_CELL_COLOR;
                }
                else {
                    color = Constants.BLACK_CELL_COLOR;
                }

                if (clickedXCoord >= x  && clickedXCoord < x + this.cellSize
                        && clickedYCoord >= y && clickedYCoord < y + this.cellSize) {
                    color = Constants.CHOSEN_CELL_COLOR;
                }
                drawTileForBoard(graphics2D, x, y, color);
                count++;
            }
            count++;
        }

        // draw pieces
        for (int i = 0; i < this.chessBoard.getHeight(); i++) {
            for (int j = 0; j < this.chessBoard.getWidth(); j++) {
                piece = this.chessBoard.getPieceAtCoordinate(j, i);
                if (piece != null) {
                    drawPiece(graphics2D, piece);
                }
            }
        }
    }

    /**
     * Draw images on canvas
     * @param graphics appropriate graphics
     */
    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        // draw empty chessBoard
        this.drawBoard(graphics2D, clickedXCoord, clickedYCoord);

        String checkmateOrStalemate = gameController.isCheckmateOrStalemate();
        if (checkmateOrStalemate == null) {
            gameController.checkUserClick(graphics2D, clickedXCoord, clickedYCoord);
        }
        else if (checkmateOrStalemate.equals(Constants.CHECKMATE)) {
            gameController.gameIsOver(checkmateOrStalemate);
        }
        else {
            gameController.gameIsOver(checkmateOrStalemate);
        }
    }
}
