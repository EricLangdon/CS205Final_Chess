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
        // TODO: super
        if (source.x == target.x) { // if in the same column
            if (source.y < target.y) { // if target is higher on the board
                for (int i = 1; source.y + i < target.y; i++) {
                    if (board.getBoardSquareAt(source.x, source.y + i).isOccupied()) {
                        return false;
                    }
                }
                return true;
            }
            if (source.y > target.y) { // if target is lower on the board
                for (int i = 1; source.y - i > target.y; i++) {
                    if (board.getBoardSquareAt(source.x, source.y - i).isOccupied()) {
                        return false;
                    }
                }
                return true;
            }
        }
        if (source.y == target.y) { // if in the same row
            if (source.x < target.x) { // if target is to the right on the board
                for (int i = 1; source.x + i < target.x; i++) {
                    if (board.getBoardSquareAt(source.y, source.x + i).isOccupied()) {
                        return false;
                    }
                }
                return true;
            }
            if (source.x > target.x) { // if target is to the left on the board
                for (int i = 1; source.x - i > target.x; i++) {
                    if (board.getBoardSquareAt(source.y, source.x - i).isOccupied()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

}