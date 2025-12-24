package si.unm_fei.core;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game {

    private JFrame frame;

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().start());
    }

    private void start() {
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        showMainMenu(); // start in menu

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void showMainMenu() {
        frame.setContentPane(new MainMenu(this));
        frame.revalidate();
        frame.repaint();
    }

    public void startNewGame() {
        GamePanel gamePanel = new GamePanel();
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();

        gamePanel.startGameThread(); // start when shown
    }
}

