package chess;

import java.util.ArrayList;

public class Rook extends Piece {
    public static final int SCORE = 5;

    /**
     * legalMove
     * Check if the given move is legal for the Bishop given the board source and target squares.
     *
     * @param board  the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target) {
        // TODO: implement
        if (source.x == target.x || source.y == target.y) {
            return true;
        }
        return false;
    }

}