package pl.polsl.lab.dcieslik.warcaby.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException;

/**
 * Class to test methods of Player class.
 *
 * @author Dawid Cieślik
 */
public class PlayerTest {

    /**
     * Test of validate method, of class Player.
     *
     * @param name2 second player name to compare names.
     * @throws pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException
     * throws exception if at least one of player name is invalid
     */
    @ParameterizedTest
    @ValueSource(strings = {"b", "caa", "DcA", "xDAĄĆ", "aDbSAG", "A"})
    public void testValidateIfValid(String name2) throws InvalidNameException {

        //GIVEN
        Player player1 = new Player("a");
        Player player2 = new Player(name2);
        boolean expResult = false;

        //WHEN
        try {
            player1.validate(player2);
            expResult = true;
        } //THEN
        catch (InvalidNameException e) {
            fail(e.getMessage());
        }
        assertTrue(expResult);
    }

    /**
     * Test of validate method, of class Player.
     *
     * @param name1 first player name to compare names.
     * @param name2 second player name to compare names.
     * @throws pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException
     * throws exception if at least one of player name is invalid
     */
    @ParameterizedTest
    @CsvSource({"a,a", "b,b"})
    public void testValidateIfTheSame(String name1, String name2) throws InvalidNameException {

        //GIVEN
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        boolean error = true;

        //WHEN
        try {
            player1.validate(player2);
        } //THEN
        catch (InvalidNameException e) {
            error = false;
            assertEquals("Nazwy graczy nie mogą być takie same.", e.getMessage());
        }
        if (error == true)
            fail("Parametry były poprawne.");
    }

    /**
     * Test of validate method, of class Player.
     *
     * @param name1 first player name to compare names.
     * @param name2 second player name to compare names.
     * @throws pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException
     * throws exception if at least one of player name is invalid
     */
    @ParameterizedTest
    @CsvSource({"'a',''", "'','a'", "'',''"})
    public void testValidateIfNull(String name1, String name2) throws InvalidNameException {

        //GIVEN
        Player player1 = new Player(name1);
        Player player2 = new Player(name2);
        boolean error = true;

        //WHEN
        try {
            player1.validate(player2);
        } //THEN
        catch (InvalidNameException e) {
            error = false;
            assertEquals("Nazwy graczy nie mogą być puste.", e.getMessage());
        }
        if (error == true)
            fail("Parametry były poprawne.");
    }
}
