package org.example.runner.gui;

import org.example.domain.game.Board;
import org.example.domain.game.Game;
import org.example.runner.players.Player;

import java.awt.*;
import java.util.List;

public interface GameGui {
    public void setPlayerMove(Player player);
    public void setBoard(Board board, List<Point> validMoves);
    public Point getNextMove();

    public void startGame(Game game);
    public void finishGame();


}
