package chess.core.board;

import chess.core.piece.Piece;

public class Move implements Cloneable {
    private BoardSquare source;
    private BoardSquare target;
    private Piece capturedPiece;

    /**
     * Constructor; create a new move with copies of each boardsquare and an optional captured piece
     *
     * @param source        the source boardsquare
     * @param target        the target boarsquare
     * @param capturedPiece the captured piece, null if no piece captured
     */
    public Move(BoardSquare source, BoardSquare target, Piece capturedPiece) {
        this.source = new BoardSquare(source);
        this.target = new BoardSquare(target);
        this.capturedPiece = capturedPiece;
    }

    /**
     * Constructor; calls other constructor with no captured piece
     *
     * @param source the source boardsquare
     * @param target the target boardsquare
     */
    public Move(BoardSquare source, BoardSquare target) {
        this(source, target, null);
    }

    /**
     * getSource
     *
     * @return the source boardsquare
     */
    public BoardSquare getSource() {
        return source;
    }

    /**
     * setSource
     * Copies the given boardsquare to source
     *
     * @param source the source boardsquare
     */
    public void setSource(BoardSquare source) {
        this.source = new BoardSquare(source);
    }

    /**
     * getTarget
     *
     * @return the target boardsquare
     */
    public BoardSquare getTarget() {
        return target;
    }

    /**
     * settarget
     * Copies the given boardsquare to target
     *
     * @param target the target boardsquare
     */
    public void setTarget(BoardSquare target) {
        this.target = new BoardSquare(target);
    }

    /**
     * getCaputredPiece
     *
     *  @return the captured piece, null if no piece captured
     */
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    /**
     * setCapturedPiece
     * Set the captured piece, cloning it from the passed reference
     *
     * @param capturedPiece the captured piece, null if no piece captured
     */
    public void setCapturedPiece(Piece capturedPiece) {
        if (capturedPiece != null) {
            this.capturedPiece = capturedPiece.clone();
        } else {
            this.capturedPiece = null;
        }
    }

    /**
     * Move toString
     *
     * @return prints the piece, the move source to target, and the captured piece
     */
    @Override
    public String toString() {
        String s;
        s = source.getPiece().getUnicode() + " at " + source.toString() + " moves to " + target.toString();
        if (capturedPiece != null) {
            s += " taking a " + capturedPiece.getUnicode();
        }
        return s;
    }

    /**
     * equals
     *
     * @param other the other move
     * @return true if the source, target, and captured piece are all equal
     */
    public boolean equals(Move other) {
        return this.source == other.source && this.target == other.target && this.capturedPiece == other.capturedPiece;
    }
}
