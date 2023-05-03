package org.example.runner.gui.views;

import org.example.domain.game.Board;
import org.example.runner.players.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

public class BoardGui extends JPanel {
    SquareGui[][] squares;
    SquareGui clicked = null;

    private final int boardSize;


    public BoardGui(int boardSize) {
        this.boardSize = boardSize;
        this.squares = new SquareGui[boardSize][boardSize];
        init();
    }

    public void setBoard(Board board, List<Point> validMoves) {
        Player[][] boardPlayers = board.getBoard();
        for (int row = 0; row < boardPlayers.length; row++) {
            for (int col = 0; col < boardPlayers.length; col++) {
                if (boardPlayers[col][row] == null)
                    squares[col][row].setBackground(Color.DARK_GRAY);
                else
                    squares[col][row].setBackground(boardPlayers[col][row].getColor());
            }
        }

        for (Point validMove : validMoves) {
            squares[validMove.x][validMove.y].setBackground(Color.GRAY);
        }

    }

    public Point getNextMove() {
        clicked = null;
        while (clicked == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Int");
            }
        }

        return clicked.getPoint();
    }

    private void init() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                SquareGui square = new SquareGui(new Point(col, row));

                square.addActionListener(e -> {
                    clicked = (SquareGui) e.getSource();
                });

                squares[col][row] = square;
            }
        }

        setMaximumSize(new Dimension(400, 400));
        setMinimumSize(new Dimension(400, 400));
        setPreferredSize(new Dimension(400, 400));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLocation(0, 10);
    }

    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        int width = getWidth();
        int height = getHeight();

        IntStream.range(0, squares.length).forEachOrdered(row -> {
            IntStream.range(0, squares.length).forEachOrdered(col -> {
                SquareGui currentSquare = squares[row][col];
                int startX = row * width / squares.length;
                int startY = col * height / squares.length;


                currentSquare.setBounds(startX,
                        startY,
                        width / squares.length,
                        height / squares.length);


                this.add(currentSquare);

               /* graphics.setColor(currentSquare.getCircleBackground());
                graphics.fillRoundRect(startX + 10,
                        startY + 10,
                        width / squares.length - 20,
                        height / squares.length - 20,
                        50, 50);*/


               /* SquareGui currentSquare = squares[row][col];



                graphics.setColor(currentSquare.getBackground());
                graphics.fillRect(startX,
                        startY,
                        width / squares.length,
                        height / squares.length);

                graphics.setColor(currentSquare.getCircleBackground());
                graphics.fillRoundRect(startX + 10,
                        startY + 10,
                        width / squares.length - 20,
                        height / squares.length - 20,
                        50, 50);*/
            });
        });
    }
}
