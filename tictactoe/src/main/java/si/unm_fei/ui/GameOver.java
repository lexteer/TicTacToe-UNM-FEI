package si.unm_fei.ui;

import si.unm_fei.core.GamePanel;
import si.unm_fei.logic.Cell;
import si.unm_fei.logic.Rules;

import java.awt.*;

public class GameOver {
    Rules rules;
    Board board;

    public GameOver(Rules rules, Board board) {
        this.rules = rules;
        this.board = board;
    }

    public void draw(Graphics2D g2) {
        if(GamePanel.isGameOver) {
            Cell winner = rules.getWinner();
            boolean isDraw = rules.isDraw();

            if(isDraw) {
                // its a draw
                displayMessage(g2, "It's a draw!");
            } else {
                if(GamePanel.engineEnabled) {
                    if (winner == GamePanel.playerSymbol) {
                        // you won
                        displayMessage(g2, "You won!");
                    } else {
                        // you lost
                        displayMessage(g2, "You lost!");
                    }
                } else {
                    if(winner == Cell.X) {
                        // X won
                        displayMessage(g2, "X won!");
                    } else {
                        // O won
                        displayMessage(g2, "O won!");
                    }
                }
            }
        }
    }

    private void displayMessage(Graphics2D g2, String msg) {
        g2.setColor(Color.WHITE);
        Font font = new Font("SansSefif", Font.BOLD, 34);
        FontMetrics fm = g2.getFontMetrics(font);
        g2.setFont(font);

        int textWidth = fm.stringWidth(msg);

        int x = board.getX() + board.boardWidth() / 2 - textWidth / 2;
        int y = board.getY() - 40;

        g2.drawString(msg, x, y);
    }
}
