package gui;

import model.ScenarioEditor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainFrame extends JFrame implements ActionListener{
    private JTextField scenarioName;
    private JTextField scenarioDescription;

    public MainFrame() {
        setLayout(new BorderLayout());
        setTitle("Scenario Editor");
        setSize(320, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 1));
        // Buttons

        topPanel.add(new JLabel());

        JButton newButton = new JButton();
        newButton.setText("New Scenario");
        newButton.addActionListener(this);
        topPanel.add(newButton);

        topPanel.add(new JLabel());

        JButton openButton = new JButton();
        openButton.setText("Open Scenario");
        openButton.addActionListener(this);
        topPanel.add(openButton);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals("New Scenario"))
            newScenario();
        else {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Open a JSON");
            jFileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json", "json"));

            if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                openFile(jFileChooser);
        }
    }

    private void newScenario() {
        if (JOptionPane.showConfirmDialog(this, createNewScenarioPanel(), "New Scenario",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (scenarioName.getText().trim().equals("") || scenarioDescription.getText().trim().equals("")) {
                JOptionPane.showMessageDialog(this, "You need to fill name and description", "Warning!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            new ScenarioFrame(new ScenarioEditor(scenarioName.getText(), scenarioDescription.getText()));
        }
    }

    private JPanel createNewScenarioPanel() {
        JPanel newScenario = new JPanel();
        newScenario.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2));

        topPanel.add(new JLabel("Scenario name: "));
        scenarioName = new JTextField();
        topPanel.add(scenarioName);

        topPanel.add(new JLabel("Scenario description: "));
        scenarioDescription = new JTextField();
        topPanel.add(scenarioDescription);

        newScenario.add(topPanel, BorderLayout.NORTH);

        return newScenario;
    }

    private void openFile(JFileChooser jFileChooser) {
        try {
            JSONObject scenario = (JSONObject) (new JSONParser()).parse(new FileReader(jFileChooser.getSelectedFile()));
            ScenarioFrame scenarioFrame = new ScenarioFrame(new ScenarioEditor(scenario));
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Parsing Error", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"File Not Found Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Reading Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassCastException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Casting Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
