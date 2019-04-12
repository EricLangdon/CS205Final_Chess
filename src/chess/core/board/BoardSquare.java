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
     * Constructor
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
     * Get if the boardsquare is selected
     *
     * @return if the boardsquare is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Set the selected state of the boardsquare
     *
     * @param selected if the boardsquare is selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Get if the boardsquare is highlighted
     *
     * @return if the boardsquare is highlighted
     */
    public boolean isHighlighted() {
        return highlighted;
    }


    /**
     * Set the highlighted state of the boardsquare
     *
     * @param highlighted if the boardsquare is highlighted
     */
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    

}