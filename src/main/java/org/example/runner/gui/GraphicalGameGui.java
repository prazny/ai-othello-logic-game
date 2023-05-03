package org.example.runner.gui;

import org.example.domain.game.Board;
import org.example.domain.game.Game;
import org.example.runner.players.Player;
import org.example.runner.gui.views.BoardGui;
import org.example.runner.gui.views.GameInfoGui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GraphicalGameGui implements GameGui {
    private final GameInfoGui gameInfoGui;
    private final JFrame frame;
    private BoardGui board;
    private Game game;

    public GraphicalGameGui() {
        frame = new JFrame("Reversi");
        this.game = null;
        gameInfoGui = new GameInfoGui();
    }

    public void startGame(Game game) {
        this.game = game;
        board = new BoardGui(game.getBoardSize());
        initFrame();
    }

    @Override
    public void finishGame() {
        System.out.println("Game finished!");
        System.out.println("Liczba rund: " + game.getRoundsCount());
        Map<Player, Integer> stats = game.getStats();
        for (Map.Entry<Player, Integer> pair : stats.entrySet()) {
            System.out.format("%s - %s\n", pair.getKey().getName(), pair.getValue());
        }
    }

    public void setPlayerMove(Player player) {
        gameInfoGui.setPlayer(player);
        gameInfoGui.revalidate();
    }

    public void setBoard(Board board, List<Point> validMoves) {
        this.board.setBoard(board, validMoves);
        this.board.revalidate();

        System.out.println(game);
        System.out.println("Valid moves: ");
        for (Point move : validMoves) {
            System.out.format("(%s, %s), ", move.x, move.y);
        }
        System.out.println();
    }

    public Point getNextMove() {
        Point nextMove = this.board.getNextMove();
        System.out.println("Clicked next move: " + nextMove.toString());
        return nextMove;
    }

    private void initFrame() {
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        frame.add(gameInfoGui);
        frame.add(board);
        frame.setSize(new Dimension(600, 600));
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocation(500, 200);
        frame.pack();
        frame.setVisible(true);
    }
}
