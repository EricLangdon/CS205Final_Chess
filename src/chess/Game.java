package chess;

import java.io.File;

public class Game {
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

    private Board board;
    // TODO: timer

    private Color currentTurn;
    private Color player1;
    private Color player2;

    private GameMode mode;

    /**
     * Constructor
     *
     * @param mode    gamemode
     * @param player1 color of player1
     * @param player2 color of player2
     */
    public Game(GameMode mode, Color player1, Color player2) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        newGame();
    }

    /**
     * Constructor; calls newGame
     * defaults player1 to white
     *
     * @param mode gamemmode
     */
    public Game(GameMode mode) {
        this(mode, Color.WHITE, Color.BLACK);
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
                currentTurn = player1;
                break;
            case DUMB_COMPUTER:
                computer.choiceMove(board);
                currentTurn = player1;
                break;
            case PVP:
                currentTurn = currentTurn.other();
                break;
        }
    }

    /**
     * Get the winner, if one exists
     *
     * @return null if no winner, else the color of the winner
     */
    Color getWinner() {
        // TODO implement
        return null;
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

}
