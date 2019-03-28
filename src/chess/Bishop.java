package chess;

import java.util.ArrayList;

public class Bishop extends Piece {
    public static final int SCORE = 3;
    private Color color;


    /**
     * Default constructor
     * Sets the Bishops's color to white
     */
    public void Piece(){
        this.color= color.WHITE;
    }
    /**
     * Constructor
     * @param color The color of the piece
     */
    public void Piece(Color color){
        this.color = color;
    }

    /**
     * legalMove
     * Check if the given move is legal for the Bishop given the board source and target squares.
     * @param board the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove (Board board, BoardSquare source, BoardSquare target){
        return false;
    }

    /**
     * getColor
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }
}
