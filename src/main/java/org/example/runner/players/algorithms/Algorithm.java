package org.example.runner.players.algorithms;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.awt.*;

public interface Algorithm {
    public Point getNextMove(Game game, Player player);
}
