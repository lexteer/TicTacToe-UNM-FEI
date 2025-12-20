package si.unm_fei.logic;

import si.unm_fei.core.GamePanel;

public class Engine {
    GridCells gridCells;
    Rules rules;

    public Engine(GridCells gridCells, Rules rules) {
        this.gridCells = gridCells;
        this.rules = rules;
    }

    public void playEngineMove() {
        if(!gridCells.anyCellEmpty()) return; // check if any empty cell still exists

        int engineMove = winningMove();
        // if no winning move, take blocking move
        // and if no blocking move, take random move
        if(engineMove == -1) {
            engineMove = blockMove();
            if(blockMove() == -1) {
                engineMove = randomMove();
            }
        }

        gridCells.setStatus(engineMove, GamePanel.computerSymbol);

        if(rules.isGameOver()) {
            GamePanel.isGameOver = true;
        }
    }

    // return random index from 0 to 8
    private int randomMove() {
        int move = -1;
        // reroll move if the cell is not empty
        while(move == -1 || gridCells.getStatus(move) != Cell.EMPTY) {
            move = (int)(Math.random() * 9);
        }
        return move;
    }

    private int winningMove() {
        for (int i = 0; i < 9; i++) {
            if(gridCells.getStatus(i) != Cell.EMPTY) continue; // skip non empty squares

            // try a move
            gridCells.setStatus(i, GamePanel.computerSymbol);

            // check if pc won
            if(rules.isGameOver() && rules.getWinner() == GamePanel.computerSymbol) {
                gridCells.setStatus(i, Cell.EMPTY); // undo move
                return i; // winning move
            }

            gridCells.setStatus(i, Cell.EMPTY); // undo move
        }
        return -1; // no winning move
    }

    private int blockMove() {
        for (int i = 0; i < 9; i++) {
            if(gridCells.getStatus(i) != Cell.EMPTY) continue; // skip non empty squares

            // try a move
            gridCells.setStatus(i, GamePanel.playerSymbol);

            // check if player won
            if(rules.isGameOver() && rules.getWinner() == GamePanel.playerSymbol) {
                gridCells.setStatus(i, Cell.EMPTY); // undo move
                rules.setWinner(Cell.EMPTY); // undo winner
                return i; // blocking move
            }

            gridCells.setStatus(i, Cell.EMPTY); // undo move
        }
        return -1; // no blocking move
    }
}
