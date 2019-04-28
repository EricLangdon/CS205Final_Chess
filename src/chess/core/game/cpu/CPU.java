package chess.core.game.cpu;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.piece.Color;

public abstract class CPU {

    public void choiceMove(Board board) {

    }

    class MoveScore {
        BoardSquare source;
        BoardSquare target;
        int score;

        MoveScore() {
        }

        MoveScore(BoardSquare source, BoardSquare target, int score) {
            this.source = source;
            this.target = target;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public BoardSquare getTarget() {
            return target;
        }

        public BoardSquare getSource() {
            return source;
        }
    }

    class TargetScore {
        BoardSquare target;
        int score;

        TargetScore() {
        }

        TargetScore(BoardSquare target, int score) {
            this.target = target;
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        public BoardSquare getTarget() {
            return target;
        }

        public void setScore(int score) {
            this.score = score;
        }
        public void addToScore(int score){
            this.score+=score;
        }
    }

}
