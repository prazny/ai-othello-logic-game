package org.example.runner.players.algorithms;

import java.awt.*;

public class Node {
    private final Point move;
    private final double score;
    public Node(Point move, double score) {
        this.move = move;
        this.score = score;
    }

    public Point getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }
}
