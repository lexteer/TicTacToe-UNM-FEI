package si.unm_fei.ui;

import si.unm_fei.logic.Question;
import javax.swing.*;

import javax.swing.*;
import java.awt.*;


public class QuestionPopUp  extends JDialog  {
    private boolean correctAnswer = false;  // ✅ Shrani rezultat

    public QuestionPopUp(JFrame parent, Question q) {
        super(parent, "Vprašanje", true);
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Vprašanje na vrhu
        JLabel questionLabel = new JLabel("<html><div style='text-align: center; padding: 10px;'><b>"
                + q.text + "</b></div></html>");
        questionLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(questionLabel, BorderLayout.NORTH);

        // Odgovori v sredini
        JPanel answersPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        answersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (String ans : q.answers) {
            JButton btn = new JButton(ans);
            btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
            btn.setPreferredSize(new Dimension(300, 50));
            btn.setFocusPainted(false);

            btn.addActionListener(e -> {
                if (ans.equals(q.correct)) {
                    correctAnswer = true;  // ✅ Shrani true
                    JOptionPane.showMessageDialog(this, "Pravilno! ✓", "Rezultat", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    correctAnswer = false;  // ✅ Shrani false
                    JOptionPane.showMessageDialog(this, "Napačno! Poskusi kasneje.", "Rezultat", JOptionPane.ERROR_MESSAGE);
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
}
