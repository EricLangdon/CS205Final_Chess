package chess.core.game;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.board.Move;
import chess.core.game.cpu.CPU;
import chess.core.game.cpu.ComplexCPU;
import chess.core.game.cpu.SimpleCPU;
import chess.core.piece.*;
import chess.gui.ChessGUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class Game {
    private static Timeline cpuTimer;
    private Board board;
    private boolean threeFoldDraw;
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
    public Game(GameMode mode, Color player1, Color player2, ChessGUI ui) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        this.p1Clock = new ChessClock(this, player1);
        this.p2Clock = new ChessClock(this, player2);
        this.ui = ui;
        newGame();
        states = new Stack<>();
        this.states.push(new Game(this));
        if (cpuTimer != null) {
            cpuTimer.stop();
        }
        if (mode != GameMode.PVP) {
            this.startCPU(player2);
        }
    }


    /**
     * Constructor; calls newGame
     * defaults player1 to white
     *
     * @param mode gamemmode
     */
    public Game(GameMode mode, ChessGUI ui) {
        this(mode, Color.WHITE, Color.BLACK, ui);
    }

    /**
     * Copy constructor
     */
    private Game(Game game) {
        this.board = new Board(game.board);
        this.threeFoldDraw = false;
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
    private void newGame() {
        board = new Board();
        currentTurn = Color.WHITE;
    }

    /**
     * End all timers and threads that are connected to this game
     */
    public void end() {
        if (p1Clock != null) {
            p1Clock.cancel();
        }
        if (p2Clock != null) {
            p2Clock.cancel();
        }
        if (cpuTimer != null) {
            cpuTimer.stop();
        }
    }


    /**
     * Start the computer player on another thread
     */
    private void startCPU(Color color) {
        CPU cpu;
        CPU cpu2 = null;

        switch (mode) {
            case SMART_COMPUTER:
                cpu = new ComplexCPU(color);
                break;
            case DUMB_COMPUTER:
                cpu = new SimpleCPU(color);
                break;
            case CVC:
                cpu = new ComplexCPU(color);
                cpu2 = new ComplexCPU(color.other());
                break;
            default:
                return;
        }

        CPU finalCpu = cpu2;
        cpuTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (isGameOver()) {
                return;
            }
            if (currentTurn == color) {
                cpu.choiceMove(board);
                executeTurn();
            } else {
                if (mode == GameMode.CVC) {
                    finalCpu.choiceMove(board);
                    executeTurn();
                }
            }

        }));
        cpuTimer.setCycleCount(Timeline.INDEFINITE);
        cpuTimer.play();
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
     * Undo the last move
     * @param limit true if undo should be limited to one move by the player, false allows computer undo
     * @return true if successful
     */
    public boolean undo(boolean limit){
        if (states.size() == 0) {
            return false;
        }
        if (states.size() == 1 && states.peek().getBoard().getNumMoves() == this.getBoard().getNumMoves()) {
            return false;
        }

        if (currentTurn == player2 && mode != GameMode.PVP && !limit) {
            return false;
        }

        Game game = states.pop();
        // pop again if the game state is the same (ie the first press of undo)
        if (game.getBoard().getNumMoves() == this.getBoard().getNumMoves()) {
            game = states.pop();
        }
        // pop again if the gamemode is not pvp, since computer's move AND player's move need to be undone
        if (mode != GameMode.PVP && !limit) {
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
     * Undo the last move, call undo(false)
     * @return true if able to undo
     */
    public boolean undo() {
        return undo(false);
    }

    /**
     * Execute turn
     */
    public synchronized void executeTurn() {
        // activate timer on first move
        if(board.getNumMoves() == 1){
            p1Clock.start();
            p2Clock.start();
        }
        currentTurn = currentTurn.other();
        this.states.push(new Game(this));
        ui.turnComplete();
    }

    /**
     * Get the winner, if one exists
     *
     * @return null if no winner, else the color of the winner
     */
    public GameResult getWinner() {
        if (!isGameOver()) {
            return null;
        } else if (p1Clock.getTime() == -1) {
            return (p1Clock.getColor() == Color.BLACK) ? GameResult.BLACKWIN_TIME : GameResult.WHITEWIN_TIME;
        } else if (p2Clock.getTime() == -1) {
            return (p2Clock.getColor() == Color.BLACK) ? GameResult.BLACKWIN_TIME : GameResult.WHITEWIN_TIME;
        } else if (board.colorInCheck(Color.WHITE) && !threeFoldDraw) {
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

        } else if (board.colorInCheck(Color.BLACK) && !threeFoldDraw) {
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
            return GameResult.DRAW;
        }
    }

    @SuppressWarnings("Duplicates")
    public boolean isGameOver() {
        if (p1Clock.getTime() == -1 || p2Clock.getTime() == -1) {
            return true;
        }
        ArrayList<Piece> blackLeft = board.getPieces(Color.BLACK);
        ArrayList<Piece> whiteLeft = board.getPieces(Color.WHITE);
        ArrayList<Move> gameMoves = getBoard().getMoves();
        //Piece deficit stalemate check
        if (blackLeft.size() <= 2 && whiteLeft.size() <= 2) {
            if (blackLeft.size() == 1 && whiteLeft.size() == 1) {
                return true;
            } else if (blackLeft.size() == 1 && whiteLeft.size() == 2) {
                if (whiteLeft.get(0) instanceof King && whiteLeft.get(1) instanceof Bishop
                        || whiteLeft.get(0) instanceof Bishop && whiteLeft.get(1) instanceof King) {
                    return true;
                } else if (whiteLeft.get(0) instanceof King && whiteLeft.get(1) instanceof Knight
                        || whiteLeft.get(0) instanceof Knight && whiteLeft.get(1) instanceof King) {
                    return true;
                }

            } else if (blackLeft.size() == 2 && whiteLeft.size() == 1) {
                if (blackLeft.get(0) instanceof King && blackLeft.get(1) instanceof Bishop
                        || blackLeft.get(0) instanceof Bishop && blackLeft.get(1) instanceof King) {
                    return true;
                } else if (blackLeft.get(0) instanceof King && blackLeft.get(1) instanceof Knight
                        || blackLeft.get(0) instanceof Knight && blackLeft.get(1) instanceof King) {
                    return true;
                }
            } else if (blackLeft.size() == 2 && whiteLeft.size() == 2) {
                if (blackLeft.get(0) instanceof King && blackLeft.get(1) instanceof Bishop
                        || blackLeft.get(0) instanceof Bishop && blackLeft.get(1) instanceof King) {
                    if (whiteLeft.get(0) instanceof King && whiteLeft.get(1) instanceof Bishop
                            || whiteLeft.get(0) instanceof Bishop && whiteLeft.get(1) instanceof King) {
                        return true;
                    }
                }
            }
            //Threefold repetition draw check
        } else if (gameMoves.size() >= 8) {
            if (gameMoves.get(gameMoves.size() - 2).getTarget() == gameMoves.get(gameMoves.size() - 4).getSource()
                    && gameMoves.get(gameMoves.size() - 4).getTarget() == gameMoves.get(gameMoves.size() - 6).getSource()
                    && gameMoves.get(gameMoves.size() - 6).getTarget() == gameMoves.get(gameMoves.size() - 8).getSource()) {
                if (gameMoves.get(gameMoves.size() - 1).getTarget() == gameMoves.get(gameMoves.size() - 3).getSource()
                        && gameMoves.get(gameMoves.size() - 3).getTarget() == gameMoves.get(gameMoves.size() - 5).getSource()
                        && gameMoves.get(gameMoves.size() - 5).getTarget() == gameMoves.get(gameMoves.size() - 7).getSource()) {
                    threeFoldDraw = true;
                    return true;
                }
            }
        }
        // Checks if player can move a piece
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

    public Color getPlayer1Color() {
        return player1;
    }

    public Color getPlayer2Color() {
        return player2;
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
            return p1Clock.toString();
        } else {
            return p2Clock.toString();
        }
    }

    /**
     * Stops the timer from ticking down and sets time to 0
     * @param color the color to have its timer disabled
     */
    public void disableTimer(Color color) {
        ChessClock clock = color == player1 ? p1Clock : p2Clock;
        clock.setTime(0);
        clock.cancel();
    }

    public boolean equals(Game oldGame) {
        if (this.getBoard().equals(oldGame.getBoard())) {
            return true;
        } else {
            return false;
        }
    }

    public ChessClock getP1Clock() {
        return p1Clock;
    }

    public ChessClock getP2Clock() {
        return p2Clock;
    }

    public enum GameMode {
        PVP, DUMB_COMPUTER, SMART_COMPUTER, CVC;
    }

    /**
     * Get the states stack
     * @return the states stack
     */
    public Stack<Game> getStates() {
        return states;
    }
}
