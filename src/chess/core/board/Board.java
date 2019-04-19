package chess.core.board;

import chess.core.piece.*;

import java.util.ArrayList;

public class Board {
    public static final int NUM_ROWS = 8;
    public static final int NUM_COLS = 8;

    private ArrayList<ArrayList<BoardSquare>> board = new ArrayList<>(NUM_ROWS);
    private ArrayList<Piece> captured = new ArrayList<>();

    private ArrayList<BoardSquare> highlightedSquares = new ArrayList<>();
    private BoardSquare selectedSquare;

    private ArrayList<Move> moves;


    /**
     * Default Constructor
     * Sets up boardSquares and pieces on the board
     */
    public Board() {
        moves = new ArrayList<>();
        // BoardSquares
        for (int i = 0; i < NUM_ROWS; i++) {
            board.add(new ArrayList<>(NUM_COLS));
            for (int j = 0; j < NUM_COLS; j++) {
                BoardSquare boardSquare = new BoardSquare(i, j);
                board.get(i).add(j, boardSquare);
            }
        }

        // Pawns
        for (int i = 0; i < NUM_COLS; i++) {
            getBoardSquareAt(i, 1).setPiece(new Pawn(Color.WHITE));
            getBoardSquareAt(i, 6).setPiece(new Pawn(Color.BLACK));
        }
        // Rooks
        getBoardSquareAt(0, 0).setPiece(new Rook(Color.WHITE));
        getBoardSquareAt(7, 0).setPiece(new Rook(Color.WHITE));
        getBoardSquareAt(0, 7).setPiece(new Rook(Color.BLACK));
        getBoardSquareAt(7, 7).setPiece(new Rook(Color.BLACK));
        // Knights
        getBoardSquareAt(1, 0).setPiece(new Knight(Color.WHITE));
        getBoardSquareAt(6, 0).setPiece(new Knight(Color.WHITE));
        getBoardSquareAt(1, 7).setPiece(new Knight(Color.BLACK));
        getBoardSquareAt(6, 7).setPiece(new Knight(Color.BLACK));
        // Bishops
        getBoardSquareAt(2, 0).setPiece(new Bishop(Color.WHITE));
        getBoardSquareAt(5, 0).setPiece(new Bishop(Color.WHITE));
        getBoardSquareAt(2, 7).setPiece(new Bishop(Color.BLACK));
        getBoardSquareAt(5, 7).setPiece(new Bishop(Color.BLACK));
        // Queens
        getBoardSquareAt(3, 0).setPiece(new Queen(Color.WHITE));
        getBoardSquareAt(3, 7).setPiece(new Queen(Color.BLACK));
        // Kings
        getBoardSquareAt(4, 0).setPiece(new King(Color.WHITE));
        getBoardSquareAt(4, 7).setPiece(new King(Color.BLACK));
    }

