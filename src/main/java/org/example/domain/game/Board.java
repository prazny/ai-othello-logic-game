package org.example.domain.game;

import org.example.runner.players.Player;

import java.awt.*;

public class Board {
    private Player[][] board;
    private final int boardSize;
    public Board(int boardSize) {
        board = new Player[boardSize][boardSize];
        this.boardSize = boardSize;
    }

    public void setTile(int x, int y, Player val) {
        board[x][y] = val;
    }
    public void setTile(Point point, Player val) {
        board[point.x][point.y] = val;
    }

    public Player[][] getBoard() {
        return board;
    }
    public void setBoard(Player[][] board) {
        this.board = board;
    }

    public Player getValue(int x, int y) {
        return board[x][y];
    }

    public Player getValue(Point point) {
        return board[point.x][point.y];
    }

    public boolean isOnBoard(int x, int y) {
        return 0 <= x && x < boardSize && 0 <= y && y < boardSize;
    }

    @Override
    public String toString() {
        StringBuilder boardStr = new StringBuilder();

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {

                if (getValue(col, row) == null) boardStr.append(0);
                else boardStr.append(getValue(col, row));

                boardStr.append(" ");
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }
}
