package pl.polsl.lab.dcieslik.warcaby.view;

import pl.polsl.lab.dcieslik.warcaby.controller.*;
import pl.polsl.lab.dcieslik.warcaby.model.*;
import javax.swing.*;
import java.awt.*;

/**
 * Implements a graphical user interface that is capable of drawing any checkers
 * game state. It also handles in-game actions such as click on a black tile or
 * move a checker.
 *
 * @author Dawid Cieślik
 */
public class BoardWindow extends JButton {

    /**
     * The number of pixels of padding between the window border and the board.
     */
    private static final int PADDING = 32;

    /**
     * The game of checkers that is being played.
     */
    private Game game;

    /**
     * Click handler to move the checkers.
     */
    private final Listener handler;

    /**
     * The colour of the light tiles.
     */
    private final Color lightTile;

    /**
     * The colour of the dark tiles.
     */
    private final Color darkTile;

    /**
     * Players playing the game.
     */
    private final Player player1, player2;

    /**
     * Class constructor.
     *
     * @param window window of the application.
     * @param player1 white player.
     * @param player2 black player.
     */
    public BoardWindow(GameWindow window, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        super.setBorderPainted(false);
        super.setFocusPainted(false);
        super.setContentAreaFilled(false);
        this.game = (game == null) ? new Game(this) : game;
        this.handler = new Listener(this, game);
        this.lightTile = new Color(238, 232, 170);
        this.darkTile = new Color(92, 17, 9);
    }

    /**
     * Redraws the component graphics.
     */
    public void update() {
        repaint();
    }

    /**
     * Returns a Game object.
     *
     * @return Game object.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns padding value.
     *
     * @return padding value.
     */
    public int getPadding() {
        return PADDING;
    }

    /**
     * Draws the current checkers game state.
     *
     * @param g A Graphics object for the entire graphics context.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int BOX_PADDING = 4;
        final int W = getWidth(), H = getHeight();
        final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * PADDING) / 8;
        final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
        final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
        final int CHECKER_SIZE = Math.max(0, BOX_SIZE - 2 * BOX_PADDING);

        g.setColor(Color.BLACK);
        g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, BOX_SIZE * 8 + 1, BOX_SIZE * 8 + 1);
        g.setColor(lightTile);
        g.fillRect(OFFSET_X, OFFSET_Y, BOX_SIZE * 8, BOX_SIZE * 8);
        g.setColor(darkTile);
        for (int y = 0; y < 8; y++) {
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                g.fillRect(OFFSET_X + x * BOX_SIZE, OFFSET_Y + y * BOX_SIZE,
                        BOX_SIZE, BOX_SIZE);
            }
        }

        if (Board.isValidPoint(game.getSelected())) {
            g.setColor(game.getSelectionValid() ? Color.GREEN.darker() : Color.RED.darker());
            g.fillRect(OFFSET_X + game.getSelected().x * BOX_SIZE + BOX_SIZE / 38,
                    OFFSET_Y + game.getSelected().y * BOX_SIZE + BOX_SIZE / 38,
                    BOX_SIZE - BOX_SIZE / 20, BOX_SIZE - BOX_SIZE / 20);

            java.util.List<Point> possibleMoves = MoveGenerator.getMoves(game.getBoard(), game.getSelected());
            java.util.List<Point> possibleSkips = MoveGenerator.getSkips(game.getBoard(), game.getSelected());

            for (int i = 0; i < possibleMoves.size(); i++) {
                g.setColor((possibleSkips.isEmpty() && game.getSelectionValid()) ? Color.GREEN.darker() : Color.RED.darker());
                g.fillRect(OFFSET_X + possibleMoves.get(i).x * BOX_SIZE + BOX_SIZE / 38,
                        OFFSET_Y + possibleMoves.get(i).y * BOX_SIZE + BOX_SIZE / 38,
                        BOX_SIZE - BOX_SIZE / 20, BOX_SIZE - BOX_SIZE / 20);
            }

            for (int i = 0; i < possibleSkips.size(); i++) {
                g.setColor(game.getSelectionValid() ? Color.GREEN.darker() : Color.RED.darker());
                g.fillRect(OFFSET_X + possibleSkips.get(i).x * BOX_SIZE + BOX_SIZE / 38,
                        OFFSET_Y + possibleSkips.get(i).y * BOX_SIZE + BOX_SIZE / 38,
                        BOX_SIZE - BOX_SIZE / 20, BOX_SIZE - BOX_SIZE / 20);
            }
        }

        Board b = game.getBoard();
        for (int y = 0; y < 8; y++) {
            int cy = OFFSET_Y + y * BOX_SIZE + BOX_PADDING;
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                int id = b.get(x, y);

                if (id == Board.IDs.EMPTY.getID()) {
                    continue;
                }

                int cx = OFFSET_X + x * BOX_SIZE + BOX_PADDING;

                if (id == Board.IDs.BLACK_CHECKER.getID() || id == Board.IDs.BLACK_KING.getID()) {
                    g.setColor(Color.BLACK);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                } else if (id == Board.IDs.WHITE_CHECKER.getID() || id == Board.IDs.WHITE_KING.getID()) {
                    g.setColor(Color.WHITE);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                }

                if (id == Board.IDs.BLACK_KING.getID() || id == Board.IDs.WHITE_KING.getID()) {
                    g.setColor(new Color(255, 225, 77));
                    g.drawOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.drawOval(cx - 1, cy - 1, CHECKER_SIZE, CHECKER_SIZE);
                    g.drawOval(cx - 1, cy + 1, CHECKER_SIZE, CHECKER_SIZE);
                    g.drawOval(cx + 1, cy + 1, CHECKER_SIZE, CHECKER_SIZE);
                    g.drawOval(cx + 1, cy - 1, CHECKER_SIZE, CHECKER_SIZE);
                }
            }
        }

        String msg = game.isP1Turn() ? ("Tura Gracza 1 (" + player1.getName() + ")")
                : ("Tura Gracza 2 (" + player2.getName() + ")");
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int width = g.getFontMetrics().stringWidth(msg);
        Color back = game.isP1Turn() ? Color.WHITE : Color.BLACK;
        Color front = game.isP1Turn() ? Color.BLACK : Color.WHITE;
        g.setColor(back);
        g.fillRect(W / 2 - width / 2 - 5, OFFSET_Y + 8 * BOX_SIZE + 2,
                width + 10, 27);
        g.setColor(front);
        g.drawString(msg, W / 2 - width / 2, OFFSET_Y + 8 * BOX_SIZE + 2 + 20);

        if (game.isGameOver()) {
            g.setFont(new Font("Arial", Font.BOLD, 20));
            msg = "Koniec gry!";
            width = g.getFontMetrics().stringWidth(msg);
            g.setColor(new Color(240, 240, 255));
            g.fillRoundRect(W / 2 - width / 2 - 5,
                    OFFSET_Y + BOX_SIZE * 4 - 16,
                    width + 10, 30, 10, 10);
            g.setColor(Color.RED);
            g.drawString(msg, W / 2 - width / 2, OFFSET_Y + BOX_SIZE * 4 + 7);
        }
    }

    /**
     * Sets a new game state.
     *
     * @param newState new game state to be set.
     * @param expected expected game state.
     * @return true if set a new game state, false if current game state was not
     * as expected.
     */
    public boolean setGameState(String newState, String expected) {
        if (!game.getGameState().equals(expected)) {
            return false;
        }

        this.game.setGameState(newState);
        update();

        return true;
    }
}
