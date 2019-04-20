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

    public ChessClock(Game game, Color color) {
        this(game, color, DEFAULT_TIME);
    }

    public ChessClock(Game game, Color color, int time) {
        this.time = time;
        this.color = color;
        this.active = true;
        this.game = game;

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
        timer = exec.scheduleAtFixedRate(() -> {
            if (this.game.getCurrentTurn() == color && this.active) {
                this.time -= 100;
            }
            if (this.time <= -1) {
                this.time = -1;
                this.cancel();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void start() {
        this.active = true;
    }

    public void pause() {
        this.active = false;
    }

    public void cancel() {
        timer.cancel(false);
    }

    public boolean isActive() {
        return active;
    }

    public Color getColor() {
        return color;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        int time = this.time >= 0 ? this.time : 0;
        int minutes = (this.time / 1000) / 60;
        int seconds = (this.time / 1000) % 60;
        return String.format("%d", minutes) + ":" + String.format("%02d", seconds);
    }

}