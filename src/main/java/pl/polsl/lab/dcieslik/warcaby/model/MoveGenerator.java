package pl.polsl.lab.dcieslik.warcaby.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for determining if a given checker can make any move or
 * skip.
 *
 * @author Dawid Cieślik
 */
public class MoveGenerator {

    /**
     * Gets a list of move end-points for a given starting point.
     *
     * @param board the game board.
     * @param start the point to look for moves around.
     * @return the list of points representing available moves.
     */
    public static List<Point> getMoves(Board board, Point start) {
        return getMoves(board, Board.toIndex(start));
    }

    /**
     * Gets a list of move end-points for a given start index.
     *
     * @param board the game board.
     * @param startIndex the index to look for moves around.
     * @return the list of points representing available moves.
     */
    public static List<Point> getMoves(Board board, int startIndex) {
        List<Point> endPoints = new ArrayList<>();
        if (board == null || !Board.isValidIndex(startIndex)) {
            return endPoints;
        }

        int id = board.get(startIndex);
        Point p = Board.toPoint(startIndex);
        addPoints(endPoints, p, id, 1);

        for (int i = 0; i < endPoints.size(); i++) {
            Point end = endPoints.get(i);
            if (board.get(end.x, end.y) != Board.IDs.EMPTY.getID()) {
                endPoints.remove(i--);
            }
        }

        return endPoints;
    }

    /**
     * Gets a list of skip end-points for a given starting point.
     *
     * @param board the game board.
     * @param start the point to look for skips around.
     * @return the list of points representing available skips.
     */
    public static List<Point> getSkips(Board board, Point start) {
        return getSkips(board, Board.toIndex(start));
    }

    /**
     * Gets a list of skip end-points for a given start index.
     *
     * @param board the game board.
     * @param startIndex the index to look for skips around.
     * @return the list of points representing available skips.
     */
    public static List<Point> getSkips(Board board, int startIndex) {
        List<Point> endPoints = new ArrayList<>();
        if (board == null || !Board.isValidIndex(startIndex)) {
            return endPoints;
        }

        int id = board.get(startIndex);
        Point p = Board.toPoint(startIndex);
        addPoints(endPoints, p, id, 2);

        for (int i = 0; i < endPoints.size(); i++) {
            Point end = endPoints.get(i);
            if (!isValidSkip(board, startIndex, Board.toIndex(end))) {
                endPoints.remove(i--);
            }
        }

        return endPoints;
    }

    /**
     * Checks if a skip is valid.
     *
     * @param board the game board.
     * @param startIndex the start index of the skip.
     * @param endIndex the end index of the skip.
     * @return true if the skip is available.
     */
    public static boolean isValidSkip(Board board, int startIndex, int endIndex) {
        if (board == null) {
            return false;
        }

        if (board.get(endIndex) != Board.IDs.EMPTY.getID()) {
            return false;
        }

        int id = board.get(startIndex);
        int midID = board.get(Board.toIndex(Board.middle(startIndex, endIndex)));
        if (id == Board.IDs.INVALID.getID() || id == Board.IDs.EMPTY.getID()) {
            return false;
        } else if (midID == Board.IDs.INVALID.getID() || midID == Board.IDs.EMPTY.getID()) {
            return false;
        } else if ((midID == Board.IDs.BLACK_CHECKER.getID() || midID == Board.IDs.BLACK_KING.getID())
                ^ (id == Board.IDs.WHITE_CHECKER.getID() || id == Board.IDs.WHITE_KING.getID())) {
            return false;
        }

        return true;
    }

    /**
     * Adds points representing available moves or skips.
     *
     * @param points the list of points to add to.
     * @param p	the selected point.
     * @param id the ID at the selected point.
     * @param delta the amount to add/subtract.
     */
    public static void addPoints(List<Point> points, Point p, int id, int delta) {
        boolean isKing = (id == Board.IDs.BLACK_KING.getID() || id == Board.IDs.WHITE_KING.getID());
        if (isKing || id == Board.IDs.BLACK_CHECKER.getID()) {
            points.add(new Point(p.x + delta, p.y + delta));
            points.add(new Point(p.x - delta, p.y + delta));
        }

        if (isKing || id == Board.IDs.WHITE_CHECKER.getID()) {
            points.add(new Point(p.x + delta, p.y - delta));
            points.add(new Point(p.x - delta, p.y - delta));
        }
    }
}
