package si.unm_fei.logic;

import si.unm_fei.core.Assets;
import si.unm_fei.core.GamePanel;
import si.unm_fei.ui.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GridCells {
    private Cell[] cells = new Cell[9]; // array for all grid cells (0...8)
    private BufferedImage symbol;

    MouseHandler mouse;
    Board board;
    Assets assets;

    public GridCells(MouseHandler mouse, Board board, Assets assets) {
        Arrays.fill(cells, Cell.EMPTY); // fill all cells with empty status

        this.mouse = mouse;
        this.board = board;
        this.assets = assets;

        if(GamePanel.playerSymbol == Cell.X) {
            symbol = assets.getImage("/x_white.png");
        } else {
            symbol = assets.getImage("/o_white.png");
        }

    }

    public Cell getStatus(int i) {
        return cells[i];
    }

    public void setStatus(int i, Cell status) {
        cells[i] = status;
    }

    public Cell[] getCells() {
        return cells;
    }

    private Point getPixelCoordinates(int index) {
        int cols = board.getCols();
        int row = index / cols;
        int col = index % cols;

        int block = board.getCellSize() + board.getLineThickness();

        int cellX = col * block;
        int cellY = row * block;

        int x = board.getX() + board.getLineThickness()  + cellX;
        int y = board.getY() + board.getLineThickness()  + cellY;

        return new Point(x, y);
    }

    public void update() {
        // update cell status if clicked
        if(mouse.isPressed()) {
            int index = mouse.getLocationIndex();
            if(index == -1) return;

            setStatus(index, Cell.X);

            mouse.setPressed(false);
        }
    }

    // draw the image x/o
    public void draw(Graphics2D g2) {
        for (int i = 0; i < cells.length; i++) {
            if(cells[i] == Cell.EMPTY) continue;

            Point point = getPixelCoordinates(i);

            if(cells[i] == Cell.X) {
                //for debugging:
                //g2.setColor(Color.BLUE);
                //g2.fillRect(point.x, point.y, board.getCellSize(), board.getCellSize());

                g2.drawImage(symbol, point.x, point.y, board.getCellSize(), board.getCellSize(), null);
            }
        }
    }
}
