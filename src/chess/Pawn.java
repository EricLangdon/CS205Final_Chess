package chess;

import java.util.ArrayList;

public class Pawn extends Piece {
    public static final int SCORE = 3;

    public Pawn(Color color) {
        super(color);
        unicode = 0x2659;
    }

    /**
     * legalMove
     * Check if the given move is legal for the pawn given the board source and target squares.
     *
     * @param board  the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target) {
        //Checks if target is legal first
        if (super.legalMove(board, source, target)) {

            //Creates the inverter that applies the moves to opposite color
            int invert;
            if (color.equals(Color.WHITE)) {
                invert = 1;
            } else {
                invert = -1;
            }

            //Handles pawn logic
            if (target.y == source.y + invert && target.x == source.x && target.getPiece() == null) {
                return true;
            } else if (target.y == source.y + 2 * invert && target.x == source.x && target.getPiece() == null && !getHasMoved()) {
                return true;
            } else if (target.y == source.y + invert && target.x == source.x - invert && target.isOccupied() && target.getPiece().getColor() == color.other()) {
                return true;
            } else if (target.y == source.y + invert && target.x == source.x + invert && target.isOccupied() && target.getPiece().getColor() == color.other()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * getColor
     *
     * @return the color of the piece
     */
    public Color getColor() {
        return color;
    }
}
