package org.example.runner.players;

import org.example.domain.game.Game;
import org.example.runner.gui.GameGui;
import org.example.runner.players.algorithms.Algorithm;

import java.awt.*;

public class ComputerPlayer extends PlayerAbstract implements Player {
    Algorithm algorithm;
    int treeDepth = 5;


    public ComputerPlayer(String name, Color color, Algorithm algorithm) {
        super(name, color);
        this.algorithm = algorithm;
    }

    public void setTreeDepth(int treeDepth) {
        this.treeDepth = treeDepth;
    }

    @Override
    public Point getNextMove(Game game, GameGui gameGui) {
        Game gameCpy = game.getGameCopy();
        if (game.getValidMoves().size() == 0) return null;
        long startTime = System.currentTimeMillis();
        decisionCount++;

        lastMove = algorithm.getNextMove(gameCpy, this);
        decisionTime += System.currentTimeMillis() - startTime;
        return lastMove;
    }

}
