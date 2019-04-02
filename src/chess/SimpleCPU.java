package chess;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimpleCPU {
    private Color color;

    /**
     * Default constructor
     * Sets the color of the CPU to black
     */
    public SimpleCPU() {
        this.color = Color.BLACK;
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
        ArrayList<BoardSquare> sources =new ArrayList<>();
        HashMap<Integer,ArrayList<BoardSquare>> targetsMap = new HashMap<>();
        BoardSquare square, randSource, randTarget;
        Random random= new Random();
        int counter=0;
        int randomNum1=0, randomNum2;

        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                square = board.getBoardSquareAt(i, j);
                if (square.isOccupied() && square.getPiece().getColor() == color) {
                    if(square.getPiece().getAvailableMoves(board,square).size()!=0) {
                        sources.add(square);
                        targetsMap.put(sources.size() - 1, sources.get(counter).getPiece().getAvailableMoves(board, square));
                        counter++;
                    }
                }
            }
        }
        randomNum1=random.nextInt(sources.size()-1);
        randSource=sources.get(randomNum1);
        randomNum2=targetsMap.get(randomNum1).size()-1;
        randTarget=targetsMap.get(randomNum1).get(randomNum2);

        board.movePiece(randSource, randTarget);
    }




        //scrap all this stuff

        /*Random random = new Random();
        int randomNum=0;
        BoardSquare target;
        ArrayList<Piece> piecesCPU = new ArrayList<>();
        ArrayList<BoardSquare> possibleMoves = new ArrayList<>();

        for (int i = 0; i == pieces.size(); i++) {
            if (pieces.get(i).getColor() == color) {
                piecesCPU.add(pieces.get(i));
            }
        }

        for (int j = 0; j == piecesCPU.size(); j++) {
            possibleMoves.addAll(piecesCPU.get(j).getAvailableMoves(board, source));
        }

        randomNum=random.nextInt(possibleMoves.size());
        target=possibleMoves.get(randomNum);
        return target;*/



}
