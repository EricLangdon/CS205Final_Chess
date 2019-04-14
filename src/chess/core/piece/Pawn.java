package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.board.Move;

import java.util.ArrayList;


public class Pawn extends Piece {

    public boolean enPassant=false;

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

            ArrayList<Move> moves = board.getMoves();

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
            } else if (target.getY() == source.getY() + invert && target.getX() == source.getX() + invert && target.isOccupied() && target.getPiece().getColor() == color.other()) {
                return true;
            } else if (source.getY() == 4 && moves.size() != 0) {
                BoardSquare lastSource = moves.get(moves.size() - 1).getSource();
                BoardSquare lastTarget = moves.get(moves.size() - 1).getTarget();
                if (lastSource.getY() == 6 && lastTarget.getX() == source.getX() + invert && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert
                        && target.getX() == source.getX() + invert) {
                    enPassant=true;
                    return true;
                } else if (lastSource.getY() == 6 && lastTarget.getX() == source.getX() - invert && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert
                        && target.getX() == source.getX() - invert) {
                    enPassant=true;
                    return true;
                }
            } else if (source.getY() == 3 && moves.size() != 0) {
                BoardSquare lastSource = moves.get(moves.size() - 1).getSource();
                BoardSquare lastTarget = moves.get(moves.size() - 1).getTarget();
                if (lastSource.getY() == 1 && lastTarget.getX() == source.getX() + invert && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert
                        && target.getX() == source.getX() + invert) {
                    enPassant=true;
                    return true;
                } else if (lastSource.getY() == 1 && lastTarget.getX() == source.getX() - invert && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert
                        && target.getX() == source.getX() - invert) {
                    enPassant=true;
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean getEnPassant(){ return enPassant; }

    public void setEnPassant(boolean enPassant){this.enPassant=enPassant;}
}
