package org.example.application;

import org.example.domain.Session;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/****
 * Class used to implement the timer service logic.
 */
public class TimerService
{
    private Session currentSession;
    private Timer timer;
    private TimerListener listener;

    public TimerService(TimerListener listener)
    {
        this.listener = listener;
    }

    public void startWorkSession()
    {
        startSession(25, "Work");
    }

    public void startShortBreak()
    {
        startSession(5, "Short Break");
    }

    public void startLongBreak()
    {
        startSession(15, "Long Break");
    }

    private void startSession(int minutes, String sessionType)
    {
        currentSession = new Session(minutes, sessionType);
        timer = new Timer(1000, new ActionListener()
        { // Fires every second
            @Override
            public void actionPerformed(ActionEvent e)
            {
                currentSession.tick();
                listener.onTick(currentSession.getTimeLeft());

                if (currentSession.isComplete())
                {
                    timer.stop();
                    listener.onSessionComplete(currentSession.getSessionType());
                }
            }
        });
        timer.start();
    }

    public void pause()
    {
        if (timer != null)
        {
            timer.stop();
        }
    }

    public void resume()
    {
        if (timer != null)
        {
            timer.start();
        }
    }

    public boolean isSessionRunning()
    {
        return timer != null && timer.isRunning();
    }

    public void resetSession()
    {
        timer.stop();

        currentSession.reset();
    }

    // TimerListener Interface
    public interface TimerListener
    {
        void onTick(int timeLeftInSeconds); // Update the UI with the remaining time

        void onSessionComplete(String sessionType); // Handle session completion
    }
}
