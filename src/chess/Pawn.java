package chess;

import java.util.ArrayList;

public class Pawn extends Piece {
    public static final int SCORE = 3;
    private Color color;
    private BoardSquare startSquare;


    /**
     * Default constructor
     * Sets the pawn's color to white
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
     * Check if the given move is legal for the pawn given the board source and target squares.
     * @param board the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove (Board board, BoardSquare source, BoardSquare target){
        if (color==color.WHITE){
            if(target.y==source.y+1){
                return true;
            }
            else if(!hasMoved(source) && target.y==source.y+2){
                return true;
            }
            else if(target.y==source.y+1 && target.x==source.x-1 && target.getPiece().getColor()==color.BLACK){
                return true;
            }
            else if(target.y==source.y+1 && target.x==source.x+1 && target.getPiece().getColor()==color.BLACK){
                return true;
            }
            else{
                return false;
            }
        }
        else{
        }
    }

    public boolean hasMoved(BoardSquare current){
        if (current.x == startSquare.x && current.y == startSquare.y){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * getAvailableMoves
     * Returns list of all legal moves available to the pawn
     * @param board the board
     * @param source boardsquare where the piece currently is located
     * @return an arraylist of all boardsquares that the pawn can legally move to
     */
    public ArrayList<BoardSquare> getAvailableMoves(Board board, BoardSquare source){

    }

    /**
     * getColor
     * @return the color of the piece
     */
    public Color getColor() {return color}

}
