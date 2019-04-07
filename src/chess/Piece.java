package chess;

import java.util.ArrayList;

public abstract class Piece {
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
     * Check if the given move is legal given the board and source and target squares. To be overridden in piece subclasses
     *
     * @param board  the board
     * @param source the boardsquare containing the piece checking for move
     * @param target the boardsquare that the piece wants to be moved to
     */
    public boolean legalMove(Board board, BoardSquare source, BoardSquare target) {
        return legalMove(board, source, target, false);
    }

    public boolean legalMove(Board board, BoardSquare source, BoardSquare target, boolean checkCheck) {
        //Checks if source location equals target location
        if (target.getX() == source.getX() && target.getY() == source.getY()) {
            return false;
        }

        //Call test in check
        if (checkCheck) {
            if (testCheckAlly(board, source, target)) {
                return false;
            }
        }

        //Checks if piece in target is the same color as piece in source
        if (target.isOccupied() && target.getPiece().getColor() == color) {
            return false;
        }

        return true;
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
     * @return the color of the piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Return if the piece has moved yet
     *
     * @return true if the piece has moved
     */
    public boolean getHasMoved() {
        return hasMoved;
    }

    /**
     * Set of the piece has moved
     *
     * @param hasMoved if the piece has moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public char getUnicode() {
        if (this.color == Color.BLACK) {
            return (char) (unicode + 6);
        }
        return unicode;
    }

    /**
     * Set the color of the piece
     *
     * @param color the color of the piece
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the score value of the piece
     *
     * @return the value
     */
    public int getScore() {
        return score;
    }

    /**
     * testInCheck
     * Method checks if a move executed by a piece puts their king in check. Re
     *
     * @param board the board
     * @return bool that returns false if the move puts the king in check, true if the move is legal
     */
//    public boolean testCheckAlly(Board board, BoardSquare source, BoardSquare target) {
//        ArrayList<BoardSquare> opponentSources = new ArrayList<>();
//        Board testBoard = new Board(board);
//        BoardSquare kingSquare = new BoardSquare();
//        BoardSquare square;
//        for (int i = 0; i < Board.NUM_ROWS; i++) {
//            for (int j = 0; j < Board.NUM_COLS; j++) {
//                square = board.getBoardSquareAt(i, j);
//                if (square.isOccupied() && square.getPiece().getColor() == color && square.getPiece() instanceof King) {
//                    kingSquare = square;
//                } else if (square.isOccupied() && square.getPiece().getColor() == color.other()) {
//                    opponentSources.add(square);
//                }
//            }
//        }
//        testBoard.movePiece(source, target);    //Todo Think this is causing the problem
//        int k = 0;
//        while (k < opponentSources.size()) {
//            if(opponentSources.get(k).getPiece().legalMove(testBoard, opponentSources.get(k), kingSquare)){
//                return true;
//            } else {
//                k++;
//            }
//        }
//        return false;
//    }
    //check if king in check from board, handle hypothetical stuff from legalMove


//        ArrayList<BoardSquare> allySources = new ArrayList<>();
//        ArrayList<BoardSquare> opponentSources = new ArrayList<>();
//        BoardSquare allyKingSquare = new BoardSquare();
//        BoardSquare opponentKingSquare = new BoardSquare();
//        BoardSquare square;
//        Board testBoard = board;
//        for (int i = 0; i < Board.NUM_ROWS; i++) {
//            for (int j = 0; j < Board.NUM_COLS; j++) {
//                square = board.getBoardSquareAt(i, j);
//                if (square.isOccupied() && square.getPiece().getColor() == color && square.getPiece() instanceof King) {
//                    allyKingSquare = square;
//                } else if (square.isOccupied() && square.getPiece().getColor() == color.other() && square.getPiece() instanceof King)
//                    opponentKingSquare = square;
//                else if (square.isOccupied() && square.getPiece().getColor() == color) {
//                    allySources.add(square);
//                } else if (square.isOccupied() && square.getPiece().getColor() == color.other()) {
//                    opponentSources.add(square);
//                }
//
//            }
//        }
//        if (source.getPiece().getColor() == color) {
//
//        } else {
//            int k = 0;
//            while (k < allySources.size()) {
//                if (allySources.get(k).getPiece().legalMove(board, source, opponentKingSquare)) {
//                    return true;
//                } else {
//                    k++;
//                }
//            }
//            return false;
//        }
//
//
//        int k = 0;
//        while (k < opponentSources.size()) {
//            if (opponentSources.get(k).getPiece().legalMove(board, opponentSources.get(k), kingSquare)) {
//                return true;
//            } else {
//                k++;
//            }
//        }
//        return false;
//    }

//    public boolean testCheckOpp(Board board, BoardSquare source) {
//        ArrayList<BoardSquare> allySources = new ArrayList<>();
//        BoardSquare square = new BoardSquare();
//        BoardSquare oppKingSquare = new BoardSquare();
//
//        for (int i = 0; i < Board.NUM_ROWS; i++) {
//            for (int j = 0; j < Board.NUM_COLS; j++) {
//                square = board.getBoardSquareAt(i, j);
//                if (square.isOccupied() && square.getPiece().getColor() == color.other() && square.getPiece() instanceof King) {
//                    oppKingSquare = square;
//                } else if (square.isOccupied() && square.getPiece().getColor() == color) {
//                    allySources.add(square);
//                }
//            }
//        }
//        int k = 0;
//        while (k < allySources.size()) {
//            if (allySources.get(k).getPiece().legalMove(board, source, oppKingSquare)) {
//                return true;
//            }
//        }
//        return false;
//    }
}

