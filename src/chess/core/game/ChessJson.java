package chess.core.game;

import chess.core.board.Move;
import chess.core.piece.Color;

import java.util.ArrayList;

public class ChessJson {
    private ArrayList<Move> moveList;
    private ChessClock player1Clock;
    private ChessClock player2Clock;
    private Game.GameMode mode;
    private Color player1Color;
    private Color player2Color;
    private int player1Score;
    private int player2Score;

    public Game.GameMode getMode() { return mode; }

    public void setMode(Game.GameMode mode) { this.mode = mode; }

    public Color getPlayer1Color() { return player1Color; }

    public void setPlayer1Color(Color player1Color) { this.player1Color = player1Color; }

    public Color getPlayer2Color() { return player2Color; }

    public void setPlayer2Color(Color player2Color) { this.player2Color = player2Color; }

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

    public int getPlayer1Score() {return player1Score;}

    public void setPlayer1Score(int s) { this.player1Score = s;}

    public int getPlayer2Score() {return player2Score;}

    public void setPlayer2Score(int s) { this.player2Score = s;}


    //Score?
    //captured pieces?
    //Current turn
}
