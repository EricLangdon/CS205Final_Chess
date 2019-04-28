package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.board.Move;

import java.util.ArrayList;


public class Pawn extends Piece {

    //Boolean flag for pawn ability to make en passant move
    public boolean enPassant=false;

    /**
     * Pawn constructor
     *
     * @param color The color of the pawn
     */
    public Pawn(Color color) {
        super(color);
        unicode = 0x2659;
        score = 10;
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
    @SuppressWarnings("Duplicates")
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        //Checks if target is legal first
        if (super.legalMove(board, source, target, checkCheck)) {
            //Gets the previous moves
            ArrayList<Move> moves = board.getMoves();

            //Creates the inverter that applies the moves to opposite color
            int invert;
            if (color.equals(Color.WHITE)) {
                invert = 1;
            } else {
                invert = -1;
            }

            //Handles pawn logic
            //Move forward one
            if (target.getY() == source.getY() + invert && target.getX() == source.getX() && target.getPiece() == null) {
                return true;
            }
            //Move forward two
            else if (target.getY() == source.getY() + 2 * invert && target.getX() == source.getX()
                    && !board.getBoardSquareAt(source.getX(), source.getY() + invert).isOccupied() && target.getPiece() == null && !getHasMoved()) {
                return true;
            }
            //Move to take left
            else if (target.getY() == source.getY() + invert && target.getX() == source.getX() - invert && target.isOccupied()
                    && target.getPiece().getColor() == color.other()) {
                return true;
            }
            //Move to take right
            else if (target.getY() == source.getY() + invert && target.getX() == source.getX() + invert && target.isOccupied()
                    && target.getPiece().getColor() == color.other()) {
                return true;
            }
            //En passant move for white
            else if (source.getY() == 4 && moves.size() != 0) {
                BoardSquare lastSource = moves.get(moves.size() - 1).getSource();
                BoardSquare lastTarget = moves.get(moves.size() - 1).getTarget();
                //Target is right
                if (lastSource.getY() == 6 && lastTarget.getPiece() instanceof Pawn && lastTarget.getX() == source.getX() + invert
                        && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert && target.getX() == source.getX() + invert) {
                    enPassant=true;
                    return true;
                }
                //Target is left
                else if (lastSource.getY() == 6 && lastTarget.getPiece() instanceof Pawn && lastTarget.getX() == source.getX() - invert
                        && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert && target.getX() == source.getX() - invert) {
                    enPassant=true;
                    return true;
                }
            }
            //En passant move for black
            else if (source.getY() == 3 && moves.size() != 0) {
                BoardSquare lastSource = moves.get(moves.size() - 1).getSource();
                BoardSquare lastTarget = moves.get(moves.size() - 1).getTarget();
                // Target is left
                if (lastSource.getY() == 1 && lastTarget.getPiece() instanceof Pawn && lastTarget.getX() == source.getX() + invert
                        && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert && target.getX() == source.getX() + invert) {
                    enPassant=true;
                    return true;
                }
                //Target is right
                else if (lastSource.getY() == 1 && lastTarget.getPiece() instanceof Pawn && lastTarget.getX() == source.getX() - invert
                        && lastTarget.getY() == source.getY() && target.getY() == source.getY() + invert && target.getX() == source.getX() - invert) {
                    enPassant=true;
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * getEnPassent
     *
     * @return the flag if the en passant move is possible
     */
    public boolean getEnPassant(){ return enPassant; }

    /**
     * setEnPassent
     *
     * @param enPassant flag if the en passant move is possible
     */
    public void setEnPassant(boolean enPassant){this.enPassant=enPassant;}
}
