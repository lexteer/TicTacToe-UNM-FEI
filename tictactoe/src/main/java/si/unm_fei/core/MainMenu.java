package si.unm_fei.core;

import si.unm_fei.logic.Kategorija;
import si.unm_fei.ui.Logo;

import javax.swing.*;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class MainMenu extends JPanel implements Runnable {
    // Game loop
    private Thread gameThread;
    private boolean running = false;

    // FPS
    private static final int FPS = 60;
    private static final double DRAW_INTERVAL = 1_000_000_000.0 / FPS;

    Logo logo;
    private Timer animTimer;
    // bg animation
    private float yLevel;

    private static Kategorija selectedKategorija = GamePanel.oldCategory;
    Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    public static Kategorija getKategorija() {
        return selectedKategorija;
    }

    public static void setKategorija(Kategorija value) {
        selectedKategorija = value;
    }

    public MainMenu(Game game, Assets assets) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        //setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        yLevel = SCREEN_HEIGHT/2;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        if(selectedKategorija == null) {
            selectedKategorija = Kategorija.UPRAVLJANJE;
        }

        addTitle(gbc);
        JComboBox<String> box = addCategoryBox(gbc);
        addButtons(gbc, game);

        String selectedLabel = (String) box.getSelectedItem();
        selectedKategorija = Kategorija.fromString(selectedLabel);

        logo = new Logo(assets);

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

            float yEnd = getHeight() / 2f;
            if (yLevel > yEnd) {
                yLevel = Math.max(yEnd, yLevel - 5f);
            }

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

    private void addTitle(GridBagConstraints gbc) {
        JLabel title = new JLabel("Križec Krožec Kviz");
        Font titleFont = new Font("SansSerif", Font.BOLD, 52);
        title.setFont(titleFont);
        title.setForeground(new Color(45, 45, 45));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0); // space under title
        add(title, gbc);
    }

    private JComboBox<String> addCategoryBox(GridBagConstraints gbc) {
        JComboBox<String> kategorijaBox = new JComboBox<>();
        kategorijaBox.setCursor(handCursor);

        // fill combo box
        for(Kategorija k : Kategorija.values()) {
            kategorijaBox.addItem(k.getFullName());
        }

        kategorijaBox.setSelectedItem(selectedKategorija.getFullName());

        kategorijaBox.setPreferredSize(new Dimension(260, 28));
        kategorijaBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        kategorijaBox.setBackground(Color.WHITE);

        kategorijaBox.addActionListener(e -> {
            String selectedLabel = (String) kategorijaBox.getSelectedItem();
            selectedKategorija = Kategorija.fromString(selectedLabel);
        });

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(kategorijaBox, gbc);

        return kategorijaBox;
    }

    private void addButtons(GridBagConstraints gbc, Game game) {
        JButton newGame = createButton("Začni", 260, 40);
        JButton options = createButton("Nastavitve", 260, 40);
        JButton quit = createButton("Izhod", 260, 40);

        newGame.addActionListener(e -> {
            stopGameThread();
            game.startNewGame();
        });

        options.addActionListener(e -> {
            stopGameThread();
            game.showOptions();
        });

        quit.addActionListener(e -> System.exit(0));

        newGame.setCursor(handCursor);
        options.setCursor(handCursor);
        quit.setCursor(handCursor);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0); // tight spacing
        add(newGame, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 8, 0);
        add(options, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(quit, gbc);
    }

    public synchronized void stopGameThread() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
            gameThread = null;
        }
    }

    private JButton createButton(String text, int w, int h) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(w, h));
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(new Color(90, 200, 140));
        b.setForeground(Color.WHITE);
        return b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        try {
            drawGradient(g2);

            logo.draw(g2);
        } finally {
            g2.dispose();
        }
    }

    private void drawGradient(Graphics2D g2) {
        g2.setPaint(new GradientPaint(
                0, yLevel, new Color(245, 248, 252),
                0, getHeight(), new Color(188, 215, 173)
        ));
        g2.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setYLevel(float value) {
        yLevel = value;
    }

    @Override
    public void removeNotify() {
        stopGameThread();
        super.removeNotify();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        startGameThread();
    }


}

