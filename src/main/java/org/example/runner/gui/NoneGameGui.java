package org.example.runner.gui;

import org.example.domain.game.Board;
import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.awt.*;
import java.util.List;

public class NoneGameGui implements GameGui{
    @Override
    public void setPlayerMove(Player player) {

    }

    @Override
    public void setBoard(Board board, List<Point> validMoves) {

    }

    @Override
    public Point getNextMove() {
        return null;
    }

    @Override
    public void startGame(Game game) {

    }

    @Override
    public void finishGame() {

    }
}
