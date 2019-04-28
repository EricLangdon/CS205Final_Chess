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

        /**
         * Non-Defauot constructor
         *
         * @param source where the piece was
         * @param target where the piece is placed
         * @param score the score of the move
         */
        MoveScore(BoardSquare source, BoardSquare target, int score) {
            this.source = source;
            this.target = target;
            this.score = score;
        }

        /**
         * getScore
         *
         * @return the score of the move
         */
        public int getScore() {
            return score;
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
         * getSource
         *
         * @return the source boardsquare
         */
        public BoardSquare getSource() {
            return source;
        }
    }

    class TargetScore {
        BoardSquare target;
        int score;

        TargetScore() {
        }

        /**
         * Non-default constructor
         *
         * @param target boardsquare
         * @param score of the target
         */
        TargetScore(BoardSquare target, int score) {
            this.target = target;
            this.score = score;
        }

        /**
         * getScore
         *
         * @return the score of the target
         */
        public int getScore() {
            return score;
        }

        /**
         * getTarget
         *
         * @return boardsquare location of the target
         */
        public BoardSquare getTarget() {
            return target;
        }

        /**
         * setScore
         *
         * @param score of the target
         */
        public void setScore(int score) {
            this.score = score;
        }

        /**
         * addToScore
         *
         * @param score is added to the current score of the target
         */
        public void addToScore(int score){
            this.score+=score;
        }
    }
}
