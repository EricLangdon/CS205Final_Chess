package chess;

public class BoardSquare {

    int x;
    int y;
    Piece piece;

    private boolean selected = false;
    private boolean highlighted = false;

    /**
     * Default Constructor
     */
    BoardSquare() {
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
    BoardSquare(int xCoor, int yCoor) {
        x = xCoor;
        y = yCoor;
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
     * setPosition
     *
     * @param x the new x-coor of the BoardSquare
     * @param y the new y-coor of the BoardSquare
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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


    /**
     * addPiece
     * @param piece the piece occupying the boardSquare
     */
    public void addPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * removePiece
     */
    public void removePiece() {
        this.piece = null;
    }

}
