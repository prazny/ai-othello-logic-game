package org.example;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.GameRunner;
import org.example.runner.GameRunnerImpl;
import org.example.runner.gui.ConsoleGameGui;
import org.example.runner.gui.GameGui;
import org.example.runner.gui.GraphicalGameGui;
import org.example.runner.gui.NoneGameGui;
import org.example.runner.players.ComputerPlayer;
import org.example.runner.players.GuiPlayer;
import org.example.runner.players.Player;
import org.example.runner.players.algorithms.AlphaBeta;
import org.example.runner.players.algorithms.Minimax;
import org.example.runner.players.algorithms.Node;
import org.example.runner.players.heuristics.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main {
    final static int boardSize = 8;

    public static void ff(String[] args) {
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

        runGame();
        // runTest();

    }

    public static void runTest() {
        Heuristic[] heuristics = {
                new CornersHeuristic(),
                //  new RandomHeuristic(),
                new TilesCountHeuristic(),
                new MobilityHeuristic(),
                new MixedHeuristic(new ArrayList<>(List.of(new MixedHeuristic.MixedHeuristicPair[]{
                        new MixedHeuristic.MixedHeuristicPair(new CornersHeuristic(), 0.4),
                        new MixedHeuristic.MixedHeuristicPair(new EdgeHeuristic(), 0.5),
                        new MixedHeuristic.MixedHeuristicPair(new TilesCountHeuristic(), 1.0),
                        new MixedHeuristic.MixedHeuristicPair(new MobilityHeuristic(), 1.5),
                })))
        };
        GameGui gameGui = new NoneGameGui();

        Map<Heuristic, Integer> winners = new HashMap<>();
        Map<String, Integer> winnerPlayers = new HashMap<>();
        winnerPlayers.put("A", 0);
        winnerPlayers.put("B", 0);

        System.out.format("%15s", "B→");
        for (Heuristic heuristicA : heuristics) {
            System.out.format("%35s", heuristicA.getShortName());
            winners.put(heuristicA, 0);
        }
        System.out.format("\n");
        System.out.format("%15s\n", "A↓");

        for (Heuristic heuristicA : heuristics) {
            System.out.format("%15s", heuristicA.getShortName());

            for (Heuristic heuristicB : heuristics) {

                Player playerA = new ComputerPlayer("A", Color.BLACK, new AlphaBeta(heuristicA, 7));
                Player playerB = new ComputerPlayer("B", Color.WHITE, new AlphaBeta(heuristicB, 7));

                //  Player playerA = new ComputerPlayer("A", Color.BLACK, new Minimax(heuristicA, 2));
                // Player playerB = new ComputerPlayer("B", Color.WHITE, new Minimax(heuristicB, 4));

                GameRunner gameRunnerA = new GameRunnerImpl(gameGui, playerA, playerB);
                gameRunnerA.setNotice(false);
                gameRunnerA.run();

                var statsA = gameRunnerA.getStats();

                if (statsA.get(playerA) > statsA.get(playerB)) {
                    winners.put(heuristicA, winners.get(heuristicA) + 1);
                    winnerPlayers.put("A", winnerPlayers.get("A") + 1);
                } else {
                    winners.put(heuristicB, winners.get(heuristicB) + 1);
                    winnerPlayers.put("B", winnerPlayers.get("B") + 1);
                }

                var statsStr = String.format("(%s) A  %s - %s B (%s)", playerA.getDecisionTime(),
                        statsA.get(playerA),
                        statsA.get(playerB),
                        playerB.getDecisionTime()

                );
                System.out.format("%35s", statsStr);


            }
            System.out.format("\n");

        }

        System.out.format("%15s: \n", "Winners");
        for (Heuristic heuristic : heuristics) {
            System.out.format("%15s: %s", heuristic.getShortName(), winners.get(heuristic));
        }
        System.out.format("\n");
        System.out.format("A: %15s   B: %15s", winnerPlayers.get("A"), winnerPlayers.get("B"));


    }

    public static void runGame() {
        MixedHeuristic mixedHeuristicOne = new MixedHeuristic(new ArrayList<>(List.of(new MixedHeuristic.MixedHeuristicPair[]{
                new MixedHeuristic.MixedHeuristicPair(new CornersHeuristic(), 0.4),
                new MixedHeuristic.MixedHeuristicPair(new EdgeHeuristic(), 0.5),
                new MixedHeuristic.MixedHeuristicPair(new TilesCountHeuristic(), 1.0),
                new MixedHeuristic.MixedHeuristicPair(new MobilityHeuristic(), 1.5),
        })));

        GameGui gameGui = new GraphicalGameGui();
        Player playerA = new ComputerPlayer("A", Color.BLACK, new AlphaBeta(new TilesCountHeuristic(), 6));
        Player playerB = new ComputerPlayer("B", Color.WHITE, new AlphaBeta(new TilesCountHeuristic(), 6));
        //Player playerA = new GuiPlayer("A", Color.BLACK);
        //Player playerB = new GuiPlayer("B", Color.WHITE);


        GameRunner gameRunner = new GameRunnerImpl(gameGui, playerA, playerB);
        //GameRunner gameRunner = new GameRunnerImpl(gameGui, playerA, playerB, getBoardFromConsole(playerA, playerB), true);
        //gameRunner.setRoundNumber(1);

        gameRunner.run();
        System.out.println("Nodes: " + Node.getCount());
    }

    public static Player[][] getBoardFromConsole(Player playerA, Player playerB) {
        Scanner scan = new Scanner(System.in);
        Player[][] board = new Player[boardSize][boardSize];

        for (int x = 0; x < boardSize; x++) {
            String str = scan.nextLine();
            String[] cols = str.split(" ");
            for (int y = 0; y < boardSize; y++) {
                if (playerA.getName().equals(cols[y])) board[y][x] = playerA;
                else if (playerB.getName().equals(cols[y])) board[y][x] = playerB;
                else board[y][x] = null;
            }
        }
        return board;
    }
}