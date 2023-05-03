package org.example.runner.gui.views;

import org.example.runner.players.Player;

import javax.swing.*;
import java.awt.*;

public class GameInfoGui  extends JPanel {
    Player currentPlayer;

    public GameInfoGui() {
        this.add(new JLabel("Game"));
    }

    public void setPlayer(Player player) {
        this.currentPlayer = player;
        this.remove(0);

        this.add(new JLabel(String.format("Current player: %s (%s)", player.getName(), player.getColorName())));
        this.setPreferredSize(new Dimension(500, 10));
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);
    }
}
