package gui;

import model.Answer;
import model.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class QuestionPanel extends JPanel implements ActionListener, MouseListener {
    private JTextField text;
    private int counter;
    private JTable answers;

    public QuestionPanel(Question question) {
        this();
        text.setText(question.getText());

        for (Answer answer : question.getAnswers())
            addAnswer(answer);
    }

    public QuestionPanel() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));
        topPanel.add(new JLabel("Text: "));
        text = new JTextField();
        topPanel.add(text);
        topPanel.add(new JLabel());
        JButton addAnswerButton = new JButton();
        addAnswerButton.setText("Add Answer");
        addAnswerButton.addActionListener(this);
        topPanel.add(addAnswerButton);

        // Table
        String[] columns = {"ID", "Text", "New Question ID", "Achievement Text", "Achievement Point"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        answers = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        answers.addMouseListener(this);
        answers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        answers.setSelectionBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(answers);
        answers.setFillsViewportHeight(true);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        counter = 0;
    }

    public void actionPerformed(ActionEvent e) {
        AnswerPanel answerPanel = new AnswerPanel();
        if (JOptionPane.showConfirmDialog(this, answerPanel, "New Answer", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (answerPanel.isPanelValid())
                addAnswer(answerPanel.getAnswer());
            else
                JOptionPane.showMessageDialog(this, "Answer Text cannot be empty!", "Warning!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void addAnswer(Answer answer) {
        if (answer != null) {
            Object[] newRow;
            DefaultTableModel model = (DefaultTableModel) answers.getModel();
            if (!answer.hasAchievement())
                newRow = new Object[] { counter, answer.getText(), "" + answer.getNextQuestionID(), "", "0" };
            else 
                newRow = new Object[] { counter, answer.getText(), "" + answer.getNextQuestionID(), answer.getAchievement().getText(), "" + answer.getAchievement().getPoint() };

            model.insertRow(counter, newRow);
            counter++;
        }
    }

    public Question getQuestion() {
        Question question = new Question(text.getText());
        DefaultTableModel model = (DefaultTableModel) answers.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                Answer answer = question.addAnswer(model.getValueAt(i, 1).toString(), Integer.parseInt(model.getValueAt(i, 2).toString()));
                String achievementText = model.getValueAt(i, 3).toString().trim();
                if (achievementText.equals(""))
                    continue;

                answer.setAchievement(achievementText, Integer.parseInt(model.getValueAt(i, 4).toString()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Parsing Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

        return question;
    }

    public boolean isPanelValid() {
        return !text.getText().trim().equals("");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int id = answers.getSelectedRow();
        if (id == -1 || e.getClickCount() < 2 || answers.getSelectedColumn() != 0)
            return;

        if (JOptionPane.showConfirmDialog(this, "Do you want to delete this answer?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
            DefaultTableModel model = (DefaultTableModel) answers.getModel();

            model.removeRow(id);
            for (int i = 0; i < model.getRowCount(); i++)
                model.setValueAt(i, i, 0);
            
            counter = model.getRowCount();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        ;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        ;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ;
    }
}
