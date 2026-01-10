package si.unm_fei.ui;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

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

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getLineThickness() {
        return lineThickness;
    }

    public int boardWidth() {
        return cols * cellSize + (cols + 1) * lineThickness;
    }

    public int boardHeight() {
        return rows * cellSize + (rows + 1) * lineThickness;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(60, 60, 60));


        g2.setStroke(new BasicStroke(
                lineThickness,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER
        ));

        int step = cellSize + lineThickness;
        double half = lineThickness / 2.0;

        // border
        g2.draw(new Rectangle2D.Double(
                x + half,
                y + half,
                boardWidth() - lineThickness,
                boardHeight() - lineThickness
        ));

        // vertical inner lines
        for (int i = 1; i < cols; i++) {
            double lx = x + i * step + half;
            g2.draw(new Line2D.Double(
                    lx, y,
                    lx, y + boardHeight()
            ));
        }

        // horizontal inner lines
        for (int i = 1; i < rows; i++) {
            double ly = y + i * step + half;
            g2.draw(new Line2D.Double(
                    x, ly,
                    x + boardWidth(), ly
            ));
        }
    }


}
