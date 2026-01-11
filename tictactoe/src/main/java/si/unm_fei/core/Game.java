package si.unm_fei.core;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game {

    private JFrame frame;
    Assets assets = new Assets();

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static void main(String[] args) {
        // save any uncaught exception into an err file
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            try (var w = new java.io.PrintWriter(new java.io.FileWriter("error.log", true))) {
                w.println("=== Uncaught exception on " + java.time.Instant.now() + " in " + t.getName() + " ===");
                e.printStackTrace(w);
            } catch (Exception ignored) {}
        });

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
    MainMenu mainMenu = new MainMenu(this, assets);

    public void showMainMenu() {
        frame.setContentPane(mainMenu);
        frame.revalidate();
        frame.repaint();

        mainMenu.startGameThread();
    }

    public void startNewGame() {
        GamePanel gamePanel = new GamePanel(this, mainMenu, assets);
        frame.setContentPane(gamePanel);
        frame.revalidate();
        frame.repaint();

        gamePanel.startGameThread(); // start when shown
    }

    public void showOptions() {
        Options options = new Options(this);
        frame.setContentPane(options);
        frame.revalidate();
        frame.repaint();

    }
}

