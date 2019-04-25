package chess.core.game;

import chess.core.board.Move;

import java.util.ArrayList;

public class ChessJson {
    private ArrayList<Move> moveList;
    private ChessClock player1Clock;
    private ChessClock player2Clock;

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    public void setMoveList(ArrayList<Move> moveList) {
        this.moveList = moveList;
    }

    public ChessClock getPlayer1Clock() {
        return player1Clock;
    }

    public void setPlayer1Clock(ChessClock player1Clock) {
        this.player1Clock = player1Clock;
    }

    public ChessClock getPlayer2Clock() {
        return player2Clock;
    }

    public void setPlayer2Clock(ChessClock player2Clock) {
        this.player2Clock = player2Clock;
    }

    //colors?
    //Score?
    //captured pieces?
    //Current turn
}
