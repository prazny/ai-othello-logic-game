package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

public class EdgeHeuristic implements Heuristic {
    @Override
    public double calculate(Game game, Player player) {
        int x = game.getLastMove().x;
        int y = game.getLastMove().y;
        if(x > 4) x = 8 - x;
        if(y > 4) y = 8 - y;

        return 8 - Math.min(x, y);
    }

    @Override
    public String getShortName() {
        return "Edge";
    }
}
