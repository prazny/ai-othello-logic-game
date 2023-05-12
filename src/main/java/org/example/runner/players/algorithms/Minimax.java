package org.example.runner.players.algorithms;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.players.Player;
import org.example.runner.players.heuristics.Heuristic;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Minimax implements Algorithm {
    private Heuristic heuristic;
    private Player player;
    private int treeDepth = 3;

    public Minimax(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public Minimax(Heuristic heuristic, int treeDepth) {
        this.heuristic = heuristic;
        this.treeDepth = treeDepth;
    }

    public Point getNextMove(Game game, Player player) {
        this.player = player;
        Node node = minimax(game, treeDepth);
        Point move = node.getMove();
        if(move == null) {
            System.out.println("Null move detected..");
        }
        return move;
    }

    private Node minimax(Game game, int depth) {
        if (game.isGameFinished()) {
            return new Node(null, heuristic.calculate(game, player));
        }
        if (depth == 0) return new Node(null, heuristic.calculate(game, player));

        Point bestMove = null;
        double bestScore;
        if (game.getCurrentPlayer() == player) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }

        Game gameCpy;

        try {

            if(game.getValidMoves().isEmpty()) {
                game.skipPlayer();
                return minimax(game.getGameCopy(), depth-1);
            }

            for (Point move : game.getValidMoves()) {
                gameCpy = game.getGameCopy();
                gameCpy.makeMove(move);


                Node node = minimax(gameCpy.getGameCopy(), depth - 1);

                double score = node.getScore();
                if ((game.getCurrentPlayer() == player && score > bestScore)
                        || (game.getCurrentPlayer() != player && score < bestScore)) {
                    bestScore = score;
                    bestMove = move;

                }

            }

            return new Node(bestMove, bestScore);
        } catch (GameFinished | InvalidMove gameFinished) {
            throw new RuntimeException();
        }
    }
}
