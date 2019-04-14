package chess.core.board;

import chess.core.piece.Piece;

public class Move implements Cloneable {
    private BoardSquare source;
    private BoardSquare target;
    private Piece capturedPiece;

    public Move(BoardSquare source, BoardSquare target, Piece capturedPiece) {
        this.source = source;
        this.target = target;
        this.capturedPiece = capturedPiece;
    }

    public Move(BoardSquare source, BoardSquare target) {
        this.source = source;
        this.target = target;
    }

    public BoardSquare getSource() {
        return source;
    }

    public void setSource(BoardSquare source) {
        this.source = source;
    }

    public BoardSquare getTarget() {
        return target;
    }

    public void setTarget(BoardSquare target) {
        this.target = target;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }
}
