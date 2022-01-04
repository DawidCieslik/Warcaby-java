package pl.polsl.lab.dcieslik.warcaby.exceptions;

/**
 * Class representing custom exception to throw if name consists not only of
 * letters.
 *
 * @author Dawid Cieślik
 */
public class InvalidNameException extends Exception {

    /**
     * Class constructor.
     *
     * @param str String to display.
     */
    public InvalidNameException(String str) {
        super(str);
    }
}
