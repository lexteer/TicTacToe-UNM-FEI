package si.unm_fei.ui;

import si.unm_fei.core.GamePanel;

import javax.swing.*;
import java.awt.*;

public class ResetGameBtn{
    private final JButton resetButton;

    public ResetGameBtn(Board board, GamePanel gp) {

        resetButton = new JButton("Nova igra");
        resetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        int btnWidth = 100;
        resetButton.setBounds(board.getX() + board.boardWidth() / 2 - btnWidth / 2,
                board.getY() + board.boardHeight() + 30,
                btnWidth, 30);

        resetButton.setFocusPainted(false);

        resetButton.addActionListener(e -> {
            gp.resetGame();
            gp.repaint();
        });
    }
    public JButton getResetButton() {
        return resetButton;
    }
}
