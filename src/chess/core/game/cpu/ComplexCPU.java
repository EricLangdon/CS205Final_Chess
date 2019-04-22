package chess.core.game.cpu;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.piece.Color;
import chess.core.piece.*;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Random;

public class ComplexCPU extends CPU {
    Color color;
    Stage gameStage;

    /**
     * Default constructor
     */
    public ComplexCPU() {
        this.color = Color.BLACK;
        this.gameStage = Stage.OPENING;
    }

    /**
     * Color Constructor
     *
     * @param newColor
     */
    public ComplexCPU(Color newColor) {
        this.color = newColor;
        this.gameStage = Stage.OPENING;
    }

    /**
     * choiceMove
     * analyzes the board and makes educated move based on piece scores
     *
     * @param board game board
     */
    public void choiceMove(Board board) {
        int depth = 2;
        gameStage = Stage.MIDGAME;
        int count = 0;
        for (Piece p : board.getCaptured()) {
            if (p.getColor() == color.other()) {
                count++;
            }
        }
//        if (count < 8) {
//            gameStage = Stage.ENDGAME;
//        }

        if (gameStage == Stage.OPENING) {
            // TODO: opening moves
        } else if (gameStage == Stage.MIDGAME) {
            BoardSquare square;
            ArrayList<MoveScore> sourceScores = new ArrayList<>();
            ArrayList<MoveScore> sourceMaxes = new ArrayList<>();
            ArrayList<TargetScore> targetScores = new ArrayList<>();
            ArrayList<TargetScore> targetMaxes = new ArrayList<>();
            ArrayList<BoardSquare> moves;
            int sourceMax = -9999, targetMax = -9999;
            Random random = new Random();
            int randomNum = 0;

            for (int i = 0; i < Board.NUM_COLS; i++) { // find sources
                for (int j = 0; j < Board.NUM_ROWS; j++) {
                    square = board.getBoardSquareAt(i, j);
                    if (square.isOccupied() && square.getPiece().getColor() == color &&
                            square.getPiece().getAvailableMoves(board, square).size() != 0) {
                        // for each source, choose random of best scores here
                        moves = square.getPiece().getAvailableMoves(board, square);
                        for (BoardSquare m : moves) {
                            targetScores.add(scoreMove(board, square, m, depth));
                        }
                        // targetScores to sourceScores
                        for (TargetScore t : targetScores) {
                            if (t.getScore() == targetMax) {
                                targetMaxes.add(t);
                            } else if (t.getScore() > targetMax) {
                                targetMax = t.getScore();
                                targetMaxes.clear();
                                targetMaxes.add(t);
                            }
                        }
                        if (targetMaxes.size() != 0) {
                            if (targetMaxes.size() == 1) {
                                sourceScores.add(new MoveScore(square, targetMaxes.get(0).getTarget(), targetMax));
                            } else {
                                randomNum = random.nextInt(targetMaxes.size());
                                sourceScores.add(new MoveScore(square, targetMaxes.get(randomNum).getTarget(), targetMax));
                            }
                        }
                        targetScores = new ArrayList<TargetScore>();
                        targetMax = -9999;
                    }
                }
            }
            // cycle scores, filling an arraylist with the maxes, take random of maxes
            for (MoveScore s : sourceScores) {
                if (s.getScore() == sourceMax) {
                    sourceMaxes.add(s);
                } else if (s.getScore() > sourceMax) {
                    sourceMax = s.getScore();
                    sourceMaxes.clear();
                    sourceMaxes.add(s);
                }
            }
            if (sourceMaxes.size() != 0) {
                if (sourceMaxes.size() == 1) {
                    board.movePiece(sourceMaxes.get(0).getSource(), sourceMaxes.get(0).getTarget());
                } else {
                    randomNum = random.nextInt(sourceMaxes.size());
                    board.movePiece(sourceMaxes.get(randomNum).getSource(), sourceMaxes.get(randomNum).getTarget());
                }
            }
        } else if (gameStage == Stage.ENDGAME) {
            // TODO: end game strategy
        }
    }

