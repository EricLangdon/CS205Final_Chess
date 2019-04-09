package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ComplexCPU {
    Color color;

    /**
     * Default constructor
     */
    public ComplexCPU() {
        this.color = Color.BLACK;
    }

    /**
     * choiceMove
     * analyzes the board and makes educated move based on piece scores
     */
    public void choiceMove(Board board) {
        // TODO: clean

        int depth = 1;

        BoardSquare square;
        ArrayList<MoveScore> sourceScores = new ArrayList<>();
        ArrayList<MoveScore> sourceMaxes = new ArrayList<>();
        ArrayList<TargetScore> targetScores = new ArrayList<>();
        ArrayList<BoardSquare> moves;
        TargetScore targetMax = new TargetScore();
        targetMax.score = -127;
        int sourceMax = -127;

        for (int i = 0; i < Board.NUM_COLS; i++) { // find sources
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                square = board.getBoardSquareAt(i, j);
                if (square.isOccupied() && square.getPiece().getColor() == color && square.getPiece().getAvailableMoves(board, square).size() != 0) {
                    // for each source, choose random of best scores here
                    moves = square.getPiece().getAvailableMoves(board, square);
                    for (BoardSquare k : moves) {
                        targetScores.add(scoreMove(board, square, k, depth));
                    }
                    // targetScores to sourceScores
                    for (int k = 0; k < targetScores.size(); k++) {
                        if (targetScores.get(k).getScore() > targetMax.score) {
                            targetMax.score = targetScores.get(k).getScore();
                            targetMax.target = targetScores.get(k).getTarget();
                        }
                    }
                    sourceScores.add(new MoveScore(square, targetMax.getTarget(), targetMax.getScore()));
                    targetScores = new ArrayList<TargetScore>();
                    targetMax.score = -127;
                }
            }
        }

        // cycle scores, filling an arraylist with the maxes, take random of maxes
        for (int i = 0; i < sourceScores.size(); i++) {
            if (sourceScores.get(i).getScore() == sourceMax) {
                sourceMaxes.add(sourceScores.get(i));
            } else if (sourceScores.get(i).getScore() > sourceMax) {
                sourceMax = sourceScores.get(i).getScore();
                sourceMaxes.clear();
                sourceMaxes.add(sourceScores.get(i));
            }
        }

        Random random = new Random();
        int randomNum = 0;

        if (sourceMaxes.size() == 1) {
            board.movePiece(sourceMaxes.get(0).getSource(), sourceMaxes.get(0).getTarget());
        } else {
            randomNum = random.nextInt(sourceMaxes.size());
            board.movePiece(sourceMaxes.get(randomNum).getSource(), sourceMaxes.get(randomNum).getTarget());
        }
    }

    /**
     * scoreMove
     * returns a score given a move, with choice of how deep to look
     * @param board
     * @param source
     * @param target
     * @param depth
     * @return
     */
    public TargetScore scoreMove(Board board, BoardSquare source, BoardSquare target, int depth) {
        Board tempBoard = new Board(board);
        BoardSquare tempSource = new BoardSquare(source);
        BoardSquare tempTarget = new BoardSquare(target);
        TargetScore moveScore = new TargetScore(target, -127);

        if (depth > 1) {
            // TODO: implement
            depth--;
            //scoreMove(tempBoard, tempSource, tempTarget, depth);
        } else {
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
                moveScore.target = target;
                return moveScore;
            } else {
                moveScore.score = 0;
                return moveScore;
            }
        }


        return moveScore;
    }

    /**
     * choiceMove
     * analyzes the board and makes educated move based on piece scores
     */

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
    }
}