    /**
     * Copy Constructor
     */
    public Board(Board oldBoard) {
        for (int i = 0; i < NUM_ROWS; i++) {
            board.add(new ArrayList<>(NUM_COLS));
            for (int j = 0; j < NUM_COLS; j++) {
                BoardSquare boardSquare = new BoardSquare(i, j);
                board.get(i).add(j, boardSquare);
                if (oldBoard.getBoardSquareAt(i, j).isOccupied()) {
                    boardSquare.setPiece(oldBoard.getBoardSquareAt(i, j).getPiece().clone());
                }
            }
        }
        captured = new ArrayList<>(oldBoard.captured);
        moves = new ArrayList<>(oldBoard.moves);
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
     * Call movepiece with checkcheck true
     *
     * @param source where the piece is coming from
     * @param target where the piece is trying to move
     * @return true if the piece is successfully moved, false if the move failed
     */
    public boolean movePiece(BoardSquare source, BoardSquare target) {
        return this.movePiece(source, target, true);
    }

    /**
     * movePiece
     *
     * @param source     where the piece is coming from
     * @param target     where the piece is trying to move
     * @param checkCheck should the legalmove consider check when determining if the move is legal
     * @return true if the piece is successfully moved, false if the move failed
     */
    public boolean movePiece(BoardSquare source, BoardSquare target, boolean checkCheck) {
        if (source.isOccupied() && source.getPiece().legalMove(this, source, target, checkCheck)) {
            Move move = new Move(source, target);
            //Castling logic
            if (source.getPiece() instanceof King && source.getX() == 4 && source.getY() == 0 && target.getX() == 2) {
                getBoardSquareAt(3, 0).setPiece(getBoardSquareAt(0, 0).getPiece());
                getBoardSquareAt(0, 0).setPiece(null);
            } else if (source.getPiece() instanceof King && source.getX() == 4 && source.getY() == 0 && target.getX() == 6) {
                getBoardSquareAt(5, 0).setPiece(getBoardSquareAt(7, 0).getPiece());
                getBoardSquareAt(7, 0).setPiece(null);
            } else if (source.getPiece() instanceof King && source.getX() == 4 && source.getY() == 7 && target.getX() == 2) {
                getBoardSquareAt(3, 7).setPiece(getBoardSquareAt(0, 7).getPiece());
                getBoardSquareAt(0, 7).setPiece(null);
            } else if (source.getPiece() instanceof King && source.getX() == 4 && source.getY() == 7 && target.getX() == 6) {
                getBoardSquareAt(5, 7).setPiece(getBoardSquareAt(7, 7).getPiece());
                getBoardSquareAt(7, 7).setPiece(null);
            }
            if (target.isOccupied()) {
                move.setCapturedPiece(target.getPiece());
                captured.add(target.getPiece());
            }

            moves.add(move);
            target.setPiece(source.getPiece());
            source.setPiece(null);
            target.getPiece().setHasMoved(true);

            //En passant pawn take
            if(target.getPiece() instanceof Pawn && ((Pawn) target.getPiece()).getEnPassant()){
                if(target.getPiece().getColor()==Color.WHITE){
                    move.setCapturedPiece(getBoardSquareAt(target.getX(), target.getY()-1).getPiece());
                    captured.add(getBoardSquareAt(target.getX(), target.getY()-1).getPiece());
                    getBoardSquareAt(target.getX(),target.getY()-1).setPiece(null);
                    ((Pawn) target.getPiece()).setEnPassant(false);

                } else if (target.getPiece().getColor()==Color.BLACK){
                    move.setCapturedPiece(getBoardSquareAt(target.getX(), target.getY()+1).getPiece());
                    captured.add(getBoardSquareAt(target.getX(), target.getY()+1).getPiece());
                    getBoardSquareAt(target.getX(), target.getY()+1).setPiece(null);
                    ((Pawn) target.getPiece()).setEnPassant(false);
                }
            }

            return true;
        }
        return false;
    }

    /**
     * colorInCheck
     *
     * @param color
     * @return true if the player of 'color' is in check
     */
    public boolean colorInCheck(Color color) {
        ArrayList<BoardSquare> opponentSources = new ArrayList<>();
        BoardSquare kingSquare = new BoardSquare();
        BoardSquare square;
        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                square = getBoardSquareAt(j, i);
                if (square.isOccupied()) {
                    if (square.getPiece().getColor() == color && square.getPiece() instanceof King) {
                        kingSquare = square;
                    } else if (square.getPiece().getColor() == color.other()) {
                        opponentSources.add(square);
                    }

                }
            }
        }
        int k = 0;
        for (BoardSquare sq : opponentSources) {
            if (sq.getPiece().legalMove(this, sq, kingSquare, false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkmate
     *
     * @param color color to check
     * @return true if side is in checkmate
     */
    public boolean checkmate (Color color) {
        boolean checkmate = true;

        if (!colorInCheck(color)) {
            return false;
        } else {
            for (BoardSquare bs : this.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == color && bs.getPiece().getAvailableMoves(this, bs).size() > 0) {
                    checkmate = false;
                }
            }
        }
        return checkmate;
    }

    /**
     * isCaptured
     *
     * @param p the piece that has been captured
     */
    public void pieceCaptured(Piece p) {
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
     * getPieces
     */
    public ArrayList<Piece> getPieces(Color color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (getBoardSquareAt(i, j).isOccupied()) {
                    if (getBoardSquareAt(i, j).getPiece().getColor() == color) {
                        pieces.add(getBoardSquareAt(i, j).getPiece());
                    }
                }
            }
        }
        return pieces;
    }

    public ArrayList<BoardSquare> getBoardSquares() {
        ArrayList<BoardSquare> squares = new ArrayList<>(64);
        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                squares.add(getBoardSquareAt(i, j));
            }
        }
        return squares;
    }

    /**
     * check to see if there is a promotable pawn
     * @return bool
     */
    public boolean checkPromotion() {
        BoardSquare square;
        for (int i = 0; i < 8; i++) {
            square = getBoardSquareAt(i, 7);
            if (square.isOccupied()) {
                if (square.getPiece() instanceof Pawn) {
                    return true;
                }
            }
            square = getBoardSquareAt(i, 0);
            if (square.isOccupied()) {
                if (square.getPiece() instanceof Pawn) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * find promotable pawn
     * @return boardsquare
     */
    public BoardSquare getPromotablePawn() {
        BoardSquare square;
        for (int i=0; i < 8; i++){
            square=getBoardSquareAt(i, 7);
            if(square.isOccupied()){
                if (square.getPiece() instanceof Pawn){
                    return square;
                }
            }
            square=getBoardSquareAt(i, 0);
            if(square.isOccupied()){
                if(square.getPiece() instanceof Pawn){
                    return square;
                }
            }
        }
        return null;
    }

    /**
     * Replace pawn with Piece
     */
    public void replacePawn(Piece piece, BoardSquare square){
        if(square.isOccupied() && square.getPiece() instanceof  Pawn) {
            square.setPiece(piece);
        }
    }

    /**
     * Get arraylist of moves
     * @return arraylist of moves
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Get number of moves
     * @return number of moves
     */
    public int getNumMoves(){
        return moves.size();
    }
}