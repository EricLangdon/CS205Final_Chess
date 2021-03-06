package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;

public class Queen extends Piece {

    /**
     * Constrictor
     *
     * @param color the color of the piece
     */
    public Queen(Color color) {
        super(color);
        unicode = 0x2655;
        score = 90;
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

            // horizontal/vertical logic (same as rook)
            if (source.getX() == target.getX()) { // if in the same column
                if (source.getY() < target.getY()) { // if target is higher on the board
                    for (int i = 1; source.getY() + i < target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX(), source.getY() + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getY() > target.getY()) { // if target is lower on the board
                    for (int i = 1; source.getY() - i > target.getY(); i++) {
                        if (board.getBoardSquareAt(source.getX(), source.getY() - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            } else if (source.getY() == target.getY()) { // if in the same row
                if (source.getX() < target.getX()) { // if target is to the right on the board
                    for (int i = 1; source.getX() + i < target.getX(); i++) {
                        if (board.getBoardSquareAt(source.getX() + i, source.getY()).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getX() > target.getX()) { // if target is to the left on the board
                    for (int i = 1; source.getX() - i > target.getX(); i++) {
                        if (board.getBoardSquareAt(source.getX() - i, source.getY()).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
            }

            // diagonal logic (same as bishop)
            // if lower left or upper right diagonal
            if (source.getX() - target.getX() == source.getY() - target.getY()) {
                if (source.getX() < target.getX()) { // if target is upper right
                    for (int i = 1; source.getX() + i < target.getX(); i++) {
                        if (board.getBoardSquareAt(source.getX() + i, source.getY() + i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getX() > target.getX()) { // if target is lower left
                    for (int i = 1; source.getX() - i > target.getX(); i++) {
                        if (board.getBoardSquareAt(source.getX() - i, source.getY() - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                }
                // if upper left or lower right diagonal
            } else if (source.getX() - target.getX() == (source.getY() - target.getY()) * -1) {
                if (source.getX() < target.getX()) { // if target is lower right
                    for (int i = 1; source.getX() + i < target.getX(); i++) {
                        if (board.getBoardSquareAt(source.getX() + i, source.getY() - i).isOccupied()) {
                            return false;
                        }
                    }
                    return true;
                } else if (source.getX() > target.getX()) { // if target is upper left
                    for (int i = 1; source.getX() - i > target.getX(); i++) {
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