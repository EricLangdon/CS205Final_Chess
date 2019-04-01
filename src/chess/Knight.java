package chess;

import java.util.ArrayList;

public class Knight extends Piece {
    public static final int SCORE = 3;
    private Color color;


    /**
     * Constrictor
     * @param color the color of the piece
     */
    public Knight(Color color){
        super(color);
    }

    /**
     * legalMove
     * Check if the given move is legal for the knight given the board source and target squares.
     * @param board the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove (Board board, BoardSquare source, BoardSquare target){
        //Checks if target is legal first
        if(super.legalMove(board, source, target)){

            //Creates the inverter that creates left side moves
            int invert;
            if(target.getX()>source.getX()){
                invert = 1;
            } else{
                invert =-1;
            }

            //Handles knight logic
            if(target.getY()==source.getY()+2 && target.getX()==source.getX()+invert){              //Up 2 and left/right
                return true;
            } else if (target.getY()==source.getY()+1 && target.getX()==source.getX()+2*invert){    //Up 1 and left/right
                return true;
            } else if (target.getY()==source.getY()-1 && target.getX()==source.getX()+2*invert){    //Down 1 and left/right
                return true;
            } else if (target.getY()==source.getY()-2 && target.getX()==source.getX()+invert){      //down 2 and left/right
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }
    }

    /**
     * getColor
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }

}
