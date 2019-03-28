package chess;

import java.util.ArrayList;

public abstract class Piece {
    public static final int SCORE = 0;
    private Color color;

    /**
     * Default Constructor
     * Sets the default Piece color to white
     */
    Piece(){this.color = white; }

     */
    /**
     * Constructor
     * @param color the color of the piece
     */
    Piece(Color color) {
        this.color = color;
    }

    /**
     * Check if the given move is legal given the board and source and target squares. To be overridden in piece subclasses
     * @param board  the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return if the given move is legal
     */
    abstract public boolean legalMove(Board board, BoardSquare source, BoardSquare target);

    /**
     * Get list of legal moves for a piece
     * @param board  the board
     * @param source the piece to check movement
     * @return an arraylist of boardsquares that the piece occupying source can move to
     */
    public ArrayList<BoardSquare> getAvailableMoves(Board board, BoardSquare source) {
        ArrayList<BoardSquare> squares = new ArrayList<>();
        BoardSquare sq;
        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                sq = board.getBoardSquareAt(i, j);
                if (legalMove(board, source, sq)) {
                    squares.add(sq);
                }
            }
        }
        return squares;
    }

    /**
     * @return the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

}