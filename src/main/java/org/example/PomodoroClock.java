package org.example;

import javax.swing.*;
import java.awt.*;

public class PomodoroClock extends JFrame {

    public PomodoroClock() {
        // Set up the frame
        setTitle("Pomodoro Clock");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel (Timer Display and Controls)
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new GridLayout(2, 1));

        JLabel timerLabel = new JLabel("25:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Serif", Font.BOLD, 48));
        timerPanel.add(timerLabel);

        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton resetButton = new JButton("Reset");

        String[] sessionTypes = {"Work (25 min)", "Short Break (5 min)", "Long Break (25 min)"};
        JComboBox<String> sessionSelector = new JComboBox<>(sessionTypes);

        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(resetButton);
        controlPanel.add(sessionSelector);

        timerPanel.add(controlPanel);
        add(timerPanel, BorderLayout.NORTH);

        // Center panel (Task List)
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new BorderLayout());

        JTextField taskField = new JTextField();
        JButton addTaskButton = new JButton("Add Task");

        JPanel taskInputPanel = new JPanel();
        taskInputPanel.setLayout(new BorderLayout());
        taskInputPanel.add(taskField, BorderLayout.CENTER);
        taskInputPanel.add(addTaskButton, BorderLayout.EAST);

        DefaultListModel<String> taskListModel = new DefaultListModel<>();
        JList<String> taskList = new JList<>(taskListModel);
        taskList.setVisibleRowCount(10);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane taskScrollPane = new JScrollPane(taskList);

        taskPanel.add(taskInputPanel, BorderLayout.NORTH);
        taskPanel.add(taskScrollPane, BorderLayout.CENTER);
        add(taskPanel, BorderLayout.CENTER);

        // Bottom panel (Progress and session info)
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(3, 1));

        JLabel modeLabel = new JLabel("Mode: Work", SwingConstants.CENTER);
        JLabel timeRemainingLabel = new JLabel("Time Remaining: 25:00", SwingConstants.CENTER);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        infoPanel.add(modeLabel);
        infoPanel.add(timeRemainingLabel);
        infoPanel.add(progressBar);

        add(infoPanel, BorderLayout.SOUTH);

        // Event listeners
        addTaskButton.addActionListener(e -> {
            String task = taskField.getText();
            if (!task.isEmpty()) {
                taskListModel.addElement(task);
                taskField.setText("");
            }
        });

        // Show the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PomodoroClock::new);
    }
}
