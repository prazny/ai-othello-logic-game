package org.example.runner.players.heuristics;

import org.example.domain.game.Game;
import org.example.runner.players.ComputerPlayer;
import org.example.runner.players.Player;

import java.util.Random;

public class RandomHeuristic implements Heuristic{
    public int calculate(Game game, Player player) {
        Random random = new Random();
        return random.nextInt(1, 64);
    }
}
