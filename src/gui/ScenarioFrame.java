package gui;

import model.Question;
import model.ScenarioEditor;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class ScenarioFrame extends JFrame implements ActionListener, MouseListener {
    private ScenarioEditor scenarioEditor;
    private JTable questions;

    public ScenarioFrame(ScenarioEditor scenarioEditor) {
        this.scenarioEditor = scenarioEditor;

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1024, 768);
        setTitle(scenarioEditor.getName());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 7));

        topPanel.add(new JLabel());

        JButton addQuestionButton = new JButton();
        addQuestionButton.setText("Add Question");
        addQuestionButton.addActionListener(this);
        topPanel.add(addQuestionButton);

        topPanel.add(new JLabel());

        JButton removeQuestionButton = new JButton();
        removeQuestionButton.setText("Remove Question");
        removeQuestionButton.addActionListener(this);
        topPanel.add(removeQuestionButton);

        topPanel.add(new JLabel());

        JButton saveButton = new JButton();
        saveButton.setText("Save Scenario");
        saveButton.addActionListener(this);
        topPanel.add(saveButton);

        topPanel.add(new JLabel());

        for (int i = 0; i < 7; i++)
            topPanel.add(new JLabel());

        // Table
        String[] columns = {"Question ID", "Question Text"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        questions = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        questions.addMouseListener(this);
        questions.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        questions.setSelectionBackground(Color.CYAN);
        JScrollPane scrollPane = new JScrollPane(questions);
        questions.setFillsViewportHeight(true);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());

        for (int i = 0; i < scenarioEditor.getQuestionCount(); i++) {
            Question question = scenarioEditor.getQuestion(i);
            model.addRow(new Object[]{i, question.getText()});
        }

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals("Add Question"))
            addQuestion();
        else if (clickedButton.getText().equals("Remove Question")) {
            if (JOptionPane.showConfirmDialog(this, "Do you want to delete this question?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION)
                removeQuestion();

        } else{
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Save as JSON");
            jFileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json", "json"));
            if (jFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                saveScenario(jFileChooser);
        }
    }

    private void addQuestion() {
        QuestionPanel questionPanel = new QuestionPanel();
        if (JOptionPane.showConfirmDialog(this, questionPanel,"New Question",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (!questionPanel.isPanelValid()) {
                JOptionPane.showMessageDialog(this, "Question Text cannot be empty!", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Question question = questionPanel.getQuestion();
            DefaultTableModel model = (DefaultTableModel) questions.getModel();
            model.addRow(new Object[] {scenarioEditor.getQuestionCount(), question.getText()});
            scenarioEditor.addQuestion(question);
        }
    }

    private void removeQuestion() {
        int id = questions.getSelectedRow();
        if (id == -1) {
            JOptionPane.showMessageDialog(this, "You need to choose a question", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) questions.getModel();
        for (int i = 0; i < model.getRowCount(); i++)
            scenarioEditor.getQuestion(i).setText(model.getValueAt(i, 1).toString());

        scenarioEditor.removeQuestion(id);

        model.removeRow(id);
        for (int i = 0; i < model.getRowCount(); i++)
            model.setValueAt(i, i, 0);

    }

    private void saveScenario(JFileChooser jFileChooser) {
        DefaultTableModel model = (DefaultTableModel) questions.getModel();

        for (int i = 0; i < model.getRowCount(); i++)
            scenarioEditor.getQuestion(i).setText(model.getValueAt(i, 1).toString());

        try {
            FileWriter writer = new FileWriter(jFileChooser.getSelectedFile());
            writer.write(scenarioEditor.toString());
            writer.flush();
            writer.close();
            JOptionPane.showMessageDialog(this, "The scenario has been created successfully!", "Congratulations :)", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Writing Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int id = questions.getSelectedRow();
        if (id == -1 || e.getClickCount() < 2 || questions.getSelectedColumn() != 0)
            return;

        DefaultTableModel model = (DefaultTableModel) questions.getModel();
        Question question = scenarioEditor.getQuestion(id);
        question.setText(model.getValueAt(id,1).toString());
        QuestionPanel questionPanel = new QuestionPanel(scenarioEditor.getQuestion(id));
        if (JOptionPane.showConfirmDialog(this, questionPanel, "Edit Question", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            question = questionPanel.getQuestion();
            model.removeRow(id);
            model.insertRow(id, new Object[] {id, question.getText()});
            scenarioEditor.getQuestions().set(id, question);
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
