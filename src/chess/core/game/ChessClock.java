package chess.core.game;

import chess.core.piece.Color;

public class ChessClock {
    Color color;
    int timeRemaining;
    final int TIME_ALLOWED = 300;   //seconds

    /**
     * Default Constructor
     */
    public ChessClock(Color color) {
        this.color = color;
        timeRemaining = TIME_ALLOWED;
    }

    //TODO: UPDATE TIMER
    /**
     * updateTime
     *
     * @param timeSpent the time (in seconds) used by the player to make their move
     */
    public void decrementTime(int timeSpent) {

        timeRemaining = getTimeRemaining() - timeSpent;
    }

    /**
     * getTimeRemaining
     *
     * @return the remaining time the player has to make their moves
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * players turn starts
     * clock starts
     * redraw clock every second, get time from class
     * stop clock once turn ends
     */

    /**
     * printTime
     *
     * @return formatted string of the time remaining (m:ss)
     */
     public String printTime() {
         int minutes;
         int seconds;

         minutes = getTimeRemaining() / 60;
         seconds = getTimeRemaining() % 60;
         if (seconds < 10) {
             return (minutes + ":0" + seconds);
         } else {
             return (minutes + ":" + seconds);
         }
     }

}