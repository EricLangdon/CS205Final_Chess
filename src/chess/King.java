package chess;

import java.util.ArrayList;

public class King extends Piece {
    public static final int SCORE = 99;

    /**
     * Constrictor
     *
     * @param color the color of the piece
     */
    public King(Color color) {
        super(color);
        unicode = 0x2654;
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
            if ((source.x == target.x || source.x - target.x == 1 || source.x - target.x == -1) &&
                    (source.y == target.y || source.y - target.y == 1 || source.y - target.y == -1)) {
                return true;
            } else if (!getHasMoved()) { // if King has not moved (for castling)
                // get row that the castle is taking place in
                int row = 0;
                if (getColor() == Color.BLACK) {
                    row = 7;
                }
                if (source.y == target.y && source.x - target.x == -2) { // if King is moving two to the right
                    if (!board.getBoardSquareAt(source.x + 1, row).isOccupied()) { // path is clear
                        if (board.getBoardSquareAt(7, row).isOccupied()) { // if there is a piece in that corner
                            if (!board.getBoardSquareAt(7, row).piece.getHasMoved()) { // if right rook has not moved
                                return true;
                            }
                        }
                    }
                } else if (source.y == target.y && source.x - target.x == 2) { // if King is moving two to the left
                    if (!board.getBoardSquareAt(source.x - 1, row).isOccupied() &&
                            !board.getBoardSquareAt(source.x - 3, row).isOccupied()) { // path is clear
                        if (board.getBoardSquareAt(0, row).isOccupied()) { // if there is a piece in that corner
                            if (!board.getBoardSquareAt(0, row).piece.getHasMoved()) { // if left rook has not moved
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}