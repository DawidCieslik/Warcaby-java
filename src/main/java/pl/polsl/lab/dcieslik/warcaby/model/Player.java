package pl.polsl.lab.dcieslik.warcaby.model;

import pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException;

/**
 * Represents a player in a game - each player has a name.
 *
 * @author Dawid Cieślik
 */
public class Player {

    /**
     * Player name.
     */
    private String name;

    /**
     * Class constructor.
     *
     * @param name player's name.
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Sets a player's name.
     *
     * @param name new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a player's name.
     *
     * @return player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if name consists only of letters.
     *
     * @param player2 second player to compare names.
     * @throws pl.polsl.lab.dcieslik.warcaby.exceptions.InvalidNameException
     * throws exception if at least one of player name is invalid
     */
    public void validate(Player player2) throws InvalidNameException {
        if (this.name.equals("") || player2.getName().equals("")) {
            throw new InvalidNameException("Nazwy graczy nie mogą być puste.");
        } else if (this.name.equals(player2.getName())) {
            throw new InvalidNameException("Nazwy graczy nie mogą być takie same.");
        }
    }
}
