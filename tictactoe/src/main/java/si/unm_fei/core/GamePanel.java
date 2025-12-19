package si.unm_fei.core;

import si.unm_fei.logic.Cell;
import si.unm_fei.logic.GridCells;
import si.unm_fei.logic.MouseHandler;
import si.unm_fei.ui.Board;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static Cell playerSymbol = Cell.X;
    public static Cell computerSymbol = (playerSymbol == Cell.X) ? Cell.O : Cell.X;

    // Screen settings
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // Game loop
    private Thread gameThread;
    private boolean running = false;

    // FPS
    private static final int FPS = 60;
    private static final double DRAW_INTERVAL = 1_000_000_000.0 / FPS;

    // Instances
    private Board board;
    private GridCells gridCells;
    private MouseHandler mouse;
    private Assets assets;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        //instances
        initObj();
    }

    public void startGameThread() {
        if (gameThread == null) {
            running = true;
            gameThread = new Thread(this, "GameThread");
            gameThread.start();
        }
    }

    @Override
    public void run() {
        double nextDrawTime = System.nanoTime() + DRAW_INTERVAL;

        while (running) {

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1_000_000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += DRAW_INTERVAL;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Instantiate game objects here
    private void initObj() {
        assets = new Assets();
        board = new Board(WIDTH, HEIGHT);

        mouse = new MouseHandler(board);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        gridCells = new GridCells(mouse, board, assets);
    }

    // game updates here (called 60 times per second)
    private void update() {
        gridCells.update();
    }

    // game screen drawing (redraw 60 times per second)
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        board.draw(g2); // draw the board
        gridCells.draw(g2);

        g2.dispose();
    }
}

