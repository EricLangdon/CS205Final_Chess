package chess.core.board;

import chess.core.piece.Piece;

public class BoardSquare {

    private int x;
    private int y;
    private Piece piece;

    private boolean selected = false;
    private boolean highlighted = false;

    /**
     * Default Constructor
     */
    public BoardSquare() {
        piece = null;
        x = 0;
        y = 0;
    }

    /**
     * Non-default Constructor
     *
     * @param xCoor
     * @param yCoor
     */
    public BoardSquare(int xCoor, int yCoor) {
        x = xCoor;
        y = yCoor;
    }

    /**
     * Copy Constructor
     */
    public BoardSquare(BoardSquare oldSquare) {
        x = oldSquare.getX();
        y = oldSquare.getY();
        if (oldSquare.isOccupied()) {
            setPiece(oldSquare.getPiece().clone());
        } else {
            setPiece(null);
        }
    }

    /**
     * isOccupied
     *
     * @return true if the piece is not null
     */
    public boolean isOccupied() {
        return piece != null;
    }

    /**
     * getPiece
     *
     * @return the piece occupying the boardSquare
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * setPiece
     * Set the piece occupying the boardSquare
     *
     * @param piece the piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * getX
     *
     * @return the x-coor of the BoardSquare
     */
    public int getX() {
        return x;
    }

    /**
     * getY
     *
     * @return the y-coor of the BoardSquare
     */
    public int getY() {
        return y;
    }

    /**
     * isSelected
     *
     * @return true if the boardsquare is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * setSelected
     * Sets the selected state of the boardsquare
     *
     * @param selected if the boardsquare is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * isHighlighted
     *
     * @return true if the boardsquare is highlighted
     */
    public boolean isHighlighted() {
        return highlighted;
    }


    /**
     * setHighlighted
     * sets the high lighted square to the passed in value
     *
     * @param highlighted if the boardsquare is highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    /**
     * toString method
     *
     * @return string representing the boardsquare
     */
    @Override
    public String toString() {
        return Character.toString((char) (x + 1 + 64)) + (y + 1);
    }

    /**
     * equals method
     * @param other the other BoardSquare
     * @return returns true if the coordinates are the same
     */
    public boolean equals(BoardSquare other) {
        return this.x == other.x && this.y == other.y;
    }


}
