package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;

public class King extends Piece {

    /**
     * Constrictor
     *
     * @param color the color of the piece
     */
    public King(Color color) {
        super(color);
        unicode = 0x2654;
        score = 999;
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
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        if (super.legalMove(board, source, target, checkCheck)) {
            if ((source.getX() == target.getX() || source.getX() - target.getX() == 1 || source.getX() - target.getX() == -1) &&
                    (source.getY() == target.getY() || source.getY() - target.getY() == 1 || source.getY() - target.getY() == -1)) {
                return true;
            } else if (!getHasMoved()) { // if King has not moved (for castling)
                // get row that the castle is taking place in
                if (checkCheck && !board.colorInCheck(color)) {
                    int row = 0;
                    if (getColor() == Color.BLACK) {
                        row = 7;
                    }
                    if (source.getY() == target.getY() && source.getX() - target.getX() == -2) { // if King is moving two to the right
                        if (!board.getBoardSquareAt(source.getX() + 1, row).isOccupied()) { // path is clear
                            if (board.getBoardSquareAt(7, row).isOccupied()) { // if there is a piece in that corner
                                // if right rook has not moved
                                return !board.getBoardSquareAt(7, row).getPiece().getHasMoved();
                            }
                        }
                    } else if (source.getY() == target.getY() && source.getX() - target.getX() == 2) { // if King is moving two to the left
                        if (!board.getBoardSquareAt(source.getX() - 1, row).isOccupied() &&
                                !board.getBoardSquareAt(source.getX() - 3, row).isOccupied()) { // path is clear
                            if (board.getBoardSquareAt(0, row).isOccupied()) { // if there is a piece in that corner
                                // if left rook has not moved
                                return !board.getBoardSquareAt(0, row).getPiece().getHasMoved();
                            }
                        }
                    }
                }

            }
        }
        return false;
    }

}