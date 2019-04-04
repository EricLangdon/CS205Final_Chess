package chess;

import java.time.*;

public class ChessClock {
    long timeRemaining;
    final long TIME_ALLOWED = 300;   //seconds

    /**
     * Default Constructor
     */
    public ChessClock() {
        timeRemaining = TIME_ALLOWED;
    }

    /**
     * countTime
     *
     * @param start the start time
     * @param finish the finish time
     * @return the time(in seconds) spent by the player
     */
    public long countTime(Instant start, Instant finish) {
        long timeSpent = Duration.between(start, finish).toSeconds();
        return timeSpent;
    }

    /**
     * updateTime
     *
     * @param timeSpent the time (in seconds) used by the player to make their move
     */
    public void updateTime(long timeSpent) {
        timeRemaining = getTimeRemaining() - timeSpent;
    }

    /**
     * getTimeRemaining
     *
     * @return the remaining time the player has to make their moves
     */
    public long getTimeRemaining() { return timeRemaining; }

    /**
     * toString
     *
     * @return formatted string of the time remaining (m:ss)
     */
     public String toString() {
        int minutes;
        int seconds;

        minutes = (int)getTimeRemaining() / 60;
        seconds = (int)getTimeRemaining() % 60;

        return (minutes + ":" + seconds);
     }

}