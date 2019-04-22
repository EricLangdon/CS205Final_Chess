package chess.core.game.cpu;

import chess.core.piece.Color;
import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.piece.Queen;
import chess.gui.ChessGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimpleCPU extends CPU {
    private chess.core.piece.Color color;

    /**
     * Default constructor
     * Sets the color of the CPU to black
     */
    public SimpleCPU() {
        this.color = chess.core.piece.Color.BLACK;
    }

    /**
     * Non-Default constructor
     * Sets the color of the CPU to the passed in value
     *
     * @param color
     */
    public SimpleCPU(Color color) {
        this.color = color;
    }

    /**
     * choiceMove method
     * Selects a random piece and a random legal move and sends that move to the
     * move method in Board
     *
     * @param board
     */
    public void choiceMove(Board board) {
        ArrayList<BoardSquare> sources = new ArrayList<>();
        HashMap<Integer, ArrayList<BoardSquare>> targetsMap = new HashMap<>();
        BoardSquare square, randSource, randTarget;
        Random random = new Random();
        int counter = 0;
        int randomNum1 = 0, randomNum2 = 0;
        int temp;

        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                square = board.getBoardSquareAt(i, j);
                if (square.isOccupied() && square.getPiece().getColor() == color) {
                    if (square.getPiece().getAvailableMoves(board, square).size() != 0) {
                        sources.add(square);
                        targetsMap.put(sources.size() - 1, sources.get(counter).getPiece().getAvailableMoves(board, square));
                        counter++;
                    }
                }
            }
        }

        if (sources.size() != 0) {
            randomNum1 = random.nextInt(sources.size());
            randSource = sources.get(randomNum1);
            randomNum2 = random.nextInt(targetsMap.get(randomNum1).size());
            randTarget = targetsMap.get(randomNum1).get(randomNum2);

            board.movePiece(randSource, randTarget);
            if(board.checkPromotion()){
                chess.core.piece.Piece queen =new Queen(color);
                board.replacePawn(queen, randTarget);
            }
        }
    }
}
