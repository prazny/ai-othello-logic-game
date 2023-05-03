package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

public interface Heuristic {
    public int calculate(Game game, Player player);
}
