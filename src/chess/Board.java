package chess;
import java.util.ArrayList;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;

    ArrayList<ArrayList<BoardSquare>> board = new ArrayList<>(NUM_ROWS);
    ArrayList<Piece> captured = new ArrayList<>();
    ArrayList<Piece> pieces = new ArrayList<>();


    /**
     * Default Constructor
     */
    Board () {
        createBoard(board);
        //initialize AL of pieces
        fillBoard();
    }

    /**
     * createBoard
     * @param board the board object
     */
    private void createBoard(ArrayList<ArrayList<BoardSquare>> board) {

    }

    /**
     * getCaptured
     * @return an arraylist of the pieces that have been taken out of play
     */
    public ArrayList<Piece> getCaptured() {
        return captured;
    }

    /**
     * getBoardSquareAt
     * @param x the x-coor of the BoardSquare
     * @param y the y-coor of the BoardSquare
     * @return the BoardSquare at (x,y)
     */
    public BoardSquare getBoardSquareAt(int x, int y) {
        //TODO: verify correctness?
        return board.get(x).get(y);
    }

    /**
     * movePiece
     * @param source where the piece is coming from
     * @param target where the piece is trying to move
     * @return true if the piece is successfully moved, false if the move failed
     */
    public boolean movePiece(BoardSquare source, BoardSquare target) {
        // TODO: implement
        return false;
    }

    /**
     * colorInCheck
     * @param color
     * @return true if the player of 'color' is in check
     */
    public boolean colorInCheck(Color color) {
        //TODO: implement
        return false;
    }

    /**
     * isCaptured
     * @param p the piece that has been captured
     */
    public void pieceCaptured(Piece p){
        pieces.remove(p);
        captured.add(p);
    }
}
