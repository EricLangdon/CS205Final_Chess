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
        HashMap<Integer,ArrayList<BoardSquare>> targetsMap = new HashMap<>();
        HashMap<Integer,ArrayList<MoveScore>> scoresMap = new HashMap<>();
        ArrayList<BoardSquare> sources = new ArrayList<>();
        ArrayList<BoardSquare> moves;
        BoardSquare square, randSource, randTarget;
        BoardSquare maxSource = new BoardSquare();
        BoardSquare maxTarget = new BoardSquare();
        int counter = 0, max = 0;

        Random random = new Random();
        int randomNum1 = 0, randomNum2;

        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                square = board.getBoardSquareAt(i, j);
                if (square.isOccupied() && square.getPiece().getColor() == color) {
                    if (square.getPiece().getAvailableMoves(board, square).size() != 0) {
                        sources.add(square);
                        moves = square.getPiece().getAvailableMoves(board, square);
                        targetsMap.put(sources.size() - 1, sources.get(counter).getPiece().getAvailableMoves(board, square));
                        counter++;
                        for (int k=0; k<moves.size(); k++) { // all available moves for this piece
                            if (moves.get(k).isOccupied()) {
                                if (moves.get(k).getPiece().getScore() > max) {
                                    max = moves.get(k).getPiece().getScore();
                                    maxSource = square;
                                    maxTarget = moves.get(k);
                                    // TODO: scoresMap.put  will replace targetsMap
                                }
                            }
                        }
                    }
                }
            }
        }

        randomNum1 = random.nextInt(sources.size() - 1);
        randSource = sources.get(randomNum1);
        randomNum2 = targetsMap.get(randomNum1).size() - 1;
        randTarget = targetsMap.get(randomNum1).get(randomNum2);

        if (max > 0) {
            board.movePiece(maxSource, maxTarget);
        } else {
            board.movePiece(randSource, randTarget);
        }
    }

    public MoveScore findMoveScore(Board board, BoardSquare source, BoardSquare target) {
        MoveScore tempScore = new MoveScore();
        BoardSquare square = new BoardSquare();
        int currScore = 0;
        Board tempBoard = new Board(board);
        Color moverColor = source.getPiece().getColor();

        if (target.isOccupied()) {
            currScore = target.getPiece().getScore();
        }

        // simulate move to score possible reaction moves
        tempBoard.movePiece(source, target);

        for (int i=0; i<tempBoard.NUM_ROWS; i++) {
            for (int j=0; j<tempBoard.NUM_COLS; j++) {
                square = tempBoard.getBoardSquareAt(i, j);
                if (square.isOccupied() && square.getPiece().getColor() == moverColor.other()) {
                    //
                }
            }
        }

        tempScore.score = currScore;
        tempScore.target = target;

        return tempScore;
    }
}

class MoveScore {
    BoardSquare target;
    int score;

    MoveScore() {
    }
}
