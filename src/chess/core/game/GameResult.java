package chess.core.game;

public enum GameResult {
    DRAW, BLACKWIN, WHITEWIN;

    public String toString(){
        switch(this){
            case BLACKWIN:
                return "Black";
            case WHITEWIN:
                return "White";
            case DRAW:
                return "Draw";
            default:
                return "Game over";
        }
    }
}
