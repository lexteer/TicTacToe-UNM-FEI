package si.unm_fei.ui;

import si.unm_fei.core.GamePanel;
import si.unm_fei.logic.GridCells;
import si.unm_fei.logic.Question;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class QuestionPopUp  extends JDialog  {
    private boolean correctAnswer = false;  // Shrani rezultat

    public QuestionPopUp(JFrame parent, Question q, GridCells gs) {
        super(parent, "Vprašanje", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Vprašanje na vrhu
        JLabel questionLabel = new JLabel("<html><div style='text-align: center; padding-top: 20px;'><b>"
                + q.text + "</b></div></html>");
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        //mešanje odgovorov
        List<String> shuffledAnswers = new ArrayList<>(Arrays.asList(q.answers));
        Collections.shuffle(shuffledAnswers);

        // Odgovori v sredini
        JPanel answersPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        answersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String ans : shuffledAnswers) {
            JButton btn = new JButton(ans);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(300, 50));
            btn.setFocusPainted(false);

            btn.addActionListener(e -> {
                if (ans.equals(q.correct)) {
                    correctAnswer = true;  // Shrani true
                    gs.setStatus(gs.currentIndex, GamePanel.playerSymbol);
                    gs.forceDraw = true;
                    showMessage("Pravilno!", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    correctAnswer = false;  // Shrani false
                    showMessage("Napačno! Poskusi kasneje.", "Rezultat", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            });
            answersPanel.add(btn);
        }
        add(answersPanel, BorderLayout.CENTER);
    }

    //prikazPopUp
    public boolean showAndGetResult() {
        setVisible(true);  // Blokira dokler se popup ne zapre
        return correctAnswer;
    }

    private void showMessage(String message, String title, int type) {
        JLabel label = new JLabel(message);
        label.setFont(new Font("Dialog", Font.BOLD, 16));

        JOptionPane pane = new JOptionPane(
                label,
                type,
                JOptionPane.DEFAULT_OPTION
        );

        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);
        ok.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        pane.setOptions(new Object[]{ ok });

        JDialog dialog = pane.createDialog(this, title);

        ok.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

}
