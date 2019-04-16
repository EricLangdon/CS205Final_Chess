package chess.core.game;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.game.cpu.ComplexCPU;
import chess.core.game.cpu.SimpleCPU;
import chess.core.piece.Color;
import chess.core.piece.Piece;
import chess.gui.ChessGUI;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

import static chess.core.game.GameResult.BLACKWIN;

public class Game {
    private Board board;
    private Color currentTurn;
    private Color player1;
    private Color player2;
    private ChessClock p1Clock;
    private ChessClock p2Clock;
    private GameMode mode;
    private ChessGUI ui;

    private Stack<Game> states;

    /**
     * Constructor
     *
     * @param mode    gamemode
     * @param player1 color of player1
     * @param player2 color of player2
     */
    public Game(GameMode mode, Color player1, Color player2, ChessClock p1Clock, ChessClock p2Clock, ChessGUI ui) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        this.p1Clock = p1Clock;
        this.p2Clock = p2Clock;
        this.ui = ui;
        newGame();
        states = new Stack<>();
        this.states.push(new Game(this));
    }


    /**
     * Constructor; calls newGame
     * defaults player1 to white
     *
     * @param mode gamemmode
     */
    public Game(GameMode mode, ChessGUI ui) {
        this(mode, Color.WHITE, Color.BLACK, new ChessClock(Color.WHITE), new ChessClock(Color.BLACK), ui);
    }

    /**
     * Copy constructor
     */
    public Game(Game game) {
        this.board = new Board(game.board);
        this.currentTurn = game.currentTurn;
        this.player1 = game.player1;
        this.player2 = game.player2;
        this.mode = game.mode;
        this.ui = game.ui;
        this.states = game.states;
    }

    /**
     * Setup game and board for a game
     */
    public void newGame() {
        board = new Board();
        currentTurn = Color.WHITE;
    }

    /**
     * Save game to file
     *
     * @param file file to save to
     */
    public void save(File file) {
        // TODO: implement
    }

    /**
     * Load game from a filepath
     *
     * @param file file to load from
     */
    public void load(File file) {
        // TODO: implement
    }

    public boolean undo() {
        if (states.size() == 0) {
            return false;
        }
        if (states.size() == 1 && states.peek().getBoard().getNumMoves() == this.getBoard().getNumMoves()) {
            return false;
        }
        Game game = states.pop();
        if (game.getBoard().getNumMoves() == this.getBoard().getNumMoves()) {
            game = states.pop();
        }
        this.board = game.board;
        this.currentTurn = game.currentTurn;
        this.player1 = game.player1;
        this.player2 = game.player2;
        this.mode = game.mode;
        this.ui = game.ui;
        states.push(new Game(this));
        this.states = game.states;
        return true;
    }

    /**
     * Execute turn
     */
    public void executeTurn() {
        SimpleCPU computer = new SimpleCPU();    //Todo Reset execute turn when done
        ComplexCPU ai = new ComplexCPU();
        switch (mode) {
            case SMART_COMPUTER:
                ai.choiceMove(board);
                currentTurn = currentTurn.other();
                ui.turnComplete();
                currentTurn = currentTurn.other();
                ui.turnComplete();
                break;
            case DUMB_COMPUTER:
                computer.choiceMove(board);
                currentTurn = currentTurn.other();
                ui.turnComplete();
                currentTurn = currentTurn.other();
                ui.turnComplete();
                break;
            case PVP:
                currentTurn = currentTurn.other();
                ui.turnComplete();
                break;
        }
        this.states.push(new Game(this));
    }

    /**
     * Get the winner, if one exists
     *
     * @return null if no winner, else the color of the winner
     */
    public GameResult getWinner() {
        // TODO implement
        if (!isGameOver()) {
            return null;
        } else if (board.colorInCheck(Color.WHITE)) {
            ArrayList<BoardSquare> holder;
            int totalMoves = 0;
            for (BoardSquare bs : board.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == Color.WHITE) {
                    holder = bs.getPiece().getAvailableMoves(board, bs);
                    totalMoves += holder.size();
                }
            }
            if (totalMoves == 0) {
                return GameResult.BLACKWIN;
            } else {
                return null;
            }

        } else if (board.colorInCheck(Color.BLACK)) {
            ArrayList<BoardSquare> holder;
            int totalMoves = 0;
            for (BoardSquare bs : board.getBoardSquares()) {
                if (bs.isOccupied() && bs.getPiece().getColor() == Color.BLACK) {
                    holder = bs.getPiece().getAvailableMoves(board, bs);
                    totalMoves += holder.size();
                }
            }
            if (totalMoves == 0) {
                return GameResult.WHITEWIN;
            } else {
                return null;
            }

        } else {
            //Todo implement draws
            return GameResult.DRAW;
        }
    }

    public boolean isGameOver() {
        // TODO check draw
        // game is not over if the player can move a piece
        for (BoardSquare bs : board.getBoardSquares()) {
            if (bs.isOccupied() && bs.getPiece().getColor().equals(currentTurn) && !bs.getPiece().getAvailableMoves(board, bs).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the board
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the game mode
     *
     * @return the game mode
     */
    public GameMode getMode() {
        return mode;
    }

    /**
     * Get the color of the current turn
     *
     * @return the current turn
     */
    public Color getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Get score based on captured pieces of a color
     *
     * @param color color
     * @return the score for the color
     */
    public int getScore(Color color) {
        int score = 0;
        for (Piece piece : board.getCaptured()) {
            if (!piece.getColor().equals(color)) {
                score += piece.getScore();
            }
        }
        return score;
    }

    /**
     * getTimeRemaining
     *
     * @param color
     * @return the time remaining for the color
     */
    public String getTimeRemaining(Color color) {
        if (player1.equals(color)) {
            return p1Clock.printTime();
        } else {
            return p2Clock.printTime();
        }
    }

    public enum GameMode {
        PVP, DUMB_COMPUTER, SMART_COMPUTER;
    }

}
