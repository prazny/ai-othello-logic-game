package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.util.List;
import java.util.Map;

public class MixedHeuristic implements Heuristic {
    public static class MixedHeuristicPair {
        public Heuristic heuristic;
        public double factor;

        public MixedHeuristicPair(Heuristic heuristic, double factor) {
            this.heuristic = heuristic;
            this.factor = factor;
        }
    }

    List<MixedHeuristicPair> heuristicList;

    public MixedHeuristic(List<MixedHeuristicPair> heuristicList) {
        this.heuristicList = heuristicList;
    }


    @Override
    public double calculate(Game game, Player player) {
        double sum = 0;

        if (game.isGameFinished()) return 1000;

        for(MixedHeuristicPair pair : heuristicList) {
            sum += pair.heuristic.calculate(game, player) * pair.factor;
        }

        return sum/heuristicList.size();
    }

    @Override
    public String getShortName() {
        StringBuilder name = new StringBuilder();
        name.append("Mix ");

        for(MixedHeuristicPair pair: heuristicList) {
            name.append(pair.heuristic.getShortName(), 0, 3);
        }
        return name.toString();
    }
}
