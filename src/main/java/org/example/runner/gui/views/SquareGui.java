package org.example.runner.gui.views;

import javax.swing.*;
import java.awt.*;

public class SquareGui extends JButton {
    boolean isSelected;
    Color background;
    Color circleBackground = Color.BLUE;
    Point point;

    public SquareGui(Point point) {
        this.point = point;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(final boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(final Color background) {
        super.setBackground(background);
        this.background = background;
    }

    public Point getPoint() {
        return point;
    }
}
