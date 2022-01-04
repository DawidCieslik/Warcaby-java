package pl.polsl.lab.dcieslik.warcaby.controller;

import java.awt.*;
import java.awt.event.*;
import pl.polsl.lab.dcieslik.warcaby.model.*;
import pl.polsl.lab.dcieslik.warcaby.view.*;

/**
 * Handles actions on the board.
 *
 * @author Dawid Cieślik
 */
public class Listener {

    /**
     * The game of checkers that is being played.
     */
    private final Game game;

    /**
     * The checker board component playing the updatable game.
     */
    private final BoardWindow boardWindow;

    /**
     * Class constructor.
     *
     * @param boardWindow window of the board.
     * @param game the game of checkers that is being played.
     */
    public Listener(BoardWindow boardWindow, Game game) {
        this.game = game;
        this.boardWindow = boardWindow;
        this.boardWindow.addActionListener(new ClickListener());
    }

    /**
     * Responds to the click events on the component.
     */
    private class ClickListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!game.isGameOver()) {
                Point m = boardWindow.getMousePosition();
                if (m != null) {
                    game.handleClick(m.x, m.y);
                }
            }
        }
    }
}
