package org.example.runner.players.algorithms;

import java.awt.*;

public class Node {
    private final Point move;
    private final double score;
    static int count = 0;
    public Node(Point move, double score) {
        this.move = move;
        this.score = score;
        count++;

    }

    public Point getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }

    public static int getCount() {
        return count;
    }
}