    /**
     * scoreMove
     * returns a score given a move, with choice of how deep to look
     *
     * @param board  game board
     * @param source source square
     * @param target source target
     * @param depth  how deep to check
     * @return score for given move
     */
    public TargetScore scoreMove(Board board, BoardSquare source, BoardSquare target, int depth) {
        Board tempBoard = new Board(board);
        ArrayList<BoardSquare> moves;
        int sourceMax = 0, oppMaxInt = 0;
        TargetScore moveScore = new TargetScore(target, -9999);
        ArrayList<MoveScore> oppMaxes = new ArrayList<>();

        if (depth == 3) {
            // TODO: fix?
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
            } else {
                moveScore.score = 0;
            }
            // simulate move
            tempBoard.movePiece(tempBoard.getBoardSquareAt(source.getX(), source.getY()), tempBoard.getBoardSquareAt(target.getX(), target.getY()));
            for (BoardSquare bs : tempBoard.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == color.other() &&
                        bs.getPiece().getAvailableMoves(board, bs).size() != 0) {
                    moves = bs.getPiece().getAvailableMoves(tempBoard, bs);
                    for (BoardSquare m : moves) {
                        // analyze next opponent move
                        MoveScore oppMax = new MoveScore(bs, m, 0);
                        if (m.isOccupied() && m.getPiece().getScore() == oppMaxInt) {
                            oppMaxes.add(oppMax);
                        } else if (m.isOccupied() && m.getPiece().getScore() > oppMaxInt) {
                            oppMax.score = m.getPiece().getScore();
                            oppMaxInt = oppMax.getScore();
                            oppMaxes.clear();
                            oppMaxes.add(oppMax);
                        } else if (!m.isOccupied()) {
                            if (!oppMaxes.isEmpty()) {
                                if (oppMaxes.get(0).getScore() == 0) {
                                    oppMaxes.add(oppMax);
                                }
                            } else {
                                oppMaxes.add(oppMax);
                            }
                        }
                    }
                }
            }
            for (MoveScore opp : oppMaxes) {
                // simulate your next move
                Board tempBoard2 = new Board(tempBoard);
                tempBoard2.movePiece(tempBoard2.getBoardSquareAt(opp.getSource().getX(), opp.getSource().getY()), tempBoard2.getBoardSquareAt(opp.getTarget().getX(), opp.getTarget().getY()));
                if (tempBoard2.checkmate(color)) { // if opponent can put you in checkmate
                    moveScore.score -= 1100;
                } else {
                    for (BoardSquare bs2 : tempBoard2.getBoardSquares()) {
                        if (bs2.isOccupied() && bs2.getPiece().getColor() == color &&
                                bs2.getPiece().getAvailableMoves(tempBoard2, bs2).size() != 0) {
                            moves = bs2.getPiece().getAvailableMoves(tempBoard2, bs2);
                            for (BoardSquare m2 : moves) { // all possible domestic moves
                                if (m2.isOccupied() && m2.getPiece().getScore() > sourceMax) {
                                    sourceMax = m2.getPiece().getScore();
                                }
                            }
                        }
                    }
                }
            }
            moveScore.score -= oppMaxes.get(0).getScore();
            moveScore.score += sourceMax;
            return moveScore;

        } else if (depth == 2) {
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
            } else {
                moveScore.score = 0;
            }
            // simulate move
            tempBoard.movePiece(tempBoard.getBoardSquareAt(source.getX(), source.getY()), tempBoard.getBoardSquareAt(target.getX(), target.getY()));
            // checkmate
            if (tempBoard.checkmate(color)) {
                moveScore.score += 1000;
                return moveScore;
            }
            // specific tweaks
            if (tempBoard.colorInCheck(color.other())) {
                moveScore.score += 1;
            } else if (source.getPiece() instanceof King && !source.getPiece().getHasMoved() && !tempBoard.colorInCheck(color) &&
                    target.getX() - source.getX() != 2 && target.getX() - source.getX() != -2) {
                moveScore.score -= 1;
            }
            for (BoardSquare bs : tempBoard.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == color.other() &&
                        bs.getPiece().getAvailableMoves(tempBoard, bs).size() != 0) {
                    moves = bs.getPiece().getAvailableMoves(tempBoard, bs);
                    for (BoardSquare m : moves) {
                        if (m.isOccupied() && m.getPiece().getScore() > sourceMax) {
                            sourceMax = m.getPiece().getScore();
                        }
                    }
                }
            }
            moveScore.score -= sourceMax;
            return moveScore;

        } else { // depth == 1
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
                return moveScore;
            } else {
                moveScore.score = 0;
                return moveScore;
            }
        }
    }
}
