package org.example.runner.players;

import java.awt.*;

abstract class PlayerAbstract implements Player{
    String name;
    Color color;

    public PlayerAbstract(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getColorName() {
        if(getColor() == Color.BLACK) return "Black";
        if(getColor() == Color.WHITE) return "White";
        return null;
    }
}
