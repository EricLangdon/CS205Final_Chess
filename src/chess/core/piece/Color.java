package chess.core.piece;

public enum Color {
    BLACK, WHITE;

    /**
     * @return the opposite color
     */
    public Color other(){
        if (this.equals(BLACK)){
            return WHITE;
        }else{
            return BLACK;
        }
    }
}
