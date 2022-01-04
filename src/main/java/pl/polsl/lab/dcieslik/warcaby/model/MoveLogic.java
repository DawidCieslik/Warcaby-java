package pl.polsl.lab.dcieslik.warcaby.model;

import java.awt.Point;
import java.util.List;

/**
 * Determinates what a valid move or a valid skip is.
 *
 * @author Dawid Cieślik
 */
public class MoveLogic {

    /**
     * Determines if the specified move or skip is valid based on the rules.
     *
     * @param game the game object.
     * @param startIndex the start index of the move.
     * @param endIndex the end index of the move.
     * @return true if the move is valid.
     */
    public static boolean isValidMove(Game game, int startIndex, int endIndex) {
        return game == null ? false : isValidMove(game.getBoard(),
                game.isP1Turn(), startIndex, endIndex, game.getSkipIndex());
    }

    /**
     * Determines if the specified move or skip is valid based on the rules.
     *
     * @param board the current board to check against.
     * @param isP1Turn the flag of a turn.
     * @param startIndex the start index of the move.
     * @param endIndex the end index of the move.
     * @param skipIndex the index of the last skip this turn.
     * @return true if the move is valid
     */
    public static boolean isValidMove(Board board, boolean isP1Turn, int startIndex, int endIndex, int skipIndex) {
        if (board == null || !Board.isValidIndex(startIndex) || !Board.isValidIndex(endIndex)) {
            return false;
        } else if (startIndex == endIndex) {
            return false;
        } else if (Board.isValidIndex(skipIndex) && skipIndex != startIndex) {
            return false;
        }

        if (!validateIDs(board, isP1Turn, startIndex, endIndex)) {
            return false;
        } else if (!validateDistance(board, isP1Turn, startIndex, endIndex)) {
            return false;
        }

        return true;
    }

    /**
     * Validates all ID related values for the start and end index (and the
     * middle index if the move is a skip).
     *
     * @param board the game board.
     * @param isP1Turn the flag of a turn.
     * @param startIndex the start index of the move.
     * @param endIndex	the end index of the move.
     * @return true if and only if all IDs are valid.
     */
    private static boolean validateIDs(Board board, boolean isP1Turn, int startIndex, int endIndex) {
        if (board.get(endIndex) != Board.IDs.EMPTY.getID()) {
            return false;
        }

        int id = board.get(startIndex);
        if ((isP1Turn && id != Board.IDs.WHITE_CHECKER.getID() && id != Board.IDs.WHITE_KING.getID())
                || (!isP1Turn && id != Board.IDs.BLACK_CHECKER.getID() && id != Board.IDs.BLACK_KING.getID())) {
            return false;
        }

        Point middle = Board.middle(startIndex, endIndex);
        int midID = board.get(Board.toIndex(middle));
        return !(midID != Board.IDs.INVALID.getID()
                && ((!isP1Turn && midID != Board.IDs.WHITE_CHECKER.getID() && midID != Board.IDs.WHITE_KING.getID())
                || (isP1Turn && midID != Board.IDs.BLACK_CHECKER.getID() && midID != Board.IDs.BLACK_KING.getID())));
    }

    /**
     * Checks that the move distance is correct.
     *
     * @param board the game board.
     * @param isP1Turn the flag of a turn.
     * @param startIndex the start index of the move.
     * @param endIndex the end index of the move.
     * @return true if the move distance is valid.
     */
    private static boolean validateDistance(Board board, boolean isP1Turn, int startIndex, int endIndex) {
        Point start = Board.toPoint(startIndex);
        Point end = Board.toPoint(endIndex);
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) > 2 || dx == 0) {
            return false;
        }

        int id = board.get(startIndex);
        if ((id == Board.IDs.WHITE_CHECKER.getID() && dy > 0) || (id == Board.IDs.BLACK_CHECKER.getID() && dy < 0)) {
            return false;
        }

        Point middle = Board.middle(startIndex, endIndex);
        int midID = board.get(Board.toIndex(middle));
        if (midID < 0) {
            List<Point> checkers;
            if (isP1Turn) {
                checkers = board.find(Board.IDs.WHITE_CHECKER.getID());
                checkers.addAll(board.find(Board.IDs.WHITE_KING.getID()));
            } else {
                checkers = board.find(Board.IDs.BLACK_CHECKER.getID());
                checkers.addAll(board.find(Board.IDs.BLACK_KING.getID()));
            }

            for (Point p : checkers) {
                int index = Board.toIndex(p);
                if (!MoveGenerator.getSkips(board, index).isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}
