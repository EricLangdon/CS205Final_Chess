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
    int depth;

    /**
     * Default constructor
     */
    public ComplexCPU() {
        this.color = Color.BLACK;
        this.gameStage = Stage.OPENING;
        this.depth = 2;
    }

    /**
     * Color Constructor
     *
     * @param newColor
     */
    public ComplexCPU(Color newColor) {
        this.color = newColor;
        this.gameStage = Stage.OPENING;
        this.depth = 2;
    }

    /**
     * choiceMove
     * analyzes the board and makes educated move based on piece scores
     *
     * @param board game board
     */
    @SuppressWarnings("Duplicates")
    public void choiceMove(Board board) {
        int count = 0;
        // count lost opponent pieces
        for (Piece p : board.getCaptured()) {
            if (p.getColor() == color.other()) {
                count++;
            }
        }
        if (board.getMoves().size() < 7) {
            gameStage = Stage.OPENING;
        } else if (count > 6) {
            gameStage = Stage.ENDGAME;
        } else {
            gameStage = Stage.MIDGAME;
        }

        if (gameStage == Stage.OPENING) {
            //Todo  briefly explain all arraylists
            ArrayList<MoveScore> sourceScores = new ArrayList<>();
            ArrayList<MoveScore> sourceMaxes = new ArrayList<>();
            ArrayList<TargetScore> targetScores = new ArrayList<>();
            ArrayList<TargetScore> targetMaxes = new ArrayList<>();
            ArrayList<BoardSquare> moves;
            int sourceMax = -9999, targetMax = -9999;
            Random random = new Random();
            int randomNum = 0;

            for (BoardSquare square : board.getBoardSquares()) {
                if (square.isOccupied() && square.getPiece().getColor() == color &&
                        square.getPiece().getAvailableMoves(board, square).size() != 0) {
                    //for each piece, score each possible move
                    moves = square.getPiece().getAvailableMoves(board, square);
                    for (BoardSquare m : moves) {
                        targetScores.add(scoreMove(board, square, m, depth));
                        //White openers
                        if (color == Color.WHITE) {
                            //E2 to E4
                            if (square.getX() == 4 && square.getY() == 1 && m.getX() == 4 && m.getY() == 3) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //D2 to D3
                            } else if (square.getX() == 3 && square.getY() == 1 && m.getX() == 3 && m.getY() == 2) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //B2 to B3
                            } else if (square.getX() == 1 && square.getY() == 1 && m.getX() == 1 && m.getY() == 2) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //G2 to G3
                            } else if (square.getX() == 6 && square.getY() == 1 && m.getX() == 6 && m.getY() == 2) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //B1 to C3
                            } else if (square.getX() == 1 && square.getY() == 0 && m.getX() == 2 && m.getY() == 2) {
                                targetScores.get(targetScores.size() - 1).addToScore(1);
                                //G1 to F3
                            } else if (square.getX() == 6 && square.getY() == 0 && m.getX() == 5 && m.getY() == 2) {
                                targetScores.get(targetScores.size() - 1).addToScore(1);
                            }
                            //Black openers
                        } else {
                            //Pawn E7 to E5
                            if (square.getX() == 4 && square.getY() == 6 && m.getX() == 4 && m.getY() == 4) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //Pawn D7 to D6
                            } else if (square.getX() == 3 && square.getY() == 6 && m.getX() == 3 && m.getY() == 4) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //Pawn B7 to B6
                            } else if (square.getX() == 1 && square.getY() == 6 && m.getX() == 1 && m.getY() == 5) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //Pawn G7 to G6
                            } else if (square.getX() == 6 && square.getY() == 6 && m.getX() == 6 && m.getY() == 5) {
                                targetScores.get(targetScores.size() - 1).addToScore(5);
                                //Knight B8 to C6
                            } else if (square.getX() == 1 && square.getY() == 7 && m.getX() == 2 && m.getY() == 5) {
                                targetScores.get(targetScores.size() - 1).addToScore(1);
                                //Knight G8 to F6
                            } else if (square.getX() == 6 && square.getY() == 7 && m.getX() == 5 && m.getY() == 5) {
                                targetScores.get(targetScores.size() - 1).addToScore(1);
                            }
                        }
                    }
                    //take all the best moves for that piece
                    for (TargetScore t : targetScores) {
                        if (t.getScore() == targetMax) {
                            targetMaxes.add(t);
                        } else if (t.getScore() > targetMax) {
                            targetMax = t.getScore();
                            targetMaxes.clear();
                            targetMaxes.add(t);
                        }
                    }
                    //take one random move of thaty piece's best moves
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
            //cycle scores, filling an arraylist with the maxes
            for (MoveScore s : sourceScores) {
                if (s.getScore() == sourceMax) {
                    sourceMaxes.add(s);
                } else if (s.getScore() > sourceMax) {
                    sourceMax = s.getScore();
                    sourceMaxes.clear();
                    sourceMaxes.add(s);
                }
            }
            //choose a random move of the best available
            if (sourceMaxes.size() != 0) {
                if (sourceMaxes.size() == 1) {
                    board.movePiece(sourceMaxes.get(0).getSource(), sourceMaxes.get(0).getTarget());
                    if (board.checkPromotion()) {
                        chess.core.piece.Piece queen = new Queen(color);
                        board.replacePawn(queen, sourceMaxes.get(0).getTarget());
                    }
                } else {
                    randomNum = random.nextInt(sourceMaxes.size());
                    board.movePiece(sourceMaxes.get(randomNum).getSource(), sourceMaxes.get(randomNum).getTarget());
                    if (board.checkPromotion()) {
                        chess.core.piece.Piece queen = new Queen(color);
                        board.replacePawn(queen, sourceMaxes.get(randomNum).getTarget());
                    }
                }
            }
        } else if (gameStage == Stage.MIDGAME || gameStage == Stage.ENDGAME) {
            ArrayList<MoveScore> sourceScores = new ArrayList<>();
            ArrayList<MoveScore> sourceMaxes = new ArrayList<>();
            ArrayList<TargetScore> targetScores = new ArrayList<>();
            ArrayList<TargetScore> targetMaxes = new ArrayList<>();
            ArrayList<BoardSquare> moves;
            int sourceMax = -9999, targetMax = -9999;
            Random random = new Random();
            int randomNum = 0;

            for (BoardSquare square : board.getBoardSquares()) {
                if (square.isOccupied() && square.getPiece().getColor() == color &&
                        square.getPiece().getAvailableMoves(board, square).size() != 0) {
                    // for each piece, score each possible move
                    moves = square.getPiece().getAvailableMoves(board, square);
                    for (BoardSquare m : moves) {
                        targetScores.add(scoreMove(board, square, m, depth));
                    }
                    // take all the best moves for that piece
                    for (TargetScore t : targetScores) {
                        if (t.getScore() == targetMax) {
                            targetMaxes.add(t);
                        } else if (t.getScore() > targetMax) {
                            targetMax = t.getScore();
                            targetMaxes.clear();
                            targetMaxes.add(t);
                        }
                    }
                    // take one random move of that piece's best moves
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
            // cycle scores, filling an arraylist with the maxes
            for (MoveScore s : sourceScores) {
                if (s.getScore() == sourceMax) {
                    sourceMaxes.add(s);
                } else if (s.getScore() > sourceMax) {
                    sourceMax = s.getScore();
                    sourceMaxes.clear();
                    sourceMaxes.add(s);
                }
            }
            // choose random move of the best available
            if (sourceMaxes.size() != 0) {
                if (sourceMaxes.size() == 1) {
                    board.movePiece(sourceMaxes.get(0).getSource(), sourceMaxes.get(0).getTarget());
                    if (board.checkPromotion()) {
                        chess.core.piece.Piece queen = new Queen(color);
                        board.replacePawn(queen, sourceMaxes.get(0).getTarget());
                    }
                } else {
                    randomNum = random.nextInt(sourceMaxes.size());
                    board.movePiece(sourceMaxes.get(randomNum).getSource(), sourceMaxes.get(randomNum).getTarget());
                    if (board.checkPromotion()) {
                        chess.core.piece.Piece queen = new Queen(color);
                        board.replacePawn(queen, sourceMaxes.get(randomNum).getTarget());
                    }
                }
            }
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
        int sourceMax = 0, oppMaxInt = 0, sourceMin = 9999;
        TargetScore moveScore = new TargetScore(target, -9999);
        ArrayList<MoveScore> oppMaxes = new ArrayList<>();
        ArrayList<Integer> sourceMaxes = new ArrayList<>();

        if (depth == 3) {
            // tries to look an extra move ahead
            // immediate score
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
            } else {
                moveScore.score = 0;
            }
            // simulate move
            tempBoard.getBoardSquareAt(target.getX(), target.getY()).setPiece(tempBoard.getBoardSquareAt(source.getX(), source.getY()).getPiece());
            tempBoard.getBoardSquareAt(source.getX(), source.getY()).setPiece(null);
            tempBoard.getBoardSquareAt(target.getX(), target.getY()).getPiece().setHasMoved(true);
            for (BoardSquare bs : tempBoard.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == color.other() &&
                        bs.getPiece().getAvailableMoves(board, bs).size() != 0) {
                    moves = bs.getPiece().getAvailableMoves(tempBoard, bs);
                    for (BoardSquare m : moves) {
                        // analyze next opponent move
                        MoveScore oppMax = new MoveScore(bs, m, 0);
                        if (m.isOccupied() && m.getPiece().getScore() == oppMaxInt) {
                            oppMax.score = m.getPiece().getScore();
                            oppMaxes.add(oppMax);
                        } else if (m.isOccupied() && m.getPiece().getScore() > oppMaxInt) {
                            oppMax.score = m.getPiece().getScore();
                            oppMaxInt = oppMax.getScore();
                            oppMaxes.clear();
                            oppMaxes.add(oppMax);
                        }
                    }
                }
            }
            if (oppMaxes.size() > 0) {
                for (int i = 0; i < oppMaxes.size(); i++) {
                    // simulate opponent next move
                    Board tempBoard2 = new Board(tempBoard);
                    tempBoard2.getBoardSquareAt(oppMaxes.get(i).getTarget().getX(), oppMaxes.get(i).getTarget().getY()).setPiece(tempBoard2.getBoardSquareAt(oppMaxes.get(i).getSource().getX(), oppMaxes.get(i).getSource().getY()).getPiece());
                    tempBoard2.getBoardSquareAt(oppMaxes.get(i).getSource().getX(), oppMaxes.get(i).getSource().getY()).setPiece(null);
                    tempBoard2.getBoardSquareAt(oppMaxes.get(i).getTarget().getX(), oppMaxes.get(i).getTarget().getY()).getPiece().setHasMoved(true);
                    if (tempBoard2.checkmate(color)) {
                        moveScore.score -= 1100;
                        i = oppMaxes.size();
                    } else {
                        for (BoardSquare bs2 : tempBoard2.getBoardSquares()) {
                            if (bs2.isOccupied() && bs2.getPiece().getColor() == color &&
                                    bs2.getPiece().getAvailableMoves(tempBoard2, bs2).size() != 0) {
                                moves = bs2.getPiece().getAvailableMoves(tempBoard2, bs2);
                                for (BoardSquare m2 : moves) { // all possible domestic moves
                                    if (m2.isOccupied() && m2.getPiece().getScore() - oppMaxInt > sourceMax) {
                                        sourceMax = m2.getPiece().getScore() - oppMaxInt;
                                    }
                                }
                            }
                        }
                        sourceMaxes.add(sourceMax);
                        sourceMax = 0;
                    }
                }
                for (int i = 0; i < sourceMaxes.size(); i++) {
                    if (sourceMaxes.get(i) < sourceMin) {
                        sourceMin = sourceMaxes.get(i).intValue();
                    }
                }
                moveScore.score += sourceMin;
            }
            return moveScore;

        } else if (depth == 2) {
            // immediate score
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
            } else {
                moveScore.score = 0;
            }
            // simulate move
            tempBoard.movePiece(tempBoard.getBoardSquareAt(source.getX(), source.getY()), tempBoard.getBoardSquareAt(target.getX(), target.getY()));
            // checkmate
            if (tempBoard.checkmate(color.other())) {
                moveScore.score += 1000;
                return moveScore;
            }
            // specific tweaks
            if (tempBoard.colorInCheck(color.other())) {
                moveScore.score += 1;
            }
            if (source.getPiece() instanceof King) {
                if (!source.getPiece().getHasMoved()) {
                    // castling
                    if (target.getX() - source.getX() != 2 && target.getX() - source.getX() != -2) {
                        moveScore.score -= 1;
                    } else {
                        moveScore.score += 9;
                    }
                }
            }
            if (source.getPiece() instanceof Rook) {
                if (tempBoard.colorInCheck(color.other())) {
                    moveScore.score += 4;
                }
            }
            if (source.getPiece() instanceof Bishop || source.getPiece() instanceof Knight) {
                if (!source.getPiece().getHasMoved()) {
                    moveScore.score += 2;
                }
                if (tempBoard.colorInCheck(color.other())) {
                    moveScore.score += 4;
                }
            } else if (source.getPiece() instanceof Pawn) {
                if (target.getY() == 0 || target.getY() == 7) {
                    moveScore.score += 79;
                }
            }
            if (gameStage == Stage.ENDGAME) {
                // end game strategy
                // Pawn advancement
                if (source.getPiece() instanceof Pawn) {
                    if (color == Color.BLACK) {
                        moveScore.score += 8 - target.getY();
                    } else {
                        moveScore.score += target.getY() + 1;
                    }
                }
            }
            // check opponent's possible reactions
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
            // just take immediate score
            if (target.isOccupied()) {
                moveScore.score = target.getPiece().getScore();
                return moveScore;
            } else {
                moveScore.score = 0;
                return moveScore;
            }
        }
    }

    /**
     * setter for depth (difficulty)
     * @param depth
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * getter for depth
     * @return
     */
    public int getDepth() {
        return depth;
    }
}
