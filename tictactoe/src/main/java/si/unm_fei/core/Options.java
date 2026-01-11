package si.unm_fei.core;

import si.unm_fei.logic.Cell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class Options extends JPanel {
    private final JToggleButton engineToggle = new JToggleButton("ON");
    private final JToggleButton playerStartsToggle = new JToggleButton("ON");
    private final JComboBox<String> symbolCombo = new JComboBox<>(new String[]{"X", "O"});
    private final JButton backButton = new JButton("Nazaj");

    Cursor handCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

    Game game;

    public Options(Game game) {
        this.game = game;
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        JPanel card = buildCenteredCard();
        add(card, new GridBagConstraints());

        applyStateToUI();
        wireListeners();

        engineToggle.setCursor(handCursor);
        playerStartsToggle.setCursor(handCursor);
        symbolCombo.setCursor(handCursor);
        backButton.setCursor(handCursor);

        engineToggle.setFocusPainted(false);
        playerStartsToggle.setFocusPainted(false);
        backButton.setFocusPainted(false);
    }

    private JPanel buildCenteredCard() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(24, 28, 24, 28));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Nastavitve");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 52f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(18));

        card.add(buildRow("AI nasprotnik", engineToggle));
        card.add(Box.createVerticalStrut(12));

        card.add(buildRow("Kdo začne igro", playerStartsToggle));
        card.add(Box.createVerticalStrut(12));

        card.add(buildRow("Začetni simbol", symbolCombo));
        card.add(Box.createVerticalStrut(24));

        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 36));

        card.add(backButton);

        Dimension toggleSize = new Dimension(110, 32);
        engineToggle.setPreferredSize(toggleSize);
        playerStartsToggle.setPreferredSize(toggleSize);
        symbolCombo.setPreferredSize(new Dimension(110, 32));

        return card;
    }

    private JPanel buildRow(String labelText, JComponent control) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(label.getFont().deriveFont(15f));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0, 0, 0, 16);
        row.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.LINE_END;
        row.add(control, gbc);

        row.setMaximumSize(new Dimension(340, 40));
        return row;
    }

    private void wireListeners() {
        engineToggle.addActionListener(e -> {
            GamePanel.engineEnabled = engineToggle.isSelected();
            applyStateToUI();
        });

        playerStartsToggle.addActionListener(e -> {
            GamePanel.playerStarts = playerStartsToggle.isSelected();
            applyStateToUI();
        });

        symbolCombo.addActionListener(e -> {
            String selected = Objects.toString(symbolCombo.getSelectedItem(), "X");
            GamePanel.startingSymbol = "O".equals(selected) ? Cell.O : Cell.X;
            GamePanel.computerSymbol = (GamePanel.startingSymbol == Cell.X) ? Cell.O : Cell.X;
        });

        backButton.addActionListener(e -> game.showMainMenu());
    }

    private void applyStateToUI() {
        engineToggle.setSelected(GamePanel.engineEnabled);
        engineToggle.setText(GamePanel.engineEnabled ? "ON" : "OFF");

        playerStartsToggle.setEnabled(GamePanel.engineEnabled);

        playerStartsToggle.setSelected(GamePanel.playerStarts);
        playerStartsToggle.setText(GamePanel.playerStarts ? "IGRALEC" : "AI");

        symbolCombo.setSelectedItem(GamePanel.startingSymbol == Cell.O ? "O" : "X");
    }
}

