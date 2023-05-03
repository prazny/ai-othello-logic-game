package org.example.runner;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.gui.GameGui;
import org.example.runner.players.Player;


import java.awt.*;

public class GameRunnerImpl implements org.example.runner.GameRunner {
    private final GameGui gameGui;
    private final Game game;

    public GameRunnerImpl(GameGui gameGui, Player playerA, Player playerB) {
        game = new Game(playerA, playerB);
        this.gameGui = gameGui;
    }

    public GameRunnerImpl(GameGui gameGui, Player playerA, Player playerB, Player[][] board) {
        game = new Game(playerA, playerB, board, true, null);
        this.gameGui = gameGui;
    }

    public GameRunnerImpl(GameGui gameGui, Player playerA, Player playerB, Player[][] board, boolean isPlayerAMove) {
        game = new Game(playerA, playerB, board, isPlayerAMove, null);
        this.gameGui = gameGui;
    }


    @Override
    public void run() {
        this.gameGui.startGame(this.game);

        while (true) {
            gameGui.setPlayerMove(game.getCurrentPlayer());
            gameGui.setBoard(game.getBoard(), game.getValidMoves());

            //System.out.println("Valid moves: " + getValidMoves());
            while(true) {
                if(game.isGameFinished()) {
                    finishGame();
                    return;
                }


                try {
                    Point nextMove = game.getCurrentPlayer().getNextMove(game, gameGui);
                    System.out.println("Player trying: " + nextMove);
                    game.makeMove(nextMove);
                    break;
                } catch (InvalidMove invaildMove) {
                    System.out.println("Invaild move: " );
                } catch (GameFinished e) {
                    finishGame();
                }
            }

        }
    }

    private void finishGame() {
        this.gameGui.finishGame();
    }


}
