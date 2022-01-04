package pl.polsl.lab.dcieslik.warcaby.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game state of Checkers.
 *
 * @author Dawid Cieślik
 */
public class Board {

    /**
     * Enum class representing the IDs of the specific tiles on the board.
     */
    public enum IDs {
        /**
         * An ID indicating a point was not on the board.
         */
        INVALID(-1),
        /**
         * The ID of an empty tile.
         */
        EMPTY(0),
        /**
         * The ID of a white checker.
         */
        WHITE_CHECKER(1),
        /**
         * The ID of a black checker.
         */
        BLACK_CHECKER(2),
        /**
         * The ID of a white checker that is also a king.
         */
        WHITE_KING(3),
        /**
         * The ID of a black checker that is also a king.
         */
        BLACK_KING(4);

        /**
         * ID of the selected value.
         */
        private final int id;

        /**
         * Enum class constructor.
         *
         * @param id ID of the selected value.
         */
        IDs(int id) {
            this.id = id;
        }

        /**
         * Returns ID of the selected value.
         *
         * @return ID of the selected value.
         */
        public int getID() {
            return id;
        }
    }

    /**
     * The current state of the board.
     */
    private int[] state;

    /**
     * Class constructor.
     */
    public Board() {
        this.state = new int[3];
        for (int i = 0; i < 12; i++) {
            set(i, IDs.BLACK_CHECKER.getID());
            set(31 - i, IDs.WHITE_CHECKER.getID());
        }
    }

    /**
     * Creates a copy of the board.
     *
     * @return an exact copy of this board.
     */
    public Board copy() {
        Board copy = new Board();
        copy.state = state.clone();
        return copy;
    }

    /**
     * Converts a point to an index of a black tile on the checker board taking
     * coordinates (for example, (1, 0) is index 0, (3, 0) is index 1, ... (7,
     * 7) is index 31).
     *
     * @param x	the x-coordinate (from 0 to 7).
     * @param y	the y-coordinate (from 0 to 7).
     * @return the index of the black tile or -1 if the point is not a black
     * tile.
     */
    public static int toIndex(int x, int y) {
        if (!isValidPoint(new Point(x, y))) {
            return -1;
        }

        return y * 4 + x / 2;
    }

    /**
     * Converts a point to an index of a black tile on the checker board taking
     * a Point (for example, (1, 0) is index 0, (3, 0) is index 1, ... (7, 7) is
     * index 31).
     *
     * @param p	the point to convert to an index.
     * @return the index of the black tile or -1 if the point is not a black
     * tile.
     */
    public static int toIndex(Point p) {
        return (p == null) ? -1 : toIndex(p.x, p.y);
    }

    /**
     * Sets the ID of a black tile on the board at the specified location.
     *
     * @param x	the x-coordinate (from 0 to 7).
     * @param y	the y-coordinate (from 0 to 7).
     * @param id the new ID to set the black tile to.
     */
    public void set(int x, int y, int id) {
        set(toIndex(x, y), id);
    }

    /**
     * Sets the ID of a black tile on the board at the specified location.
     *
     * @param index the index of the black tile (from 0 to 31).
     * @param id the new ID to set the black tile to.
     */
    public void set(int index, int id) {
        if (!isValidIndex(index)) {
            return;
        }

        if (id < 0) {
            id = IDs.EMPTY.getID();
        }

        for (int i = 0; i < state.length; i++) {
            boolean set = ((1 << (state.length - i - 1)) & id) != 0;
            this.state[i] = setBit(state[i], index, set);
        }
    }

    /**
     * Gets the ID corresponding to the specified point.
     *
     * @param x	the x-coordinate (from 0 to 7).
     * @param y	the y-coordinate (from 0 to 7).
     * @return the ID at the specified location.
     */
    public int get(int x, int y) {
        return get(toIndex(x, y));
    }

    /**
     * Gets the ID corresponding to the specified point.
     *
     * @param index the index of the black tile (from 0 to 31).
     * @return the ID at the specified location.
     */
    public int get(int index) {
        if (!isValidIndex(index)) {
            return IDs.INVALID.getID();
        }

        return getBit(state[0], index) * 4 + getBit(state[1], index) * 2
                + getBit(state[2], index);
    }

    /**
     * Sets or clears the specified bit in the target value and returns the
     * updated value.
     *
     * @param target the target value to update.
     * @param bit the bit to update (from 0 to 31).
     * @param set true to set the bit, false to clear the bit.
     * @return the updated target value with the bit set or cleared.
     */
    public static int setBit(int target, int bit, boolean set) {
        if (bit < 0 || bit > 31) {
            return target;
        }

        if (set) {
            target |= (1 << bit);
        } else {
            target &= (~(1 << bit));
        }

        return target;
    }

