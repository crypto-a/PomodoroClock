package org.example.controllers;

import org.example.application.TimerService;

public class TimerController
{
    private TimerService timerService;

    public TimerController(TimerService.TimerListener listener)
    {
        timerService = new TimerService(listener);
    }

    public void startWorkSession()
    {
        timerService.startWorkSession();
    }

    public void startShortBreak()
    {
        timerService.startShortBreak();
    }

    public void startLongBreak()
    {
        timerService.startLongBreak();
    }

    public void pause()
    {
        timerService.pause();
    }

    public void resume()
    {
        timerService.resume();
    }

    public boolean isRunning()
    {
        return timerService.isSessionRunning();
    }

    public void resetTimer()
    {
        timerService.resetSession();
    }
}
