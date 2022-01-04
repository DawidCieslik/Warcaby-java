package pl.polsl.lab.dcieslik.warcaby.model;

import pl.polsl.lab.dcieslik.warcaby.view.BoardWindow;
import java.awt.*;
import java.util.List;

/**
 * Represents a game of Checkers and ensures that all moves made are valid based
 * on the rules.
 *
 * @author Dawid Cieślik
 */
public class Game {

    /**
     * The checker board component playing the updatable game.
     */
    private final BoardWindow boardWindow;

    /**
     * The current state of the checker board.
     */
    private Board board;

    /**
     * The flag indicating if it is player 1's turn.
     */
    private boolean isPlayer1Turn;

    /**
     * The index of the last skip, to allow for multiple skips in a turn.
     */
    private int skipIndex;

    /**
     * The last selected point on the board.
     */
    private Point selected;

    /**
     * The flag to determine the player can move a selected checker.
     */
    private boolean selectionValid;

    /**
     * Class constructor.
     *
     * @param window The checkers board window.
     */
    public Game(BoardWindow window) {
        this.boardWindow = window;
        restart();
    }

    /**
     * Resets the game of checkers to the initial state.
     */
    public void restart() {
        this.board = new Board();
        this.isPlayer1Turn = true;
        this.skipIndex = -1;
    }

    /**
     * Gets a copy of the current board state.
     *
     * @return a non-reference to the current game board state.
     */
    public Board getBoard() {
        return board.copy();
    }

    /**
     * Gets the current board state.
     *
     * @return a reference to the current game board state.
     */
    public Board getExactBoard() {
        return board;
    }

    /**
     * Gets the current board window.
     *
     * @return a reference to the current game board window.
     */
    public BoardWindow getBoardWindow() {
        return boardWindow;
    }

    /**
     * Checks if it is white player turn.
     *
     * @return true if it is white player turn, false otherwise.
     */
    public boolean isP1Turn() {
        return isPlayer1Turn;
    }

    /**
     * Gets last skip index.
     *
     * @return last skip index.
     */
    public int getSkipIndex() {
        return skipIndex;
    }

    /**
     * Creates a copy of this game.
     *
     * @return an exact copy of this game.
     */
    public Game copy() {
        Game g = new Game(boardWindow);
        g.board = board.copy();
        g.isPlayer1Turn = isPlayer1Turn;
        g.skipIndex = skipIndex;
        return g;
    }

    /**
     * Returns selected point.
     *
     * @return selected point.
     */
    public Point getSelected() {
        return selected;
    }

    /**
     * Returns selectionValid flag.
     *
     * @return selectionValid flag.
     */
    public boolean getSelectionValid() {
        return selectionValid;
    }

    /**
     * Handles a click at the specified point.
     *
     * @param x	the x-coordinate of the click.
     * @param y	the y-coordinate of the click.
     */
    public void handleClick(int x, int y) {
        Game copy = this.copy();
        final int W = boardWindow.getWidth(), H = boardWindow.getHeight();
        final int DIM = W < H ? W : H, BOX_SIZE = (DIM - 2 * boardWindow.getPadding()) / 8;
        final int OFFSET_X = (W - BOX_SIZE * 8) / 2;
        final int OFFSET_Y = (H - BOX_SIZE * 8) / 2;
        x = (x - OFFSET_X) / BOX_SIZE;
        y = (y - OFFSET_Y) / BOX_SIZE;
        Point sel = new Point(x, y);

        if (Board.isValidPoint(sel) && Board.isValidPoint(selected)) {
            boolean change = copy.isP1Turn();
            String expected = copy.getGameState();
            boolean move = copy.move(selected, sel);
            if (move) {
                boardWindow.setGameState(copy.getGameState(), expected);
            }
            change = (copy.isP1Turn() != change);
            selected = change ? null : sel;
        } else {
            selected = sel;
        }

        selectionValid = copy.getBoard().isValidSelection(copy.isP1Turn(), selected);

        boardWindow.update();
    }

