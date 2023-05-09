package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.runner.players.Player;

public class MobilityHeuristic implements Heuristic{
    @Override
    public double calculate(Game game, Player player) {
        if(game.isGameFinished()) return 64.0;
        return 64 - game.getValidMoves().size();

    }

    @Override
    public String getShortName() {
        return "Mobility";
    }
}
