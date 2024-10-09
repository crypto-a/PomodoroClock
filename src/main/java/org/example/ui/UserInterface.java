package org.example.ui;

import org.example.application.TimerService;
import org.example.controllers.TimerController;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/****
 * This class represents the User interface and will be in charge of handling the user interactions.
 */
public class UserInterface implements TimerService.TimerListener
{
    private JPanel TimerPanel;
    private JTabbedPane tabbedPane1;
    private JLabel workTimeDisplay;
    private JButton workTimeStartButton;
    private JLabel shortBreakTimeDisplay;
    private JButton shortBreakStartButton;
    private JPanel workSessionPanel;
    private JPanel shortBreakPanel;
    private JPanel longBreakPanel;
    private JLabel longBreakTimeDisplay;
    private JButton longBreakStartButton;

    private final TimerController timerController;
    private int workSessionCount = 0;

    public UserInterface()
    {
        timerController = new TimerController(this); // Passing UI as listener

        // Work Session Start Button
        workTimeStartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleStartPauseButton(workTimeStartButton, "Work");
            }
        });

        // Short Break Start Button
        shortBreakStartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleStartPauseButton(shortBreakStartButton, "Short Break");
            }
        });

        // Long Break Start Button
        longBreakStartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                handleStartPauseButton(longBreakStartButton, "Long Break");
            }
        });

        // Reset timer when switching tabs
        tabbedPane1.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                timerController.resetTimer();
                resetDisplays();  // Reset all displays when switching
                updateButtonLabels();
            }
        });
    }

    // Helper method for handling start/pause logic
    private void handleStartPauseButton(JButton button, String sessionType)
    {
        playSound("org/example/sounds/click.mp3");

        if (!timerController.isRunning())
        {
            switch (sessionType)
            {
                case "Work":
                    timerController.startWorkSession();
                    break;
                case "Short Break":
                    timerController.startShortBreak();
                    break;
                case "Long Break":
                    timerController.startLongBreak();
                    break;
            }
            button.setText("Pause");
        } else
        {
            timerController.pause();
            button.setText("Start");
        }
    }

    // TimerListener implementations for updating the UI
    @Override
    public void onTick(int timeLeftInSeconds)
    {
        String formattedTime = String.format("%02d:%02d", timeLeftInSeconds / 60, timeLeftInSeconds % 60);

        int selectedTab = tabbedPane1.getSelectedIndex();
        switch (selectedTab)
        {
            case 0:
                workTimeDisplay.setText(formattedTime);
                break;
            case 1:
                shortBreakTimeDisplay.setText(formattedTime);
                break;
            case 2:
                longBreakTimeDisplay.setText(formattedTime);
                break;
        }
    }

    @Override
    public void onSessionComplete(String sessionType)
    {
        playSound("org/example/sounds/alarm.mp3"); // Play sound when session completes

        JOptionPane.showMessageDialog(TimerPanel, sessionType + " completed!");

        // Switch sessions based on session type
        switch (sessionType)
        {
            case "Work":
                workSessionCount++;
                if (workSessionCount >= 4)
                {
                    tabbedPane1.setSelectedIndex(2); // Go to long break after 4 work sessions
                    workSessionCount = 0;
                    timerController.startLongBreak();
                } else
                {
                    tabbedPane1.setSelectedIndex(1); // Go to short break
                    timerController.startShortBreak();
                }
                break;
            case "Short Break":
                tabbedPane1.setSelectedIndex(0); // Return to work session
                timerController.startWorkSession();
                break;
            case "Long Break":
                tabbedPane1.setSelectedIndex(0); // Return to work session
                timerController.startWorkSession();
                break;
        }

        updateButtonLabels();
    }

    // Reset the time displays on switching tabs
    private void resetDisplays()
    {
        workTimeDisplay.setText("25:00");
        shortBreakTimeDisplay.setText("05:00");
        longBreakTimeDisplay.setText("15:00");
    }

    // Update button labels based on which tab is selected
    private void updateButtonLabels()
    {
        workTimeStartButton.setText("Start");
        shortBreakStartButton.setText("Start");
        longBreakStartButton.setText("Start");
    }

    // Method to play a sound when a session completes
    private void playSound(String path)
    {
        try
        {
            File soundFile = new File("path/to/sound.wav"); // Add your sound file path here
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(soundFile));
            clip.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Main method to run the UI
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Pomodoro Timer");
        frame.setContentPane(new UserInterface().TimerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
