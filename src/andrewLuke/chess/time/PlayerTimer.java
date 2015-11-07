package andrewLuke.chess.time;

/**
 * Handles calculations related to the player's time limit per game
 *
 * @author Andrew
 */
public class PlayerTimer
{

    boolean p1Previous = true;
    long startTime;
    long p1secondsElapsed = 0;
    long p2secondsElapsed = 0;
    int minutesPerPlayer;
    long secondsPerPlayer;
    public long p1secondsRemaining;
    public long p2secondsRemaining;
    public int p1minutesRemaining;
    public int p2minutesRemaining;
    public int p1SecondsDigits = 0;
    public int p2SecondsDigits = 0;
    public static boolean p1Turn = true;


    /**
     * Starts the timer
     * @param minutesPerPlayer
     */
    public PlayerTimer(int minutesPerPlayer)
    {
        this.minutesPerPlayer = minutesPerPlayer;
        secondsPerPlayer = minutesPerPlayer * 60;
        p1secondsRemaining = minutesPerPlayer * 60;
        p2secondsRemaining = minutesPerPlayer * 60;
        startTime = System.currentTimeMillis() / 1000;
        p2minutesRemaining = (int) (p2secondsRemaining / 60.0);
        p1minutesRemaining = (int) (p1secondsRemaining / 60.0);
    }

    /**
     * Updates the time elapsed since the timer was started
     */
    public void updateElapsedTime()
    {
        if (p1Turn && p1Previous)
        {
            p1secondsElapsed += System.currentTimeMillis() / 1000 - startTime;
            startTime = System.currentTimeMillis() / 1000;
            p1secondsRemaining = secondsPerPlayer - p1secondsElapsed;
            setDigits();
        }
        else if (p1Turn && !p1Previous)
        {
            resetPlayerTimer();
        }
        else if (!p1Turn && !p1Previous)
        {
            p2secondsElapsed += System.currentTimeMillis() / 1000 - startTime;
            startTime = System.currentTimeMillis() / 1000;
            p2secondsRemaining = secondsPerPlayer - p2secondsElapsed;
            setDigits();
        }
        else if (!p1Turn && p1Previous)
        {
            resetPlayerTimer();
        }
        if (!p1Turn)
        {
            p1Previous = false;
        }
        else
        {
            p1Previous = true;
        }
    }

    private void resetPlayerTimer()
    {
        startTime = System.currentTimeMillis() / 1000;
    }

    private void setDigits()
    {
        p2SecondsDigits = (int) (p2secondsRemaining % 60);
        p1SecondsDigits = (int) (p1secondsRemaining % 60);
        p2minutesRemaining = (int) (p2secondsRemaining / 60.0);
        p1minutesRemaining = (int) (p1secondsRemaining / 60.0);
    }
}