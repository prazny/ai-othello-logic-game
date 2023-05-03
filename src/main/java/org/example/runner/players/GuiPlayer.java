package org.example.runner.players;

import org.example.domain.game.Game;
import org.example.runner.gui.GameGui;

import java.awt.*;

public class GuiPlayer extends PlayerAbstract implements Player {
    public GuiPlayer(String name, Color color) {
        super(name, color);
    }

    @Override
    public Point getNextMove(Game game, GameGui gameGui) {
        return gameGui.getNextMove();
    }


}
