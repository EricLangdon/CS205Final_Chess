package chess;

import java.util.ArrayList;

public class Queen extends Piece {
    public static final int SCORE = 9;

    /**
     * legalMove
     * Check if the given move is legal for the Bishop given the board source and target squares.
     * @param board the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean true if move is legal
     */
    public boolean legalMove (ChessBoard board, BoardSquare source, BoardSquare target){
        // TODO: implement
    }

    /**
     * getAvailableMoves
     * Returns list of all legal moves available to the Bishop
     * @param board the board
     * @param source boardsquare where the piece currently is located
     * @return an arraylist of all boardsquares that the Bishop can legally move to
     */
    public ArrayList<BoardSquare> getAvailableMoves(ChessBoard board, BoardSquare source){
        // TODO: implement
    }
}