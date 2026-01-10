package si.unm_fei.logic;

import si.unm_fei.core.Assets;
import si.unm_fei.core.GamePanel;
import si.unm_fei.ui.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class GridCells {
    private Cell[] cells = new Cell[9]; // array for all grid cells (0...8)
    private BufferedImage Xsymbol, Osymbol;

    MouseHandler mouse;
    Board board;
    GamePanel gp;
    public boolean hover = false;
    public int hoverIndex = -1;
    public int currentIndex = -1;

    public GridCells(MouseHandler mouse, Board board, Assets assets, GamePanel gp) {
        Arrays.fill(cells, Cell.EMPTY); // fill all cells with empty status

        this.mouse = mouse;
        this.board = board;
        this.gp = gp;

        Xsymbol = assets.getImage("/x_black.png");
        Osymbol = assets.getImage("/o_black.png");

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

    public void resetCells() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = Cell.EMPTY;
        }
    }

    public boolean anyCellEmpty() {
        for(Cell cell : cells) {
            if(cell == Cell.EMPTY) return true;
        }
        return false;
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
        if(mouse.isPressed() && !GamePanel.isGameOver) {
            int index = mouse.getLocationIndex();
            currentIndex = index;
            if(index == -1 || getStatus(index) != Cell.EMPTY) return;

            gp.showQuestionAndGetResult();

            //premaknjeno v questionPopUp.java(when correct) za hitrejse risanje simbola
            //setStatus(index, GamePanel.playerSymbol);
            gp.switchPlayer();
            mouse.setPressed(false);
        }
    }

    // draw the image x/o
    public void draw(Graphics2D g2) {
        for (int i = 0; i < cells.length; i++) {
            if(cells[i] == Cell.EMPTY) continue;

            Point point = getPixelCoordinates(i);

            if(cells[i] == Cell.X) {
                g2.drawImage(Xsymbol, point.x, point.y, board.getCellSize(), board.getCellSize(), null);
            } else {
                g2.drawImage(Osymbol, point.x, point.y, board.getCellSize(), board.getCellSize(), null);
            }
        }
        highlightHover(g2, hoverIndex);
    }

    public void highlightHover(Graphics2D g2, int index) {
        if(hover) {
            Point point = getPixelCoordinates(index);
            g2.setColor(new Color(240, 240, 240));
            g2.fillRect(point.x, point.y, board.getCellSize(), board.getCellSize());
        }
    }
}
