package pl.polsl.lab.dcieslik.warcaby.view;

import pl.polsl.lab.dcieslik.warcaby.model.*;
import javax.swing.*;
import java.awt.*;

/**
 * A graphical user interface responsible for displaying player names.
 *
 * @author Dawid Cieślik
 */
public class PlayersPanel extends JPanel {

    /**
     * Label with player 1 name.
     */
    private JLabel player1Label = new JLabel();

    /**
     * Label with player 2 name.
     */
    private JLabel player2Label = new JLabel();

    /**
     * Class constructor.
     *
     * @param player1 white player.
     * @param player2 black player.
     */
    public PlayersPanel(Player player1, Player player2) {
        super(new GridLayout(0, 2));
        player1Label.setText(player1.getName());
        player2Label.setText(player2.getName());

        JPanel player1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel player2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        player1Panel.add(new JLabel("Gracz 1 (Biały): "));
        player1Panel.add(player1Label);

        player2Panel.add(new JLabel("Gracz 2 (Czarny): "));
        player2Panel.add(player2Label);

        this.add(player1Panel);
        this.add(player2Panel);
    }
}
