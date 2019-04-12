package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        unicode = 0x2659;
        score = 1;
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
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        //Checks if target is legal first
        if (super.legalMove(board, source, target, checkCheck)) {

            //Creates the inverter that applies the moves to opposite color
            int invert;
            if (color.equals(Color.WHITE)) {
                invert = 1;
            } else {
                invert = -1;
            }

            //Handles pawn logic
            if (target.getY() == source.getY() + invert && target.getX() == source.getX() && target.getPiece() == null) {
                return true;
            } else if (target.getY() == source.getY() + 2 * invert && target.getX() == source.getX() && !board.getBoardSquareAt(source.getX(), source.getY() + invert).isOccupied()
                    && target.getPiece() == null && !getHasMoved()) {
                return true;
            } else if (target.getY() == source.getY() + invert && target.getX() == source.getX() - invert && target.isOccupied() && target.getPiece().getColor() == color.other()) {
                return true;
            } else
                return target.getY() == source.getY() + invert && target.getX() == source.getX() + invert && target.isOccupied() && target.getPiece().getColor() == color.other();
        } else {
            return false;
        }
    }
}