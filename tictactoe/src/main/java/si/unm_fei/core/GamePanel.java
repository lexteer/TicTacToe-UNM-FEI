package si.unm_fei.core;

import si.unm_fei.logic.*;
import si.unm_fei.ui.Board;
import si.unm_fei.ui.GameOver;
import si.unm_fei.ui.Logo;
import si.unm_fei.ui.ResetGameBtn;

import javax.swing.*;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class GamePanel extends JPanel implements Runnable {

    public static Cell playerSymbol = Cell.X;
    public static Cell computerSymbol = (playerSymbol == Cell.X) ? Cell.O : Cell.X;

    public static boolean engineEnabled = true; // on, off for engine
    private final boolean playerStarts = true; // decide if player or engine starts

    // Screen settings, change from game.java
    private static final int WIDTH = SCREEN_WIDTH;
    private static final int HEIGHT = SCREEN_HEIGHT;

    // Game loop
    private Thread gameThread;
    private boolean running = false;

    // FPS
    private static final int FPS = 60;
    private static final double DRAW_INTERVAL = 1_000_000_000.0 / FPS;

    public static boolean isGameOver = false;

    // Instances
    private Board board;
    private GridCells gridCells;
    private MouseHandler mouse;
    private Assets assets;
    private Engine engine;
    private Rules rules;
    private GameOver gameOver;
    private ResetGameBtn resetButton;
    private Logo logo;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        setLayout(null);

        //instances
        initObj();

        // reset game btn
        resetButton = new ResetGameBtn(board, this);
        add(resetButton.getResetButton());
        revalidate();
        repaint();

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

        gridCells = new GridCells(mouse, board, assets, this);
        rules = new Rules(gridCells);
        engine = new Engine(gridCells, rules);
        gameOver = new GameOver(rules, board);
        logo = new Logo(assets);

        // if engine starts
        if(engineEnabled && !playerStarts) {
            engine.playEngineMove();
        }
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
        gameOver.draw(g2);
        logo.draw(g2);

        g2.dispose();
    }

    public void switchPlayer() {
        if(rules.isGameOver()) {
            isGameOver = true;
            return;
        }

        if(engineEnabled) {
            engine.playEngineMove();
        } else {
            playerSymbol = (playerSymbol == Cell.X) ? Cell.O : Cell.X;
        }
    }

    public void resetGame() {
        gridCells.resetCells();
        rules.setWinner(Cell.EMPTY);
        isGameOver = false;

        if(engineEnabled && !playerStarts) {
            engine.playEngineMove();
        }
    }
}

