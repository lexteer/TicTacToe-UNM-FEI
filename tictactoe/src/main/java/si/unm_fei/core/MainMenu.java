package si.unm_fei.core;

import si.unm_fei.logic.Kategorija;
import si.unm_fei.ui.Logo;

import javax.swing.*;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class MainMenu extends JPanel {
    Logo logo;
    private Timer animTimer;
    // bg animation
    private static float yLevel = SCREEN_HEIGHT/2;
    private static final float TARGET_Y = 200f;
    private static final float STEP = 10f;

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

        startGradientAnimation();
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
        JButton quit = createButton("Izhod", 260, 40);

        newGame.addActionListener(e -> game.startNewGame());
        quit.addActionListener(e -> System.exit(0));

        newGame.setCursor(handCursor);
        quit.setCursor(handCursor);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 8, 0); // tight spacing
        add(newGame, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(quit, gbc);
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
    public void startGradientAnimation() {
        if (animTimer != null && animTimer.isRunning()) animTimer.stop();

        animTimer = new Timer(16, e -> {
            if (yLevel > TARGET_Y) {
                yLevel = Math.max(TARGET_Y, yLevel - STEP);
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        animTimer.start();
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

    public static void setYLevel(float value) {
        yLevel = value;
    }

}