    /**
     * Makes a move from the start point to the end point.
     *
     * @param start	the start point.
     * @param end	the end point.
     * @return true if an update was made to the game state.
     */
    public boolean move(Point start, Point end) {
        if (start == null || end == null) {
            return false;
        }
        return move(Board.toIndex(start), Board.toIndex(end));
    }

    /**
     * Make a move given the start and end index of the move.
     *
     * @param startIndex the start index.
     * @param endIndex the end index.
     * @return true if an update was made to the game state.
     */
    public boolean move(int startIndex, int endIndex) {

        if (!MoveLogic.isValidMove(this, startIndex, endIndex)) {
            return false;
        }

        Point middle = Board.middle(startIndex, endIndex);
        int midIndex = Board.toIndex(middle);
        this.board.set(endIndex, board.get(startIndex));
        this.board.set(midIndex, Board.IDs.EMPTY.getID());
        this.board.set(startIndex, Board.IDs.EMPTY.getID());

        Point end = Board.toPoint(endIndex);
        int id = board.get(endIndex);
        boolean switchTurn = false;
        if (end.y == 0 && id == Board.IDs.WHITE_CHECKER.getID()) {
            this.board.set(endIndex, Board.IDs.WHITE_KING.getID());
        } else if (end.y == 7 && id == Board.IDs.BLACK_CHECKER.getID()) {
            this.board.set(endIndex, Board.IDs.BLACK_KING.getID());
        }

        boolean midValid = Board.isValidIndex(midIndex);

        if (midValid) {
            this.skipIndex = endIndex;
        }

        if (!midValid || MoveGenerator.getSkips(board.copy(), endIndex).isEmpty()) {
            switchTurn = true;
        }

        if (switchTurn) {
            this.isPlayer1Turn = !isPlayer1Turn;
            this.skipIndex = -1;
        }

        return true;
    }

    /**
     * Determines if the game is over.
     *
     * @return true if the game is over.
     */
    public boolean isGameOver() {
        List<Point> black = board.find(Board.IDs.BLACK_CHECKER.getID());
        black.addAll(board.find(Board.IDs.BLACK_KING.getID()));
        if (black.isEmpty()) {
            return true;
        }

        List<Point> white = board.find(Board.IDs.WHITE_CHECKER.getID());
        white.addAll(board.find(Board.IDs.WHITE_KING.getID()));
        if (white.isEmpty()) {
            return true;
        }

        List<Point> test = isPlayer1Turn ? white : black;
        return test.stream()
                .map(p -> Board.toIndex(p))
                .noneMatch(i -> (!MoveGenerator.getMoves(board, i).isEmpty()
                || !MoveGenerator.getSkips(board, i).isEmpty()));
    }

    /**
     * Gets the current game state as a string of data.
     *
     * @return a string representing the current game state.
     */
    public String getGameState() {

        String state = "";
        for (int i = 0; i < 32; i++) {
            state += "" + board.get(i);
        }

        state += (isPlayer1Turn ? "1" : "0");
        state += skipIndex;

        return state;
    }

    /**
     * Parses a string representing a game state.
     *
     * @param state the game state as a string of data.
     */
    public void setGameState(String state) {
        restart();

        if (state == null || state.isEmpty()) {
            return;
        }

        int n = state.length();
        for (int i = 0; i < 32 && i < n; i++) {
            try {
                int id = Integer.parseInt("" + state.charAt(i));
                this.board.set(i, id);
            } catch (NumberFormatException e) {
            }
        }

        if (n > 32) {
            this.isPlayer1Turn = (state.charAt(32) == '1');
        }
        if (n > 33) {
            try {
                this.skipIndex = Integer.parseInt(state.substring(33));
            } catch (NumberFormatException e) {
                this.skipIndex = -1;
            }
        }
    }
}
