package org.example.runner.players.algorithms;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.players.Player;
import org.example.runner.players.heuristics.Heuristic;

import java.awt.*;

public class AlphaBeta implements Algorithm {
    private Heuristic heuristic;
    private Player player;
    private int treeDepth = 3;

    public AlphaBeta(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public AlphaBeta(Heuristic heuristic, int treeDepth) {
        this.heuristic = heuristic;
        this.treeDepth = treeDepth;
    }

    @Override
    public Point getNextMove(Game game, Player player) {
        this.player = player;
        Node node = alphaBeta(game, treeDepth, Float.MIN_VALUE, Float.MAX_VALUE);
        System.out.println("returned: " + node.getMove());
        return node.getMove();
    }

    private Node alphaBeta(Game game, int depth, float alpha, float beta) {
        if (game.isGameFinished()) {
            return new Node(null, heuristic.calculate(game, player));
        }
        if (depth == 0) return new Node(null, heuristic.calculate(game, player));


        Point bestMove = null;
        int bestScore;
        if (game.getCurrentPlayer() == player) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }

        Game gameCpy;
        try {
            for (Point move : game.getValidMoves()) {
                gameCpy = game.getGameCopy();
                gameCpy.makeMove(move);

                Node node = alphaBeta(gameCpy.getGameCopy(), depth - 1, alpha, beta);
                int score = node.getScore();

                if ((game.getCurrentPlayer() == player && score > bestScore) || (game.getCurrentPlayer() != player && score < bestScore)) {
                    bestScore = score;
                    bestMove = move;
                }

                if (game.getCurrentPlayer() == player) {
                    alpha = Math.max(alpha, bestScore);
                } else {
                    beta = Math.min(beta, bestScore);
                }

                if(beta <= alpha) break;

            }
            return new Node(bestMove, bestScore);
        } catch (GameFinished | InvalidMove gameFinished) {
            throw new RuntimeException();
        }

    }
}
