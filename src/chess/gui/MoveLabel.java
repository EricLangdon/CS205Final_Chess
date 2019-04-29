package chess.gui;


import chess.core.board.Move;
import javafx.scene.control.Label;

/**
 * Label that has a move object, to be used in game log
 */
public class MoveLabel extends Label {
    private Move move;

    /**
     * Create a new label with the move as text
     *
     * @param move the move
     */
    public MoveLabel(Move move) {
        super(move.toString());
        this.move = move;
    }

    /**
     * @return the move used by the label
     */
    public Move getMove() {
        return this.move;
    }
}
