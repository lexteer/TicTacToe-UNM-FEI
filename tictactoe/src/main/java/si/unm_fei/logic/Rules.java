package si.unm_fei.logic;

public class Rules {
    private GridCells gridCells;
    private Cell winner = Cell.EMPTY;

    public Rules(GridCells gridCells) {
        this.gridCells = gridCells;
    }

    private static final int[][] WINNING_LINES = {
            // horizontal
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},

            // vertical
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},

            // diagonal
            {0, 4, 8},
            {2, 4, 6}
    };

    public Cell getWinner() {
        return winner;
    }

    public void setWinner(Cell status) {
        winner = status;
    }

    public boolean isGameOver() {
        Cell[] cells = gridCells.getCells();

        for (int[] line : WINNING_LINES) {
            Cell a = cells[line[0]];
            Cell b = cells[line[1]];
            Cell c = cells[line[2]];

            if (a != Cell.EMPTY && a == b && b == c) {
                winner = a;
                return true;
            }
        }

        return !gridCells.anyCellEmpty(); // if its draw
    }

    public boolean isDraw() {
        if(!isGameOver()) return false;

        if(!gridCells.anyCellEmpty() && winner == Cell.EMPTY) {
            return true;
        }

        return false;
    }

}
