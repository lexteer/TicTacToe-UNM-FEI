package si.unm_fei.core;

import si.unm_fei.logic.*;
import si.unm_fei.ui.*;

import javax.swing.*;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class GamePanel extends JPanel implements Runnable {

    private Game game;

    public static Cell startingSymbol = Cell.X;
    public static Cell playerSymbol;
    public static Cell computerSymbol = (playerSymbol == Cell.X) ? Cell.O : Cell.X;

    public static boolean engineEnabled = true; // on, off for engine
    public static boolean playerStarts = true; // decide if player or engine starts

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

    // save selected category
    public static Kategorija oldCategory;

    // bg animation
    private float yLevel = SCREEN_HEIGHT/2;

    // Instances
    private MainMenu mm;
    private Board board;
    public GridCells gridCells;
    private MouseHandler mouse;
    private Assets assets;
    private Engine engine;
    private Rules rules;
    private GameOver gameOver;
    private ResetGameBtn resetButton;
    private Logo logo;
    private QuestionManager questionManager;

    public GamePanel(Game game, MainMenu mm, Assets assets) {
        this.game=game;
        this.mm = mm;
        this.assets = assets;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        setLayout(null);

        //instances
        initObj();

        playerSymbol = startingSymbol;

        oldCategory = MainMenu.getKategorija();

        // reset game btn
        resetButton = new ResetGameBtn(board, this);
        add(resetButton.getResetButton());
        revalidate();
        repaint();

    }

    public synchronized void startGameThread() {
        if (running) return;

        running = true;
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }


    @Override
    public void run() {
        double nextDrawTime = System.nanoTime() + DRAW_INTERVAL;

        while (running && !Thread.currentThread().isInterrupted()) {

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
        board = new Board(WIDTH, HEIGHT);

        mouse = new MouseHandler(this, board);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        gridCells = new GridCells(mouse, board, assets, this);
        rules = new Rules(gridCells);
        engine = new Engine(gridCells, rules);
        gameOver = new GameOver(rules, board);
        logo = new Logo(assets);
        questionManager = new QuestionManager(MainMenu.getKategorija());

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

        try {
            drawGradient(g2);

            board.draw(g2); // draw the board
            gridCells.draw(g2);
            gameOver.draw(g2);
            logo.draw(g2);

        } finally {
            g2.dispose();
        }
    }

    private void drawGradient(Graphics2D g2) {
        float yEnd = 520;

        if(yLevel < yEnd) {
            yLevel += 10f;
        }

        g2.setPaint(new GradientPaint(
                0, yLevel, new Color(245, 248, 252),
                0, getHeight(), new Color(188, 215, 173)
        ));

        g2.fillRect(0, 0, getWidth(), getHeight());


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

    public boolean showQuestionAndGetResult() {
        Question q = questionManager.getRandomQuestion();

        if (q == null) {
            JOptionPane.showMessageDialog(this, "Zmanjkalo je vprašanj!");
            return false;
        }

        // Pridobi JFrame parent
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        QuestionPopUp popup = new QuestionPopUp(parentFrame, q, gridCells);

        return popup.showAndGetResult();
    }

    public void returnToMainMenu() {
        stopGameThread();

        removeMouseListener(mouse);
        removeMouseMotionListener(mouse);

        mm.setYLevel(yLevel);
        game.showMainMenu();

        MainMenu.setKategorija(oldCategory);
    }

    public void resetGame() {
        gridCells.resetCells();
        rules.setWinner(Cell.EMPTY);
        isGameOver = false;

        if(engineEnabled && !playerStarts) {
            engine.playEngineMove();
        }

        returnToMainMenu();
    }

    public synchronized void stopGameThread() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }
    }


}

