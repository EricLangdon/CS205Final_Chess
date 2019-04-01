package chess;

public class Game {
    public enum GameMode {
        PVP, DUMB_COMPUTER;
    }

    private Board board;
    // TODO: timer

    private Color currentTurn;
    private Color player1;
    private Color player2;

    private GameMode mode;

    /**
     * Constructor; calls newGame
     */
    public Game() {
        newGame();
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
     * @param filepath filepath to save to
     */
    public void save(String filepath) {
        // TODO: implement
    }


    /**
     * Load game from a filepath
     *
     * @param filepath filepath to load from
     */
    public void load(String filepath) {
        // TODO: implement
    }

    /**
     * Execute turn
     */
    public void executeTurn() {
        // TODO: implement
        currentTurn = currentTurn.other();
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
