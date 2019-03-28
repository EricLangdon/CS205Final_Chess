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
        
    }

    /**
     * getAvailableMoves
     * Returns list of all legal moves available to the Bishop
     * @param board the board
     * @param source boardsquare where the piece currently is located
     * @return an arraylist of all boardsquares that the Bishop can legally move to
     */
    public ArrayList<BoardSquare> getAvailableMoves(Board board, BoardSquare source){

    }

    /**
     * getColor
     * @return the color of the piece
     */
    public Color getColor() {return color}
}
