package pl.polsl.lab.dcieslik.warcaby.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.lab.dcieslik.warcaby.view.BoardWindow;
import pl.polsl.lab.dcieslik.warcaby.view.GameWindow;

/**
 * Class to test methods of Board class.
 *
 * @author Dawid Cieślik
 */
public class BoardTest {

    /**
     * White player.
     */
    private final Player p1 = new Player("White");
    /**
     * Black player.
     */
    private final Player p2 = new Player("Black");
    /**
     * Window of the application.
     */
    private final GameWindow gameWindow = new GameWindow(p1, p2);
    /**
     * The graphical representation of the board.
     */
    private final BoardWindow boardWindow = new BoardWindow(gameWindow, p1, p2);
    /**
     * The game of checkers that is being played.
     */
    private final Game game = new Game(boardWindow);
    /**
     * The current state of the checker board.
     */
    private final Board board = game.getBoard();
    
    /**
     * Test of toIndex method, of class Board.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"2,2", "3,4", "6,3"})
    public void testToIndex_int_int(int x, int y) {

        //GIVEN
        int expResult = 0;
        if (x == 2 && y == 2) {
            expResult = -1;
        } else if (x == 3 && y == 4) {
            expResult = 17;
        } else if (x == 6 && y == 3) {
            expResult = 15;
        }

        //WHEN
        int result = Board.toIndex(x, y);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of toIndex method, of class Board.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"2,2", "3,4", "6,3"})
    public void testToIndex_Point(int x, int y) {

        //GIVEN
        Point p = new Point(x, y);
        int expResult = 0;
        if (x == 2 && y == 2) {
            expResult = -1;
        } else if (x == 3 && y == 4) {
            expResult = 17;
        } else if (x == 6 && y == 3) {
            expResult = 15;
        }

        //WHEN
        int result = Board.toIndex(p);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Board.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"3,2", "2,5", "6,3"})
    public void testGet_int_int(int x, int y) {

        //GIVEN
        int expResult = -1;
        if (x == 3 && y == 2) {
            expResult = 2;
        } else if (x == 2 && y == 5) {
            expResult = 1;
        } else if (x == 6 && y == 3) {
            expResult = 0;
        }

        //WHEN
        int result = board.get(x, y);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of get method, of class Board.
     *
     * @param index index of the selected point.
     */
    @ParameterizedTest
    @ValueSource(ints = {9, 21, 15})
    public void testGet_int(int index) {

        //GIVEN
        int expResult = -1;
        switch (index) {
            case 9: {
                expResult = 2;
                break;
            }
            case 21: {
                expResult = 1;
                break;
            }
            case 15: {
                expResult = 0;
                break;
            }
            default:
                break;
        }
        //WHEN
        int result = board.get(index);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of setBit method, of class Board.
     *
     * @param target the target value to update.
     * @param bit the bit to update (from 0 to 31).
     * @param set true to set the bit, false to clear the bit.
     */
    @ParameterizedTest
    @CsvSource({"2,30,true", "1,21,false", "5,14,true"})
    public void testSetBit(int target, int bit, boolean set) {

        //GIVEN
        int expResult = -1;
        if (target == 2 && bit == 30 && set == true) {
            expResult = 1073741826;
        } else if (target == 1 && bit == 21 && set == false) {
            expResult = 1;
        } else if (target == 5 && bit == 14 && set == true) {
            expResult = 16389;
        }

        //WHEN
        int result = Board.setBit(target, bit, set);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of getBit method, of class Board.
     *
     * @param target the target value to update.
     * @param bit the bit to update (from 0 to 31).
     */
    @ParameterizedTest
    @CsvSource({"2,30", "1,21", "5,14"})
    public void testGetBit(int target, int bit) {

        //GIVEN
        int expResult = 0;

        //WHEN
        int result = Board.getBit(target, bit);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of middle method, of class Board.
     *
     * @param x1 the x-coordinate of the start point.
     * @param y1 the y-coordinate of the start point.
     * @param x2 the x-coordinate of the end point.
     * @param y2 the y-coordinate of the end point.
     */
    @ParameterizedTest
    @CsvSource({"3,2,5,4", "2,5,4,7", "6,3,4,5"})
    public void testMiddle_Point_Point(int x1, int y1, int x2, int y2) {

        //GIVEN
        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);
        Point expResult = new Point(-1, -1);
        if (x1 == 3 && y1 == 2 && x2 == 5 && y2 == 4) {
            expResult = new Point(4, 3);
        } else if (x1 == 2 && y1 == 5 && x2 == 4 && y2 == 7) {
            expResult = new Point(3, 6);
        } else if (x1 == 6 && y1 == 3 && x2 == 4 && y2 == 5) {
            expResult = new Point(5, 4);
        }

        //WHEN
        Point result = Board.middle(point1, point2);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of middle method, of class Board.
     *
     * @param index1 index of the start point.
     * @param index2 index of the end point.
     */
    @ParameterizedTest
    @CsvSource({"9,18", "21,30", "15,22"})
    public void testMiddle_int_int(int index1, int index2) {

        //GIVEN
        Point expResult = new Point(-1, -1);
        if (index1 == 9 && index2 == 18) {
            expResult = new Point(4, 3);
        } else if (index1 == 21 && index2 == 30) {
            expResult = new Point(3, 6);
        } else if (index1 == 15 && index2 == 22) {
            expResult = new Point(5, 4);
        }

        //WHEN
        Point result = Board.middle(index1, index2);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of middle method, of class Board.
     *
     * @param x1 the x-coordinate of the start point.
     * @param y1 the y-coordinate of the start point.
     * @param x2 the x-coordinate of the end point.
     * @param y2 the y-coordinate of the end point.
     */
    @ParameterizedTest
    @CsvSource({"3,2,5,4", "2,5,4,7", "6,3,4,5"})
    public void testMiddle_4args(int x1, int y1, int x2, int y2) {

        //GIVEN
        Point expResult = new Point(-1, -1);
        if (x1 == 3 && y1 == 2 && x2 == 5 && y2 == 4) {
            expResult = new Point(4, 3);
        } else if (x1 == 2 && y1 == 5 && x2 == 4 && y2 == 7) {
            expResult = new Point(3, 6);
        } else if (x1 == 6 && y1 == 3 && x2 == 4 && y2 == 5) {
            expResult = new Point(5, 4);
        }

        //WHEN
        Point result = Board.middle(x1, y1, x2, y2);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of find method, of class Board.
     *
     * @param id the ID to search for.
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void testFind(int id) {

        //GIVEN
        List<Point> expResult = new ArrayList();
        switch (id) {
            case 0:
                expResult.add(new Point(0, 3));
                expResult.add(new Point(2, 3));
                expResult.add(new Point(4, 3));
                expResult.add(new Point(6, 3));
                expResult.add(new Point(1, 4));
                expResult.add(new Point(3, 4));
                expResult.add(new Point(5, 4));
                expResult.add(new Point(7, 4));
                break;
            case 1:
                expResult.add(new Point(0, 5));
                expResult.add(new Point(2, 5));
                expResult.add(new Point(4, 5));
                expResult.add(new Point(6, 5));
                expResult.add(new Point(1, 6));
                expResult.add(new Point(3, 6));
                expResult.add(new Point(5, 6));
                expResult.add(new Point(7, 6));
                expResult.add(new Point(0, 7));
                expResult.add(new Point(2, 7));
                expResult.add(new Point(4, 7));
                expResult.add(new Point(6, 7));
                break;
            case 2:
                expResult.add(new Point(1, 0));
                expResult.add(new Point(3, 0));
                expResult.add(new Point(5, 0));
                expResult.add(new Point(7, 0));
                expResult.add(new Point(0, 1));
                expResult.add(new Point(2, 1));
                expResult.add(new Point(4, 1));
                expResult.add(new Point(6, 1));
                expResult.add(new Point(1, 2));
                expResult.add(new Point(3, 2));
                expResult.add(new Point(5, 2));
                expResult.add(new Point(7, 2));
                break;
            default:
                break;
        }

        //WHEN
        List<Point> result = board.find(id);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of toPoint method, of class Board.
     *
     * @param index the index of the black tile to convert to a point.
     */
    @ParameterizedTest
    @ValueSource(ints = {9, 18, 21})
    public void testToPoint(int index) {

        //GIVEN
        Point expResult = new Point(-1, -1);
        switch (index) {
            case 9:
                expResult = new Point(3, 2);
                break;
            case 18:
                expResult = new Point(5, 4);
                break;
            case 21:
                expResult = new Point(2, 5);
                break;
            default:
                break;
        }

        //WHEN
        Point result = Board.toPoint(index);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidIndex method, of class Board.
     *
     * @param testIndex the index to check.
     */
    @ParameterizedTest
    @ValueSource(ints = {33, 18, -2})
    public void testIsValidIndex(int testIndex) {

        //GIVEN
        boolean expResult = false;
        switch (testIndex) {
            case 33:
                expResult = false;
                break;
            case 18:
                expResult = true;
                break;
            case -2:
                expResult = false;
                break;
            default:
                break;
        }

        //WHEN
        boolean result = Board.isValidIndex(testIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidPoint method, of class Board.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"-2,18", "21,37", "1,22", "1,5", "3,4"})
    public void testIsValidPoint(int x, int y) {
        
        //GIVEN
        Point point = new Point(x, y);
        boolean expResult = false;
        if (x == -2 && y == 18) {
            expResult = false;
        } else if (x == 21 && y == 37) {
            expResult = false;
        } else if (x == 1 && y == 22) {
            expResult = false;
        } else if (x == 1 && y == 5) {
            expResult = false;
        } else if (x == 3 && y == 4) {
            expResult = true;
        }

        //WHEN
        boolean result = Board.isValidPoint(point);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidSelection method, of class Board.
     *
     * @param isP1Turn the flag of a turn.
     * @param x the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"true,2,5", "false,3,2", "false,6,5", "true,4,3", "false,4,3", "true,1,2"})
    public void testIsValidSelection(boolean isP1Turn, int x, int y) {

        //GIVEN
        Point selected = new Point(x, y);
        boolean expResult = false;
        if (isP1Turn == true && x == 2 && y == 5) {
            expResult = true;
        } else if (isP1Turn == false && x == 3 && y == 2) {
            expResult = true;
        } else if (isP1Turn == false && x == 6 && y == 5) {
            expResult = false;
        } else if (isP1Turn == true && x == 4 && y == 3) {
            expResult = false;
        } else if (isP1Turn == false && x == 4 && y == 3) {
            expResult = false;
        } else if (isP1Turn == true && x == 1 && y == 2) {
            expResult = false;
        }

        //WHEN
        boolean result = board.isValidSelection(isP1Turn, selected);

        //THEN
        assertEquals(expResult, result);
    }

}
