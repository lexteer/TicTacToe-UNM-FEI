package si.unm_fei.core;

import si.unm_fei.logic.Kategorija;

import javax.swing.*;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class MainMenu extends JPanel {

    private static Kategorija selectedKategorija = Kategorija.UPRAVLJANJE;

    public static Kategorija getKategorija() {
        return selectedKategorija;
    }

    public MainMenu(Game game) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        //setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        addTitle(gbc);
        JComboBox<String> box = addCategoryBox(gbc);
        addButtons(gbc, game);

        String selectedLabel = (String) box.getSelectedItem();
        selectedKategorija = Kategorija.fromString(selectedLabel);

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

        // fill combo box
        for(Kategorija k : Kategorija.values()) {
            kategorijaBox.addItem(k.getFullName());
        }

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
        JButton newGame = createButton("New Game", 260, 40);
        JButton quit = createButton("Quit", 260, 40);

        newGame.addActionListener(e -> game.startNewGame());
        quit.addActionListener(e -> System.exit(0));

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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(new GradientPaint(
                0, 0, new Color(245, 248, 252),
                0, getHeight(), new Color(220, 228, 238)
        ));
        g2.fillRect(0, 0, getWidth(), getHeight());

    }




}

