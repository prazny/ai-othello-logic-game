package org.example.runner.gui;

import org.example.domain.game.Board;
import org.example.domain.game.Game;
import org.example.runner.gui.views.BoardGui;
import org.example.runner.gui.views.GameInfoGui;
import org.example.runner.players.Player;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleGameGui implements GameGui {
    private final GameInfoGui gameInfoGui;
    private BoardGui board;
    private Game game;

    private boolean noticesEnabled = true;

    public ConsoleGameGui() {
        this.game = null;
        gameInfoGui = new GameInfoGui();
    }

    public void setNoticesEnabled(boolean noticesEnabled) {
        this.noticesEnabled = noticesEnabled;
    }


    public void startGame(Game game) {
        this.game = game;
        if (noticesEnabled)
            System.out.println("\n\n\nRozpoczęto grę Reversi!");
    }

    @Override
    public void finishGame() {
        if (noticesEnabled) {
            System.out.println("Game finished!");
            System.out.println("Liczba rund: " + game.getRoundsCount());
            Map<Player, Integer> stats = game.getStats();
            for (Map.Entry<Player, Integer> pair : stats.entrySet()) {
                System.out.format("%s - %s\n", pair.getKey().getName(), pair.getValue());
            }
        }
    }

    public void setPlayerMove(Player player) {
        if (noticesEnabled) {
            System.out.println("Runda gracza " + player.getName());
        }

    }

    public void setBoard(Board board, List<Point> validMoves) {
        System.out.println(game);
        System.out.println("Valid moves: ");
        for (Point move : validMoves) {
            System.out.format("(%s, %s), ", move.x, move.y);
        }
        System.out.println();
    }

    public Point getNextMove() {

        System.out.print("Type move: x y: ");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        return new Point(x, y);
    }

}
