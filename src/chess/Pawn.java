package chess;

import java.util.ArrayList;

public class Pawn extends Piece {
    public static final int SCORE = 3;
    private Color color;

    /**
     * Default constructor
     * Sets the pawn's color to white
     */
    public void Piece() {
        this.color = color.WHITE;
    }

    /**
     * Constructor
     *
     * @param color The color of the piece
     */
    public void Piece(Color color) {
        this.color = color;
        this.color = color;
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

            //Creates the inverter
            int invert;
            if (color == color.WHITE) {
                invert = 1;
            } else {
                invert = -1;
            }

            //Handles pawn logic
            if (target.y == source.y + 1 * invert && target.getPiece() == null) {
                return true;
            } else if (target.y == source.y + 2 * invert && !getHasMoved()) {
                return true;
            } else if (target.y == source.y + 1 * invert && target.x == source.x - 1 * invert && target.getPiece().getColor() == color.BLACK) {
                return true;
            } else if (target.y == source.y + 1 * invert && target.x == source.x + 1 * invert && target.getPiece().getColor() == color.BLACK) {
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
