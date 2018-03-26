package chess;

import constants.Constants;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Class need to draw menu
 */
public class MenuView extends JPanel {
    private boolean isWelcomeMessageDisplayed = false;
    private String message;
    private GameView gameView;
    private JButton startButton;
    private JButton restartButton;
    private JButton forfeitButton;
    private JButton playerOneName;
    private JButton playerTwoName;

    /**
     * Constructor: put components on panel
     * @param width menuWidth
     * @param height menuHeight
     */
    public MenuView(int width, int height, final GameView gameView) {
        this.gameView = gameView;
        this.setPreferredSize(new Dimension(width, height));
        // absolute layout
        this.setLayout(null);

        startButton = new JButton(Constants.START_BUTTON);
        startButton.setBounds(Constants.START_BUTTON_X,
                Constants.BUTTONS_Y_COORDINATE, Constants.BUTTONS_WIDTH, Constants.BUTTONS_HEIGHT);
        this.add(startButton);
        startButton.addActionListener(event -> {
            // run click start button event
            gameView.gameController.clickedStartButton();
        });

        restartButton = new JButton(Constants.RESTART);
        restartButton.setBounds(Constants.RESTART_BUTTON_X,
                Constants.BUTTONS_Y_COORDINATE, Constants.BUTTONS_WIDTH, Constants.BUTTONS_HEIGHT);
        this.add(restartButton);
        restartButton.addActionListener(event -> {
            // run click restart button event
            gameView.gameController.clickedRestartButton();
        });

        forfeitButton = new JButton(Constants.FORFEIT_BUTTON_NAME);
        forfeitButton.setBounds(Constants.FORFEIT_BUTTON_X,
                Constants.BUTTONS_Y_COORDINATE, Constants.BUTTONS_WIDTH, Constants.BUTTONS_HEIGHT);
        this.add(forfeitButton);
        forfeitButton.addActionListener(event -> {
            // run click forfeit button event
            gameView.gameController.clickedForfeitButton();
        });

        playerOneName = new JButton(Constants.WHITE_NAME);
        playerOneName.setForeground(Constants.WHITE_COLOR);
        playerOneName.setFont(new Font(Constants.FONT_NAME, Font.BOLD, Constants.FONT_SIZE));
        playerOneName.setHorizontalAlignment(SwingConstants.LEFT);
        playerOneName.setBounds(Constants.PLAYER_ONE_NAME_X, Constants.PLAYERS_NAMES_Y,
                Constants.PLAYERS_NAMES_WIDTH, Constants.PLAYERS_NAMES_HEIGHT);
        playerOneName.setBorderPainted(false);
        playerOneName.setFocusPainted(false);
        playerOneName.setContentAreaFilled(false);
        this.add(playerOneName);
        playerOneName.addActionListener(event -> gameView.gameController.updatePlayerName(playerOneName));

        playerTwoName = new JButton(Constants.BLACK_NAME);
        playerTwoName.setForeground(Constants.BLACK_COLOR);
        playerTwoName.setFont(new Font(Constants.FONT_NAME, Font.BOLD, Constants.FONT_SIZE));
        playerTwoName.setHorizontalAlignment(SwingConstants.LEFT);
        playerTwoName.setBounds(Constants.PLAYER_TWO_NAME_X, Constants.PLAYERS_NAMES_Y,
                Constants.PLAYERS_NAMES_WIDTH, Constants.PLAYERS_NAMES_HEIGHT);
        playerTwoName.setBorderPainted(false);
        playerTwoName.setFocusPainted(false);
        playerTwoName.setContentAreaFilled(false);
        this.add(playerTwoName);
        playerTwoName.addActionListener(event -> gameView.gameController.updatePlayerName(playerTwoName));

        this.setBackground(Constants.BACKGROUND_COLOR);

        this.message = Constants.WELCOME_MESSAGE;
    }

    /**
     * Draw menu
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setFont(new Font(Constants.FONT_NAME, Font.BOLD, Constants.FONT_SIZE));

        // draw "VS"
        graphics.setColor(Constants.VS_COLOR);
        graphics.drawString(Constants.VERSUS_STRING,
                Constants.VS_COORDINATES.getKey(), Constants.VS_COORDINATES.getValue());

        // draw game message
        graphics.setColor(Constants.MESSAGE_COLOR);
        graphics.drawString(this.message,
                Constants.MESSAGE_COORDINATES.getKey(), Constants.MESSAGE_COORDINATES.getValue());
        if (!isWelcomeMessageDisplayed) {
            graphics.drawString(Constants.ADDITIONAL_WELCOME_MESSAGE,
                    Constants.ADDITIONAL_MESSAGE.getKey(), Constants.ADDITIONAL_MESSAGE.getValue());
            isWelcomeMessageDisplayed = true;
        }
    }

    /**
     * Update message, repaint the menu canvas.
     * @param message new message
     */
    public void drawMessage(String message) {
        this.message = message;
        this.repaint();
    }
}