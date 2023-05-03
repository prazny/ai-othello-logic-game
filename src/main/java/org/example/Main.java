package org.example;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.GameRunner;
import org.example.runner.GameRunnerImpl;
import org.example.runner.gui.ConsoleGameGui;
import org.example.runner.gui.GameGui;
import org.example.runner.gui.GraphicalGameGui;
import org.example.runner.players.ComputerPlayer;
import org.example.runner.players.Player;
import org.example.runner.players.algorithms.Minimax;
import org.example.runner.players.heuristics.CornersHeuristic;
import org.example.runner.players.heuristics.RandomHeuristic;
import org.example.runner.players.heuristics.TilesCountHeuristic;

import java.awt.*;
import java.util.Scanner;

public class Main {
    final static int boardSize = 8;

    public static void ff(String[] args)  {
        GameGui gameGui = new ConsoleGameGui();
        Player playerA = new ComputerPlayer("A", Color.BLACK, new Minimax(new RandomHeuristic()));
        Player playerB = new ComputerPlayer("B", Color.WHITE, new Minimax(new RandomHeuristic()));

        Game game = new Game(playerA, playerB);
        Game gameCpy = game.getGameCopy();

        try {

            System.out.println(game.getBoard().hashCode());
            System.out.println(game.getBoard().toString());
            System.out.println(gameCpy.getBoard().hashCode());
            System.out.println(gameCpy.getBoard().toString());

            gameCpy.makeMove(new Point(3, 2));

            System.out.println(game.getBoard().hashCode());
            System.out.println(game.getBoard().toString());
            System.out.println(gameCpy.getBoard().hashCode());
            System.out.println(gameCpy.getBoard().toString());
        } catch (InvalidMove | GameFinished e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        GameGui gameGui = new GraphicalGameGui();
        Player playerA = new ComputerPlayer("A", Color.BLACK, new Minimax(new CornersHeuristic(), 5));
        Player playerB = new ComputerPlayer("B", Color.WHITE, new Minimax(new TilesCountHeuristic(), 1));


        GameRunner gameRunner = new GameRunnerImpl(gameGui, playerA, playerB);
        //GameRunner gameRunner = new GameRunnerImpl(gameGui, playerA, playerB, getBoardFromConsole(playerA, playerB));
        gameRunner.run();

    }

    public static Player[][] getBoardFromConsole(Player playerA, Player playerB) {
        Scanner scan = new Scanner(System.in);
        Player[][] board = new Player[boardSize][boardSize];

        for (int x = 0; x < boardSize; x++) {
            String str = scan.nextLine();
            String[] cols = str.split(" ");
            for (int y = 0; y < boardSize; y++) {
                if(playerA.getName().equals(cols[y])) board[y][x] = playerA;
                else if(playerB.getName().equals(cols[y])) board[y][x] = playerB;
                else board[y][x] = null;
            }
        }
        return board;
    }
}