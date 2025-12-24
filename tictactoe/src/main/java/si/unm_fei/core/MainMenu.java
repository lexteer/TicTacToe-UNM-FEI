package si.unm_fei.core;

import si.unm_fei.logic.Kategorija;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static si.unm_fei.core.Game.SCREEN_HEIGHT;
import static si.unm_fei.core.Game.SCREEN_WIDTH;

public class MainMenu extends JPanel {

    private static Kategorija selectedKategorija = Kategorija.RAČUNALNIŠTVO;

    public static Kategorija getKategorija() {
        return selectedKategorija;
    }

    public MainMenu(Game game) {
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        addTitle(gbc);
        JComboBox<Kategorija> box = addCategoryBox(gbc);
        addButtons(gbc, game);

        // keep your static in sync even if user never changes selection
        selectedKategorija = (Kategorija) box.getSelectedItem();
    }
    private void addTitle(GridBagConstraints gbc) {
        JLabel title = new JLabel("Tic Tac Toe Kviz");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 50f));

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 25, 0); // space under title
        add(title, gbc);
    }

    private JComboBox<Kategorija> addCategoryBox(GridBagConstraints gbc) {
        JComboBox<Kategorija> kategorijaBox = new JComboBox<>(Kategorija.values());
        kategorijaBox.setPreferredSize(new Dimension(260, 28));

        kategorijaBox.addActionListener(e ->
                selectedKategorija = (Kategorija) kategorijaBox.getSelectedItem()
        );

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
        return b;
    }

}

