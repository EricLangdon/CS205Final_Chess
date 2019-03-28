package chess;

public class BoardSquare {

    int x;
    int y;
    Piece piece;

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
     * @param piece the piece occupying the boardSquare
     * @param xCoor
     * @param yCoor
     */
    BoardSquare(Piece piece, int xCoor, int yCoor) {
        this.piece = piece;
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
}
