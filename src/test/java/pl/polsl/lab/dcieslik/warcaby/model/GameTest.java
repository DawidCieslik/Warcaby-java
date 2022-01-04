package pl.polsl.lab.dcieslik.warcaby.model;

import java.awt.Point;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.lab.dcieslik.warcaby.view.BoardWindow;
import pl.polsl.lab.dcieslik.warcaby.view.GameWindow;

/**
 * Class to test methods of Game class.
 *
 * @author Dawid Cieślik
 */
public class GameTest {

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
     * Test of handleClick method, of class Game.
     *
     * @param x	the x-coordinate of the click.
     * @param y	the y-coordinate of the click.
     */
    @ParameterizedTest
    @CsvSource({"435,475", "115,467", "526,230"})
    public void testHandleClick(int x, int y) {

        //GIVEN
        String expResult = game.getGameState();

        //WHEN
        game.handleClick(x, y);

        //THEN
        String result = game.getGameState();
        assertEquals(expResult, result);
    }

    /**
     * Test of move method, of class Game.
     *
     * @param x1 the x-coordinate of the start point.
     * @param y1 the y-coordinate of the start point.
     * @param x2 the x-coordinate of the end point.
     * @param y2 the y-coordinate of the end point.
     */
    @ParameterizedTest
    @CsvSource({"0,5,1,4", "4,5,5,4", "2,5,1,4"})
    public void testMove_Point_Point(int x1, int y1, int x2, int y2) {

        //GIVEN
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        boolean expResult = true;

        //WHEN
        boolean result = game.move(start, end);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of move method, of class Game.
     *
     * @param startIndex the start index.
     * @param endIndex the end index.
     */
    @ParameterizedTest
    @CsvSource({"20,16", "22,18", "21,16"})
    public void testMove_int_int(int startIndex, int endIndex) {

        //GIVEN
        boolean expResult = true;

        //WHEN
        boolean result = game.move(startIndex, endIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of setGameState method, of class Game.
     *
     * @param state the game state as a string of data.
     */
    @ParameterizedTest
    @ValueSource(strings = {"111111111011000000202210222222221-1", "111111111011010000202220222222221-1", "1111111110110100022022002222222205"})
    public void testSetGameState(String state) {

        //GIVEN
        String expResult = game.getGameState();

        //WHEN
        game.setGameState(state);
        String result = game.getGameState();

        //THEN
        assertNotEquals(expResult, result);
    }

}
