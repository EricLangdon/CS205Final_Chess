package chess.core.piece;

import chess.core.board.Board;
import chess.core.board.BoardSquare;

import java.util.ArrayList;

public abstract class Piece implements Cloneable {
    protected Color color;
    protected boolean hasMoved;
    protected int score;
    protected char unicode;

    /**
     * Default Constructor
     * Sets the default Piece color to white
     */
    Piece() {
        this(Color.WHITE);
    }

    /**
     * Constructor
     *
     * @param color the color of the piece
     */
    Piece(Color color) {
        this.color = color;
        unicode = 0x2659;
        score = 0;
        hasMoved = false;
    }

    /**
     * Copy Constructor
     */
    Piece(Piece oldPiece) {
        this.color = oldPiece.getColor();
        this.hasMoved = oldPiece.getHasMoved();
        this.score = oldPiece.getScore();
        this.unicode = oldPiece.getUnicode();
    }

    /**
     * Piece clone
     * Creates an identical copy of the piece
     *
     * @return A piece clone
     */
    public Piece clone() {
        try {
            Piece clonePiece = (Piece) super.clone();
            return clonePiece;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * legalMove without check checker
     * Call the over ridden legalMove method and sets checkChecker to false.
     *
     * @param board  the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     * @return boolean if the move passes initial checks
     */
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target) {
        return legalMove(board, source, target, false);
    }

    /**
     *legalMove with passed in checkChecker
     * Checks if the move is legal based on initial checks and determines
     * if the move puts the color in check if checkChecker is passed in as true
     *
     * @param board
     * @param source
     * @param target
     * @param checkCheck
     * @return boolean if the move passed initial checks
     */
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        //Checks if source location equals target location
        if (target.getX() == source.getX() && target.getY() == source.getY()) {
            return false;
        }

        //Call test in check if checkChecker is true
        if (checkCheck) {
            Board tempBoard = new Board(board);
            tempBoard.movePiece(tempBoard.getBoardSquareAt(source.getX(), source.getY()), tempBoard.getBoardSquareAt(target.getX(), target.getY()), false);
            if (tempBoard.colorInCheck(color)) {
                return false;
            }
        }

        //Checks if piece in target is the same color as piece in source
        return !target.isOccupied() || target.getPiece().getColor() != color;
    }

    /**
     * Get list of legal moves for a piece
     *
     * @param board  the board
     * @param source the piece to check movement
     * @return an arraylist of boardsquares that the piece occupying source can move to
     */
    public ArrayList<BoardSquare> getAvailableMoves(Board board, BoardSquare source) {
        ArrayList<BoardSquare> squares = new ArrayList<>();
        BoardSquare sq;
        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                sq = board.getBoardSquareAt(i, j);
                if (legalMove(board, source, sq, true)) {
                    squares.add(sq);
                }
            }
        }
        return squares;
    }

    /**
     * getColor
     *
     * @return the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * getHasMoved
     *
     * @return true if the piece has moved
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * setHasMoved
     *
     * @param hasMoved if the piece has moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * getUnicode
     *
     * @return the unicode of the piece
     */
    public char getUnicode() {
        if (this.color == Color.BLACK) {
            return (char) (unicode + 6);
        }
        return unicode;
    }

    /**
     * setColor
     *
     * @param color the color of the piece
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * getScore
     *
     * @return the score value of the piece
     */
    public int getScore() {
        return score;
    }

    /**
     * toString
     *
     * @return detailed string of the piece
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.getUnicode();
    }

    /**
     * equals
     * 
     * @param other the other piece
     * @return returns true if the piece is the same class, color, and hasMoved state
     */
    public boolean equals(Piece other){
        return this.color == other.color && this.hasMoved == other.hasMoved && this.getClass() == other.getClass();
    }
}

