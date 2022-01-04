package pl.polsl.lab.dcieslik.warcaby.view;

import pl.polsl.lab.dcieslik.warcaby.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class responsible for managing the applcation window.
 *
 * @author Dawid Cieślik
 */
public class GameWindow extends JFrame {

    /**
     * Default height of a window.
     */
    private static final int DEFAULT_HEIGHT = 800;

    /**
     * Default width of a window.
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * Default title of a window.
     */
    private static final String DEFAULT_TITLE = "Warcaby";

    /**
     * Restart button on the top of a window.
     */
    private final JButton restartBtn;

    /**
     * The checker board component playing the updatable game.
     */
    private final BoardWindow board;

    /**
     * The panel component with players' names.
     */
    private static PlayersPanel playersPanel;

    /**
     * Class constructor.
     *
     * @param player1 white player.
     * @param player2 black player.
     */
    public GameWindow(Player player1, Player player2) {
        super(DEFAULT_TITLE);
        super.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        super.setLocationRelativeTo(null);
        super.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(GameWindow.EXIT_ON_CLOSE);
        
        restartBtn = new JButton("Restart");
        ButtonListener listener = new ButtonListener();
        restartBtn.addActionListener(listener);
        board = new BoardWindow(this, player1, player2);
        playersPanel = new PlayersPanel(player1, player2);

        JPanel layout = new JPanel(new BorderLayout());
        layout.add(restartBtn, BorderLayout.NORTH);
        layout.add(board, BorderLayout.CENTER);
        layout.add(playersPanel, BorderLayout.SOUTH);
        add(layout);
    }

    /**
     * Restarts the game in the window.
     */
    public void restart() {
        this.board.getGame().restart();
        this.board.update();
    }

    /**
     * Responds to the restart button when it is clicked.
     */
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();

            if (src == restartBtn) {
                dispose();
                Menu menu = new Menu();
                menu.setVisible(true);
            }
        }
    }
}
