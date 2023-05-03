package org.example.domain.game;

import org.example.domain.game.exceptions.GameFinished;
import org.example.domain.game.exceptions.InvalidMove;
import org.example.runner.players.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Game {
    protected Player playerA;
    protected Player playerB;
    protected final int boardSize = 8;
    private boolean isPlayerAMove;
    private boolean isGameFinished = false;
    private int roundsCount = 0;
    private Map<Player, Integer> discCount;
    List<Point> validMoves;
    Board board;
    Point lastMove;


    public Game(Player playerA, Player playerB) {
        this.board = new Board(boardSize);
        this.discCount = new HashMap<>();
        this.playerA = playerA;
        this.playerB = playerB;

        board.setTile(3, 3, playerA);
        board.setTile(4, 4, playerA);
        board.setTile(3, 4, playerB);
        board.setTile(4, 3, playerB);
        isPlayerAMove = false;
        updateValidMoves();
        checkIfGameIsFinished();
        //this.currentPlayer = 1;
    }

    public Game(Player playerA, Player playerB, Player[][] board) {
        this.board = new Board(boardSize);
        this.discCount = new HashMap<>();
        this.playerA = playerA;
        this.playerB = playerB;

        this.board.setBoard(board);

        isPlayerAMove = false;

        updateValidMoves();
        checkIfGameIsFinished();
    }

    public Game(Player playerA, Player playerB, Player[][] board, boolean isPlayerAMove, Point lastMove) {
        this.board = new Board(boardSize);
        this.discCount = new HashMap<>();
        this.playerA = playerA;
        this.playerB = playerB;

        this.board.setBoard(board);

        this.isPlayerAMove = isPlayerAMove;
        this.lastMove = lastMove;
        updateValidMoves();
        checkIfGameIsFinished();
    }

    public Game getGameCopy() {
        Player[][] newBoard = new Player[boardSize][boardSize];
        IntStream.range(0, 8).forEachOrdered(row -> {
            IntStream.range(0, 8).forEachOrdered(col -> {
                newBoard[col][row] = board.getValue(col, row);
            });
        });

        return new Game(playerA, playerB, newBoard, isPlayerAMove, lastMove);
    }

    public List<Point> getValidMoves() {
        return validMoves;
    }

    private void updateValidMoves() {
        List<Point> moves = new ArrayList<>();

        IntStream.range(0, 8).forEachOrdered(row -> {
            IntStream.range(0, 8).forEachOrdered(col -> {
                if (isValidMove(new Point(col, row)))
                    moves.add(new Point(col, row));
            });
        });
        this.validMoves = moves;
    }

    private boolean isValidMove(Point point) {
        Player val = board.getValue(point);
        if (val != null) return false;

        for (int d_row = -1; d_row <= 1; d_row++) {
            for (int d_col = -1; d_col <= 1; d_col++) {
                if (d_row == 0 && d_col == 0)
                    continue;

                if (isValidDirection(point.x, point.y, d_col, d_row))
                    return true;
            }
        }


        return false;
    }


    private boolean isValidDirection(int col, int row, int d_col, int d_row) {
        // int opponent = 3 - currentPlayer;
        Player opponent = getOpponent();
        int r = row + d_row;
        int c = col + d_col;

        if (!board.isOnBoard(c, r) || board.getValue(c, r) != opponent)
            return false;

        while (board.isOnBoard(c, r)) {
            if (board.getValue(c, r) == null)
                return false;

            if (board.getValue(c, r) == getCurrentPlayer()) {
                return true;
            }

            r += d_row;
            c += d_col;
        }
        return false;
    }

    private void flipDirections(int col, int row, int d_col, int d_row) {
        int r = row + d_row;
        int c = col + d_col;
        while (board.getValue(c, r) != getCurrentPlayer()) {
            board.setTile(c, r, getCurrentPlayer());
            r += d_row;
            c += d_col;
        }
    }

    public void makeMove(Point move) throws InvalidMove, GameFinished {
        if (isGameFinished) throw new GameFinished();
        if (!isValidMove(move)) throw new InvalidMove();


        board.setTile(move.x, move.y, getCurrentPlayer());
        lastMove = move;

        for (int d_row = -1; d_row <= 2; d_row++) {
            for (int d_col = -1; d_col <= 2; d_col++) {
                if (d_row == 0 && d_col == 0)
                    continue;

                if (isValidDirection(move.x, move.y, d_col, d_row)) {
                    //System.out.println("flip " + move.x + " "+ d_col + " -> " + move.y + " "+ d_row);
                    flipDirections(move.x, move.y, d_col, d_row);
                }

            }
        }
        isPlayerAMove = !isPlayerAMove;
        roundsCount++;

        updateValidMoves();
        checkIfGameIsFinished();
    }

    public void skipPlayer() throws GameFinished {
        if (isGameFinished) throw new GameFinished();
        isPlayerAMove = !isPlayerAMove;
        roundsCount++;

        updateValidMoves();
        checkIfGameIsFinished();
    }


    private void checkIfGameIsFinished() {
        if (isGameFinished) return;

        for (int i = 0; true; i++) {
            if (validMoves.size() > 0)
                return;

            isPlayerAMove = !isPlayerAMove;
            updateValidMoves();

            if (i == 2) {
                finishGame();
                return;
            }
        }

    }

    private void finishGame() {
        this.isGameFinished = true;
        //System.out.println("No valid moves");

        calculateStats();
    }

    private void calculateStats() {
        discCount = new HashMap<>();
        discCount.put(playerA, 0);
        discCount.put(playerB, 0);

        IntStream.range(0, 8).forEachOrdered(row -> {
            IntStream.range(0, 8).forEachOrdered(col -> {
                Player player = board.getValue(row, col);
                if (player != null) {
                    discCount.put(player, discCount.get(player) + 1);
                }
            });
        });
    }

    public Board getBoard() {
        return board;
    }

    public int getBoardSize() {
        return boardSize;
    }

    private Player getOpponent() {
        if (isPlayerAMove) return playerB;
        else return playerA;
    }

    public Player getCurrentPlayer() {
        if (isPlayerAMove) return playerA;
        else return playerB;
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public int getRoundsCount() {
        return roundsCount;
    }

    public Point getLastMove() {
        return lastMove;
    }

    public Map<Player, Integer> getStats() {
        if (!isGameFinished) calculateStats();
        return discCount;
    }

    public String toString() {
        return board.toString();
    }
}
