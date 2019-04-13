package chess.core.game;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.game.cpu.ComplexCPU;
import chess.core.game.cpu.SimpleCPU;
import chess.core.piece.Color;
import chess.core.piece.Piece;
import chess.gui.ChessGUI;

import java.io.File;

public class Game {
    private Board board;
    private Color currentTurn;
    private Color player1;
    private Color player2;
    private ChessClock p1Clock;
    private ChessClock p2Clock;
    private GameMode mode;
    private ChessGUI ui;

    /**
     * Constructor
     *
     * @param mode    gamemode
     * @param player1 color of player1
     * @param player2 color of player2
     */
    public Game(GameMode mode, Color player1, Color player2, ChessClock p1Clock, ChessClock p2Clock) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        this.p1Clock = p1Clock;
        this.p2Clock = p2Clock;
        newGame();
    }


    /**
     * Constructor; calls newGame
     * defaults player1 to white
     *
     * @param mode gamemmode
     */
    public Game(GameMode mode) {
        this(mode, Color.WHITE, Color.BLACK, new ChessClock(Color.WHITE), new ChessClock(Color.BLACK));
    }

    public Game(GameMode mode, ChessGUI ui) {
        this(mode);
        this.ui = ui;
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
                break;
            case DUMB_COMPUTER:
                computer.choiceMove(board);
                currentTurn = currentTurn.other();
                ui.turnComplete();
                currentTurn = currentTurn.other();
                break;
            case PVP:
                currentTurn = currentTurn.other();
                ui.turnComplete();
                break;
        }
    }

    /**
     * Get the winner, if one exists
     *
     * @return null if no winner, else the color of the winner
     */
    public Color getWinner() {
        // TODO implement
        if (!isGameOver()) {
            return null;
        }
        return currentTurn.other();
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

        @Override
        public String toString() {
            switch (this) {
                case DUMB_COMPUTER:
                    return "Dumb Computer";
                case SMART_COMPUTER:
                    return "Smart Computer";
                default:
                    return this.name();
            }
        }
    }

}
