package chess.core.game;

public enum GameResult {
    DRAW, BLACKWIN, WHITEWIN, BLACKWIN_TIME, WHITEWIN_TIME;

    public String toString() {
        switch (this) {
            case BLACKWIN_TIME:
            case BLACKWIN:
                return "Black";
            case WHITEWIN_TIME:
            case WHITEWIN:
                return "White";
            case DRAW:
                return "Draw";
            default:
                return "Game over";
        }
    }
}
