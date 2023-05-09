package org.example.runner;

import org.example.domain.game.Game;
import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.gui.GameGui;
import org.example.runner.players.Player;


import java.awt.*;
import java.util.Map;

public class GameRunnerImpl implements org.example.runner.GameRunner {
    private final GameGui gameGui;
    private final Game game;
    private long time;
    boolean notice = true;
    int roundNumber = Integer.MAX_VALUE;

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

    public void setNotice(boolean notice) {
        this.notice = notice;
    }
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }


    @Override
    public void run() {
        this.gameGui.startGame(this.game);

        time = System.currentTimeMillis();
        while (true) {
            gameGui.setPlayerMove(game.getCurrentPlayer());
            gameGui.setBoard(game.getBoard(), game.getValidMoves());

            //System.out.println("Valid moves: " + getValidMoves());
            while (true) {
                if(roundNumber <= 0){
                    finishGame();
                    return;
                }
                if (game.isGameFinished()) {
                    finishGame();
                    return;
                }

                try {
                    Point nextMove = game.getCurrentPlayer().getNextMove(game, gameGui);
                    if (notice)
                        System.out.println("Player trying: " + nextMove);
                    game.makeMove(nextMove);
                    roundNumber--;
                    break;
                } catch (InvalidMove invaildMove) {
                    System.out.println("Invaild move: ");
                } catch (GameFinished e) {
                    finishGame();
                    return;
                }
            }


        }
    }

    private void finishGame() {
        time = System.currentTimeMillis() - time;
        this.gameGui.finishGame();
    }

    public Map<Player, Integer> getStats() {
        return game.getStats();
    }

    @Override
    public long getTime() {
        return time;
    }


}
