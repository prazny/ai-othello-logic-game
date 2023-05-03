package org.example.runner.players;

import org.example.domain.game.Game;
import org.example.runner.gui.GameGui;

import java.awt.*;

public interface Player {
    public Point getNextMove(Game game, GameGui gameGui);
    public String getName();
    public Color getColor();

    public String getColorName();
}
