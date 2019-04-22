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
     * @return the source boardsquare
     */
    public BoardSquare getSource() {
        return source;
    }

    /**
     * Copies the given boardsquare to source
     *
     * @param source the source boardsquare
     */
    public void setSource(BoardSquare source) {
        this.source = new BoardSquare(source);
    }

    /**
     * @return the target boardsquare
     */
    public BoardSquare getTarget() {
        return target;
    }

    /**
     * Copies the given boardsquare to target
     *
     * @param target the target boardsquare
     */
    public void setTarget(BoardSquare target) {
        this.target = new BoardSquare(target);
    }

    /**
     * @return the captured piece, null if no piece captured
     */
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    /**
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

    @Override
    public String toString() {
        String s;
        s = source.getPiece().getUnicode() + " at " + source.toString() + " moves to " + target.toString();
        if (capturedPiece != null) {
            s += " taking a " + capturedPiece.getUnicode();
        }
        return s;
    }
}
