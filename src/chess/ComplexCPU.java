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
        HashMap<Integer,ArrayList<BoardSquare>> ownTargetsMap = new HashMap<>();
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
                        ownTargetsMap.put(sources.size() - 1, sources.get(counter).getPiece().getAvailableMoves(board, square));
                        counter++;
                        for (int k=0; k<moves.size(); k++) { // all available moves for this piece
                            if (moves.get(k).isOccupied()) {
                                if (moves.get(k).getPiece().getScore() > max) {
                                    max = moves.get(k).getPiece().getScore();
                                    maxSource = square;
                                    maxTarget = moves.get(k);
                                }
                            }
                        }
                    }
                }
            }
        }

        randomNum1 = random.nextInt(sources.size() - 1);
        randSource = sources.get(randomNum1);
        randomNum2 = ownTargetsMap.get(randomNum1).size() - 1;
        randTarget = ownTargetsMap.get(randomNum1).get(randomNum2);

        if (max > 0) {
            board.movePiece(maxSource, maxTarget);
        } else {
            board.movePiece(randSource, randTarget);
        }
    }

    public void findOppMove (Board board, BoardSquare source, BoardSquare target) {
        // simulate move to predict opponent's move
        // if called recursively, rather than recreate new in function, reset moves on this board?
        Board temp = board;
        temp.movePiece(source, target);
    }

}
