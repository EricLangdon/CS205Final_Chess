package chess;

public enum Color {
    BLACK, WHITE;

    /**
     * @return the opposite color
     */
    Color other(){
        if (this.equals(BLACK)){
            return WHITE;
        }else{
            return BLACK;
        }
    }
}
