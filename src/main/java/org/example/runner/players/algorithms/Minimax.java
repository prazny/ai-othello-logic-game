package org.example.runner.players.algorithms;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.players.Player;
import org.example.runner.players.heuristics.Heuristic;

import java.awt.*;

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
        assert node != null;
        System.out.println("returned: " + node.getMove());
        return node.getMove();
    }

    private Node minimax(Game game, int depth) {
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

               /* System.out.println(game.getBoard().toString());
                System.out.println();
                System.out.println(gameCpy.getBoard().toString());
                System.out.println();

                System.out.println(move);*/
                gameCpy.makeMove(move);


                Node node = minimax(gameCpy.getGameCopy(), depth - 1);


                //int score = heuristic.calculate(game, player);
                int score = node.getScore();

                if ((game.getCurrentPlayer() == player && score > bestScore) || (game.getCurrentPlayer() != player && score < bestScore)) {
                    bestScore = score;
                    bestMove = move;
                }

            }
            return new Node(bestMove, bestScore);
        } catch (GameFinished gameFinished) {
            System.out.println("Game finished");
            throw new RuntimeException();
        } catch (InvalidMove invaildMove) {
            System.out.println("invalid move");
            throw new RuntimeException();
        }

    }
}
