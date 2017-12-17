package gui;

import model.Answer;

import javax.swing.*;
import java.awt.*;

public class AnswerPanel extends JPanel{
    private JTextField text;
    private JTextField nextQuestionID;

    public AnswerPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));

        topPanel.add(new JLabel("Text: "));
        text = new JTextField();
        topPanel.add(text);

        topPanel.add(new JLabel("Next Question ID: "));
        nextQuestionID = new JTextField();
        nextQuestionID.setText("-1");
        topPanel.add(nextQuestionID);

        add(topPanel, BorderLayout.NORTH);
    }

    public Answer getAnswer() {
        try {
            return new Answer(text.getText(), Integer.parseInt(nextQuestionID.getText().trim()));
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Parsing Error!", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public boolean isPanelValid() {
        return !text.getText().trim().equals("");
    }
}
