package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

public class TilesCountHeuristic implements Heuristic{
    @Override
    public int calculate(Game game, Player player) {
        return game.getStats().get(player);
    }
}
