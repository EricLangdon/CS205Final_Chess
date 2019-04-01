package chess;

import java.util.ArrayList;

public class Bishop extends Piece {

    /**
     * Constrictor
     *
     * @param color the color of the piece
     */
    public Bishop(Color color) {
        super(color);
        unicode = 0x2657;
        score = 3;
    }

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
        if (super.legalMove(board, source, target)) {
            if (source.x - target.x == source.y - target.y) { // if lower left or upper right diagonal
                if (source.x < target.x) { // if target is upper right
                    for (int i = 1; source.x + i < target.x; i++) {
                        if (board.getBoardSquareAt(source.x + i, source.y + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.x > target.x) { // if target is lower left
                    for (int i = 1; source.x - i > target.x; i++) {
                        if (board.getBoardSquareAt(source.x - i, source.y - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (source.x - target.x == (source.y - target.y) * -1) { // if upper left or lower right diagonal
                if (source.x < target.x) { // if target is lower right
                    for (int i = 1; source.x + i < target.x; i++) {
                        if (board.getBoardSquareAt(source.x + i, source.y - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.x > target.x) { // if target is upper left
                    for (int i = 1; source.x - i > target.x; i++) {
                        if (board.getBoardSquareAt(source.x - i, source.y + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
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
