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
        return algorithm.getNextMove(gameCpy, this);
        //return game.getValidMoves().get(0);
    }


}
