package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.awt.*;

public class CornersHeuristic implements Heuristic {
    static Point[] corners = {new Point(0, 0), new Point(0, 7), new Point(7, 0), new Point(7, 7)};
    static double minDistance = 0;
    static double maxDistance = Math.sqrt(128);

    @Override
    public double calculate(Game game, Player player) {
        double distance =  game.getLastMove().distance(corners[0]);
        for (Point corner : corners) {
            if(distance > game.getLastMove().distance(corner))
                distance = game.getLastMove().distance(corner);
        }
        return maxDistance - distance;
    }

    @Override
    public String getShortName() {
        return "Corners";
    }

    private double normalize(double value) {

        return  maxDistance - (value * 64 / maxDistance);
    }
}
