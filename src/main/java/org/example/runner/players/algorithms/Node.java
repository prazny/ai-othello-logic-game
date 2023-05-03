package org.example.runner.players.algorithms;

import java.awt.*;

public class Node {
    private final Point move;
    private final int score;
    public Node(Point move, int score) {
        this.move = move;
        this.score = score;
    }

    public Point getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }
}
