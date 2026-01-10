package si.unm_fei.logic;

import si.unm_fei.core.GamePanel;
import si.unm_fei.ui.Board;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    private int x, y;
    private boolean pressed = false;

    private static final Cursor HAND = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    private static final Cursor DEFAULT = Cursor.getDefaultCursor();

    private Cursor currentCursor = DEFAULT;

    private Board board;
    private GamePanel gp;
    private GridCells gs;


    public MouseHandler(GamePanel gp, Board board) {
        this.gp = gp;
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

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        this.gs = gp.gridCells;

        int index = getLocationIndex();
        if(index != -1 && gs.getStatus(index) == Cell.EMPTY) {
            setForcedCursor(HAND);
            gs.hover = true;
            gs.hoverIndex = index;
        } else {
            setForcedCursor(DEFAULT);
            gs.hover = false;
        }
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

    private void setForcedCursor(Cursor cursor) {
        if (currentCursor == cursor) return;
        currentCursor = cursor;
        gp.setCursor(cursor);
    }
}
