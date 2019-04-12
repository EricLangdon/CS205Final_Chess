package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;

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
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        if (super.legalMove(board, source, target, checkCheck)) {
            if (source.getX() - target.getY() == source.getY() - target.getY()) { // if lower left or upper right diagonal
                if (source.getX() < target.getY()) { // if target is upper right
                    for (int i = 1; source.getX() + i < target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX() + i, source.getY() + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getX() > target.getY()) { // if target is lower left
                    for (int i = 1; source.getX() - i > target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX() - i, source.getY() - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (source.getX() - target.getY() == (source.getY() - target.getY()) * -1) { // if upper left or lower right diagonal
                if (source.getX() < target.getY()) { // if target is lower right
                    for (int i = 1; source.getX() + i < target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX() + i, source.getY() - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getX() > target.getY()) { // if target is upper left
                    for (int i = 1; source.getX() - i > target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX() - i, source.getY() + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

}
