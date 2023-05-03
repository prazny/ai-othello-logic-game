package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.awt.*;

public class CornersHeuristic implements Heuristic {
    static Point[] corners = {new Point(0, 0), new Point(0, 7), new Point(7, 0), new Point(7, 7)};

    @Override
    public int calculate(Game game, Player player) {
        float distance = 0;
        for (Point corner : corners) {
            distance += game.getLastMove().distance(corner);
        }
        return (int) (distance * 10);
    }
}
