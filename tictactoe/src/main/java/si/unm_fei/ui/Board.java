package si.unm_fei.ui;

import java.awt.*;
import java.awt.geom.Line2D;

public class Board {
    private final int rows = 3;
    private final int cols = 3;

    private int x = 100; // top left of board
    private int y = 100; // top left of board

    private final int cellSize = 100;
    private final int lineThickness = 5;

    private final int screenWidth, screenHeight;

    public Board(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        centerTheBoard(); // change x and y to center the board
    }

    private void centerTheBoard() {
        int middleOfScreenX = screenWidth / 2;
        int middleOfScreenY = screenHeight / 2;

        int halfBoardX = boardWidth() / 2;
        int halfBoardY = boardHeight() / 2;

        x = middleOfScreenX - halfBoardX;
        y = middleOfScreenY - halfBoardY;
    }

    private int boardWidth() {
        return cols * cellSize;
    }

    private int boardHeight() {
        return rows * cellSize;
    }

    public void draw(Graphics2D g2) {
        // Change the stroke
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(
                lineThickness,
                BasicStroke.CAP_ROUND, // rounded
                BasicStroke.JOIN_ROUND // connect rounded
        ));

        // Vertical grid lines
        for (int i = 1; i < cols; i++) {
            double xStartPoint = x + i * cellSize;
            g2.draw(new Line2D.Double(
                    xStartPoint, // start point of line
                    y,
                    xStartPoint, // end point of line
                    y + boardHeight()
            ));
        }

        // Horizontal grid lines
        for (int i = 1; i < rows; i++) {
            double yStartPoint = y + i * cellSize;
            g2.draw(new Line2D.Double(
                    x, // start point of line
                    yStartPoint,
                    x + boardWidth(), // end point of line
                    yStartPoint
            ));
        }

        // outer border
        g2.drawRect(x, y, boardWidth(), boardHeight());
    }
}
