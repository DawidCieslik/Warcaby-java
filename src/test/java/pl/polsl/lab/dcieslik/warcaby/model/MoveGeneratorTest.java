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
 * Class to test methods of MoveGenerator class.
 *
 * @author Dawid Cieślik
 */
public class MoveGeneratorTest {

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
     * Test of getMoves method, of class MoveGenerator.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"0,5", "4,5", "5,2", "5,6"})
    public void testGetMoves_Board_Point(int x, int y) {

        //GIVEN
        Point start = new Point(x, y);
        List<Point> expResult = new ArrayList();
        if (x == 0 && y == 5) {
            expResult.add(new Point(1, 4));
        } else if (x == 4 && y == 5) {
            expResult.add(new Point(5, 4));
            expResult.add(new Point(3, 4));
        } else if (x == 5 && y == 2) {
            expResult.add(new Point(6, 3));
            expResult.add(new Point(4, 3));
        } else if (x == 5 && y == 6) {
            //No avaiable moves.
        }

        //WHEN
        List<Point> result = MoveGenerator.getMoves(board, start);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoves method, of class MoveGenerator.
     *
     * @param startIndex the index to look for moves around.
     */
    @ParameterizedTest
    @ValueSource(ints = {20, 22, 10, 26})
    public void testGetMoves_Board_int(int startIndex) {

        //GIVEN
        List<Point> expResult = new ArrayList();
        switch (startIndex) {
            case 20:
                expResult.add(new Point(1, 4));
                break;
            case 22:
                expResult.add(new Point(5, 4));
                expResult.add(new Point(3, 4));
                break;
            case 10:
                expResult.add(new Point(6, 3));
                expResult.add(new Point(4, 3));
                break;
            case 26:
                //No available moves.
                break;
            default:
                break;
        }

        //WHEN
        List<Point> result = MoveGenerator.getMoves(board, startIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of getSkips method, of class MoveGenerator.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     */
    @ParameterizedTest
    @CsvSource({"0,5", "4,5", "5,2", "5,6"})
    public void testGetSkips_Board_Point(int x, int y) {

        //GIVEN
        Point start = new Point(x, y);
        List<Point> expResult = new ArrayList();

        //WHEN
        List<Point> result = MoveGenerator.getSkips(board, start);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of getSkips method, of class MoveGenerator.
     *
     * @param startIndex the index to look for skips around.
     */
    @ParameterizedTest
    @ValueSource(ints = {20, 22, 10, 26})
    public void testGetSkips_Board_int(int startIndex) {

        //GIVEN
        List<Point> expResult = new ArrayList();

        //WHEN
        List<Point> result = MoveGenerator.getSkips(board, startIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidSkip method, of class MoveGenerator.
     *
     * @param startIndex the start index of the skip.
     * @param endIndex the end index of the skip.
     */
    @ParameterizedTest
    @CsvSource({"21,14", "22,13", "9,18"})
    public void testIsValidSkip(int startIndex, int endIndex) {

        //GIVEN
        boolean expResult = false;

        //WHEN
        boolean result = MoveGenerator.isValidSkip(board, startIndex, endIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of addPoints method, of class MoveGenerator.
     *
     * @param x	the x-coordinate of the selected point.
     * @param y	the y-coordinate of the selected point.
     * @param id the ID at the selected point.
     * @param delta the amount to add/subtract.
     */
    @ParameterizedTest
    @CsvSource({"0,5,2,1", "4,5,2,1", "5,2,1,1", "5,6,2,1"})
    public void testAddPoints(int x, int y, int id, int delta) {

        //GIVEN
        Point p = new Point(x, y);
        List<Point> result = MoveGenerator.getMoves(board, p);
        List<Point> expResult = new ArrayList();
        if (x == 0 && y == 5) {
            expResult.add(new Point(1, 4));
            expResult.add(new Point(1, 6));
            expResult.add(new Point(-1, 6));
        } else if (x == 4 && y == 5) {
            expResult.add(new Point(5, 4));
            expResult.add(new Point(3, 4));
            expResult.add(new Point(5, 6));
            expResult.add(new Point(3, 6));
        } else if (x == 5 && y == 2) {
            expResult.add(new Point(6, 3));
            expResult.add(new Point(4, 3));
            expResult.add(new Point(6, 1));
            expResult.add(new Point(4, 1));
        } else if (x == 5 && y == 6) {
            expResult.add(new Point(6, 7));
            expResult.add(new Point(4, 7));
        }

        //WHEN
        MoveGenerator.addPoints(result, p, id, delta);

        //THEN
        assertEquals(expResult, result);
    }

}
