package chess.core.game;

import chess.core.piece.Color;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


public class ChessClock {
    private static final int DEFAULT_TIME = 5 * 60 * 1000;
    private Color color;
    private Game game;
    private int time;
    private boolean active;
    private ScheduledFuture<?> timer;

    /**
     * Default constructor, uses DEFAULT_TIME and starts clock inactive
     *
     * @param game  the game the timer is attached to
     * @param color the color of the player
     */
    public ChessClock(Game game, Color color) {
        this(game, color, DEFAULT_TIME, false);
    }

    /**
     * @param game   the game the timer is attached to
     * @param color  the color of the player
     * @param time   the initial time
     * @param active should the timer be started on creation?
     */
    public ChessClock(Game game, Color color, int time, boolean active) {
        this.time = time;
        this.color = color;
        this.active = active;
        this.game = game;

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
        timer = exec.scheduleAtFixedRate(() -> {
            if (this.game.getCurrentTurn() == color && this.active && !this.game.isGameOver()) {
                this.time -= 100;
            }
            if (this.time <= -1) {
                this.time = -1;
                this.cancel();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    /**
     * Start the timer counting
     */
    public void start() {
        this.active = true;
    }

    /**
     * Temporarily pause the timer from counting down, but allow it to be started again with start()
     */
    public void pause() {
        this.active = false;
    }

    /**
     * Stop the timer from counting down, and prevent future usage
     */
    public void cancel() {
        timer.cancel(false);
        active = false;
    }

    /**
     * @return true if the timer is active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get the color associated with the timer
     *
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get the time left
     *
     * @return the time left in ms
     */
    public int getTime() {
        return time;
    }

    /**
     * Set the time left
     *
     * @param time the time left in ms
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Get string rep of timer formatted as m:ss
     *
     * @return string representation
     */
    @Override
    public String toString() {
        int time = this.time >= 0 ? this.time : 0;
        int minutes = (this.time / 1000) / 60;
        int seconds = (this.time / 1000) % 60;
        return String.format("%d", minutes) + ":" + String.format("%02d", seconds);
    }

}