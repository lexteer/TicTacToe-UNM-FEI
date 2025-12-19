package si.unm_fei.logic;

import si.unm_fei.ui.Board;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private int x, y;
    private boolean pressed = false;

    private Board board;

    public MouseHandler(Board board) {
        this.board = board;
    }

    public void setPressed(boolean status) {
        pressed = status;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        pressed = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPressed() {
        return pressed;
    }

    // get the location of the mouse converted into grid cell index (0...8)
    // if returns -1, mouse isnt on any valid grid cell
    public int getLocationIndex() {

        // calculate mouse x,y relative to the board
        int localX = x - board.getX();
        int localY = y - board.getY();

        // mouse outside the board:
        if(localX < 0 || localY < 0 ||
                localX >= board.boardWidth() || localY >= board.boardHeight()) {
            return -1;
        }

        int blockW = board.getCellSize() + board.getLineThickness();

        int col = localX / blockW;
        int row = localY / blockW;

        // safety check
        if(col < 0 || col >= board.getCols() ||
            row < 0 || row >= board.getRows()) {
            return -1;
        }

        return row * board.getCols() + col;
    }
}
