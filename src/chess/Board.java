package chess;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;

    private ArrayList<ArrayList<BoardSquare>> board = new ArrayList<>(NUM_ROWS);
    private ArrayList<Piece> captured = new ArrayList<>();
    private ArrayList<Piece> pieces = new ArrayList<>();

    private ArrayList<BoardSquare> highlightedSquares = new ArrayList<>();
    private BoardSquare selectedSquare;


    /**
     * Default Constructor
     * Sets up boardSquares and pieces on the board
     */
    Board () {
        // BoardSquares
        for (int i=0; i<NUM_ROWS; i++) {
            board.add(new ArrayList<>(NUM_COLS));
            for (int j=0; j<NUM_COLS; j++) {
                BoardSquare boardSquare = new BoardSquare(i, j);
                board.get(i).add(j, boardSquare);
            }
        }

        // Pawns
        for (int i=0; i<NUM_COLS; i++) {
            getBoardSquareAt(i, 1).addPiece(new Pawn(Color.WHITE));
            getBoardSquareAt(i, 6).addPiece(new Pawn(Color.BLACK));
        }
        // Rooks
        getBoardSquareAt(0, 0).addPiece(new Rook(Color.WHITE));
        getBoardSquareAt(7, 0).addPiece(new Rook(Color.WHITE));
        getBoardSquareAt(0, 7).addPiece(new Rook(Color.BLACK));
        getBoardSquareAt(7, 7).addPiece(new Rook(Color.BLACK));
        // Knights
        getBoardSquareAt(1, 0).addPiece(new Knight(Color.WHITE));
        getBoardSquareAt(6, 0).addPiece(new Knight(Color.WHITE));
        getBoardSquareAt(1, 7).addPiece(new Knight(Color.BLACK));
        getBoardSquareAt(6, 7).addPiece(new Knight(Color.BLACK));
        // Bishops
        getBoardSquareAt(2, 0).addPiece(new Bishop(Color.WHITE));
        getBoardSquareAt(5, 0).addPiece(new Bishop(Color.WHITE));
        getBoardSquareAt(2, 7).addPiece(new Bishop(Color.BLACK));
        getBoardSquareAt(5, 7).addPiece(new Bishop(Color.BLACK));
        // Queens
        getBoardSquareAt(3, 0).addPiece(new Queen(Color.WHITE));
        getBoardSquareAt(3, 7).addPiece(new Queen(Color.BLACK));
        // Kings
        getBoardSquareAt(4, 0).addPiece(new King(Color.WHITE));
        getBoardSquareAt(4, 7).addPiece(new King(Color.BLACK));
    }
    /**
     * getCaptured
     *
     * @return an arraylist of the pieces that have been taken out of play
     */
    public ArrayList<Piece> getCaptured() {
        return captured;
    }

    /**
     * getBoardSquareAt
     *
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
     *
     * @param source where the piece is coming from
     * @param target where the piece is trying to move
     * @return true if the piece is successfully moved, false if the move failed
     */
    public boolean movePiece(BoardSquare source, BoardSquare target) {
        if (source.isOccupied() && source.getPiece().legalMove(this, source, target)) {
            target.setPiece(source.getPiece());
            source.setPiece(null);
        }
        // TODO: implement
        return false;
    }

    /**
     * colorInCheck
     *
     * @param color
     * @return true if the player of 'color' is in check
     */
    public boolean colorInCheck(Color color) {
        //TODO: implement
        return false;
    }

    /**
     * isCaptured
     *
     * @param p the piece that has been captured
     */
    public void pieceCaptured(Piece p) {
        pieces.remove(p);
        captured.add(p);
    }

    /**
     * deselect the currently selected square and select the new one
     *
     * @param square the square to select
     */
    public void selectSquare(BoardSquare square) {
        if (selectedSquare != null) {
            selectedSquare.setSelected(false);
        }
        this.selectedSquare = square;
        selectedSquare.setSelected(true);
    }

    /**
     * deselect the currently selected square
     */
    public void deselectSquare() {
        if (selectedSquare != null) {
            selectedSquare.setSelected(false);
        }
        this.selectedSquare = null;
    }

    /**
     * get the selected square
     *
     * @return the selected square
     */
    public BoardSquare getSelectedSquare() {
        return selectedSquare;
    }

    /**
     * add another square to highlight
     *
     * @param square the square to highlight
     */
    public void addHighlightedSquare(BoardSquare square) {
        if (!this.highlightedSquares.contains(square)) {
            square.setHighlighted(true);
            this.highlightedSquares.add(square);
        }
    }

    /**
     * unhighlight a single square
     *
     * @param square the square to unhighlight
     */
    public void removeHighlightedSquare(BoardSquare square) {
        square.setHighlighted(false);
        this.highlightedSquares.remove(square);
    }

    /**
     * Unhighlight all squares
     */
    public void resetHighlightedSquares() {
        for (BoardSquare square : highlightedSquares) {
            square.setHighlighted(false);
        }
        this.highlightedSquares.clear();
    }

    /**
     * fillBoard
     * initializes all the pieces on the board
     */
    public void fillBoard() {

    }

}