    /**
     * Gets the state of a bit (is it set (1) or not (0)).
     *
     * @param target the target value to get the bit from.
     * @param bit the bit to get (from 0 to 31).
     * @return 1 if the specified bit is set, 0 otherwise.
     */
    public static int getBit(int target, int bit) {
        if (bit < 0 || bit > 31) {
            return 0;
        }

        return (target & (1 << bit)) != 0 ? 1 : 0;
    }

    /**
     * Gets the middle point on the checker board between two points taking
     * Points.
     *
     * @param p1 the first point of a black tile.
     * @param p2 the second point of a black tile.
     * @return the middle point between two points or (-1, -1) if such point is
     * invalid.
     */
    public static Point middle(Point p1, Point p2) {
        if (p1 == null || p2 == null) {
            return new Point(-1, -1);
        }

        return middle(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * Gets the middle point on the checker board between two points taking
     * indexes.
     *
     * @param index1 the index of the first point (from 0 to 31).
     * @param index2 the index of the second point (from 0 to 31).
     * @return the middle point between two points or (-1, -1) if such point is
     * invalid.
     */
    public static Point middle(int index1, int index2) {
        return middle(toPoint(index1), toPoint(index2));
    }

    /**
     * Gets the middle point on the checker board between two points taking
     * coordinates.
     *
     * @param x1 the x-coordinate of the first point.
     * @param y1 the y-coordinate of the first point.
     * @param x2 the x-coordinate of the second point.
     * @param y2 the y-coordinate of the second point.
     * @return the middle point between two points or (-1, -1) if such point is
     * invalid.
     */
    public static Point middle(int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1;
        if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 > 7 || y1 > 7 || x2 > 7 || y2 > 7) {
            return new Point(-1, -1);
        } else if (x1 % 2 == y1 % 2 || x2 % 2 == y2 % 2) {
            return new Point(-1, -1);
        } else if (Math.abs(dx) != Math.abs(dy) || Math.abs(dx) != 2) {
            return new Point(-1, -1);
        }

        return new Point(x1 + dx / 2, y1 + dy / 2);
    }

    /**
     * Searches through the board and finds black tiles that match the specified
     * ID.
     *
     * @param id the ID to search for.
     * @return a list of points on the board with the specified ID. If none
     * exist, an empty list is returned.
     */
    public List<Point> find(int id) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            if (get(i) == id) {
                points.add(toPoint(i));
            }
        }

        return points;
    }

    /**
     * Converts a black tile index (0 to 31) to an (x, y) point (for example,
     * (1, 0) is index 0, (3, 0) is index 1, ... (7, 7) is index 31).
     *
     * @param index the index of the black tile to convert to a point.
     * @return the (x, y) point corresponding to the black tile index or the
     * point (-1, -1) if the index is not between 0 - 31.
     */
    public static Point toPoint(int index) {
        int y = index / 4;
        int x = 2 * (index % 4) + (y + 1) % 2;
        return !isValidIndex(index) ? new Point(-1, -1) : new Point(x, y);
    }

    /**
     * Checks if an index corresponds to a black tile on the checker board.
     *
     * @param testIndex	the index to check.
     * @return true if the index is between 0 and 31.
     */
    public static boolean isValidIndex(int testIndex) {
        return testIndex >= 0 && testIndex < 32;
    }

    /**
     * Checks if a point corresponds to a black tile on the checker board.
     *
     * @param point the point to check.
     * @return true if the point is on a black tile on the board.
     */
    public static boolean isValidPoint(Point point) {
        if (point == null) {
            return false;
        }

        final int x = point.x, y = point.y;
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            return false;
        }

        return x % 2 != y % 2;
    }

    /**
     * Checks if a selected point is valid.
     *
     * @param isP1Turn the flag of a turn.
     * @param selected the point to test.
     * @return true if the selected point is a valid checker to make a move in
     * the current turn.
     */
    public boolean isValidSelection(boolean isP1Turn, Point selected) {
        int i = toIndex(selected), id = get(i);
        if (id == IDs.EMPTY.getID() || id == IDs.INVALID.getID()) {
            return false;
        } else if (isP1Turn ^ (id == IDs.WHITE_CHECKER.getID() || id == IDs.WHITE_KING.getID())) {
            return false;
        } else if (!MoveGenerator.getSkips(this, i).isEmpty()) {
            return true;
        } else if (MoveGenerator.getMoves(this, i).isEmpty()) {
            return false;
        }

        java.util.List<Point> points = find(isP1Turn ? IDs.WHITE_CHECKER.getID() : IDs.BLACK_CHECKER.getID());
        points.addAll(find(isP1Turn ? IDs.WHITE_KING.getID() : IDs.BLACK_KING.getID()));
        for (Point p : points) {
            int checker = toIndex(p);
            if (checker == i) {
                continue;
            }
            if (!MoveGenerator.getSkips(this, checker).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
