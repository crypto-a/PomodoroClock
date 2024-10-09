package org.example.domain;


/****
 * A class that implements all the session logic.
 */
public class Session
{
    private final String sessionType; // "Work", "Short Break", "Long Break"

    private  int timeLeftInSeconds;
    private  boolean isComplete;

    public Session(int minutes, String sessionType)
    {
        this.timeLeftInSeconds = minutes * 60;
        this.sessionType = sessionType;
        this.isComplete = false;
    }

    /****
     * Method to start counting in the timer
     */
    public void startSession()
    {
        //ToDo: start the session
    }

    /****
     * Method to record change of time.
     */
    public void tick()
    {
        if (timeLeftInSeconds > 0)
        {
            timeLeftInSeconds--;
        }
        else
        {
            isComplete = true;
        }
    }

    public boolean isComplete()
    {
        return isComplete;
    }

    public int getTimeLeft()
    {
        return timeLeftInSeconds;
    }

    public String getSessionType()
    {
        return sessionType;
    }


}
