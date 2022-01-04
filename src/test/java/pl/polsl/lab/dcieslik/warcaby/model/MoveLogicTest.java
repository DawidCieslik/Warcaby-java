package pl.polsl.lab.dcieslik.warcaby.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.polsl.lab.dcieslik.warcaby.view.*;

/**
 * Class to test methods of MoveLogic class.
 *
 * @author Dawid Cieślik
 */
public class MoveLogicTest {

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
     * Test of isValidMove method, of class MoveLogic.
     *
     * @param startIndex the start index of the move.
     * @param endIndex the end index of the move.
     */
    @ParameterizedTest
    @CsvSource({"23,18", "22,17", "20,16"})
    public void testIsValidMove_3args(int startIndex, int endIndex) {

        //GIVEN
        boolean expResult = true;

        //WHEN
        boolean result = MoveLogic.isValidMove(game, startIndex, endIndex);

        //THEN
        assertEquals(expResult, result);
    }

    /**
     * Test of isValidMove method, of class MoveLogic.
     *
     * @param isP1Turn the flag of a turn.
     * @param startIndex the start index of the move.
     * @param endIndex the end index of the move.
     * @param skipIndex the index of the last skip this turn.
     */
    @ParameterizedTest
    @CsvSource({"true,23,18,14", "false,11,15,19", "false,10,15,19"})
    public void testIsValidMove_5args(boolean isP1Turn, int startIndex, int endIndex, int skipIndex) {

        //GIVEN
        boolean expResult = false;

        //WHEN
        boolean result = MoveLogic.isValidMove(board, isP1Turn, startIndex, endIndex, skipIndex);

        //THEN
        assertEquals(expResult, result);
    }

}
