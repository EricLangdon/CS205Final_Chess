package chess.gui;

import chess.core.board.Board;
import chess.core.board.BoardSquare;
import chess.core.board.Move;
import chess.core.game.Game;
import chess.core.game.GameResult;
import chess.core.piece.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;


public class ChessGUI extends Application {

    private static final Color BACKGROUND_COLOR = Color.DARKSLATEGRAY;
    private Game game;
    private CustomGridPane grid;
    private BorderPane bp;
    private MenuBar menuBar;
    private GameParameters gameParameters;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        gameParameters = new GameParameters();
        newGame(false);
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main, 900, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(700);

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> resizeListener(primaryStage, obs, oldVal, newVal));
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> resizeListener(primaryStage, obs, oldVal, newVal));

        bp = new BorderPane();
        main.setCenter(bp);

        // menubars
        menuBar = new MenuBar();
        KeyCombination.Modifier modifier;
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
            modifier = KeyCombination.META_DOWN;
        } else {
            modifier = KeyCombination.CONTROL_DOWN;
        }
        Menu fileMenu = new Menu("File");

        // new
        MenuItem newItem = new MenuItem("New Game");
        newItem.setOnAction(e -> this.newGame(true));
        newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, modifier));

        // save
        MenuItem saveItem = new MenuItem("Save Game");
        saveItem.setOnAction(e -> this.save(primaryStage));
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, modifier));
        // load
        MenuItem loadItem = new MenuItem("Load Game");
        loadItem.setOnAction(e -> this.load(primaryStage));
        loadItem.setAccelerator(new KeyCodeCombination(KeyCode.O, modifier));
        //exit
        MenuItem exitItem = new MenuItem("Quit");
        exitItem.setOnAction(e -> Platform.exit());
        exitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, modifier));

        fileMenu.getItems().addAll(newItem, saveItem, loadItem, exitItem);

        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setOnAction(e -> this.undo());
        editMenu.getItems().add(undoItem);
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, modifier));

        Menu viewMenu = new Menu("View");
        MenuItem logItem = new MenuItem("Game Log");
        logItem.setAccelerator(new KeyCodeCombination(KeyCode.L, modifier));
        logItem.setOnAction(e -> this.showGameLog());
        viewMenu.getItems().add(logItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu);
        main.setTop(menuBar);

        bp.setPadding(new Insets(0));
        bp.setLeft(null);

        // center grid with actual board
        this.grid = new CustomGridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setCenter(grid);
        redrawGrid();

        redrawPlayerInfo();

        // update player info on a timer so the timer appears to countdown
        Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> updatePlayerInfo());
            }
        }, 0, 100);

        primaryStage.show();

    }

    /**
     * Update player info (timer, score, etc) without redraw
     */
    private void updatePlayerInfo() {
        ((PlayerInfoPane) bp.getBottom()).updateTimer();
        ((PlayerInfoPane) bp.getTop()).updateTimer();
    }

    /**
     * Redraw the player info
     */
    private void redrawPlayerInfo() {
        bp.setTop(new PlayerInfoPane(this.game, chess.core.piece.Color.BLACK));
        bp.setBottom(new PlayerInfoPane(this.game, chess.core.piece.Color.WHITE));
    }

    /**
     * Redraw the entire board
     */
    private void redrawGrid() {
        redrawPlayerInfo();
        this.grid.getChildren().clear();
        for (int i = 0; i < Board.NUM_COLS + 1; i++) {
            for (int j = 0; j < Board.NUM_ROWS + 1; j++) {
                // labels
                if (i < 1 || j < 1) {
                    // bottom left corner doesn't get any label
                    if (i == 0 && j == 0) {
                        continue;
                    }

                    Label label = new Label();
                    String text;
                    if (i < 1) {
                        text = Integer.toString(j);
                        label.setAlignment(Pos.CENTER_LEFT);
                        label.setAlignment(Pos.CENTER_LEFT);
                        label.setPadding(new Insets(0, 8, 0, 0));
                    } else { // j < 1
                        text = Character.toString((char) (i + 64)); // get char from i starting at ascii A
                        label.setMinWidth(BoardSquarePane.SQUARE_SIZE); // must set min width or it aligns left
                        label.setAlignment(Pos.BASELINE_CENTER);
                        label.setPadding(new Insets(8, 0, 0, 0));
                    }
                    label.setTextFill(Color.LIGHTGRAY);
                    label.setFont(Font.font(15));
                    label.setText(text);
                    this.grid.add(label, i, Board.NUM_ROWS - j);
                    continue;
                }
                // boardsquares
                BoardSquare boardSquare = game.getBoard().getBoardSquareAt(i - 1, j - 1);
                BoardSquarePane bsp = new BoardSquarePane(this.game.getBoard(), boardSquare);
                Board board = this.game.getBoard();

                // event handler for clicking source then target
                bsp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    // dont allow click if not player's turn or it's computer vs computer
                    if ((game.getMode() != Game.GameMode.PVP && game.getCurrentTurn() != game.getPlayer1Color()) || game.getMode() == Game.GameMode.CVC) {
                        return;
                    }
                    // if a square is already selected, move its piece
                    if (board.getSelectedSquare() != null && !boardSquare.equals(board.getSelectedSquare())) {
                        if (board.movePiece(board.getSelectedSquare(), boardSquare)) {
                            board.resetHighlightedSquares();
                            board.deselectSquare();
                            if (board.checkPromotion()) {
                                handlePawnPromotion();
                            }
                            this.game.executeTurn();
                            return;
                        }
                    }
                    // Let a user select a source square
                    if (!boardSquare.isSelected() && boardSquare.isOccupied() && boardSquare.getPiece().getColor().equals(this.game.getCurrentTurn())) {
                        // select square clicked and highlight all possible moves
                        board.selectSquare(boardSquare);
                        board.resetHighlightedSquares();
                        for (BoardSquare bs : boardSquare.getPiece().getAvailableMoves(board, boardSquare)) {
                            board.addHighlightedSquare(bs);
                        }
                    } else if (boardSquare.isSelected()) {
                        // deselect and unhighlight everything if same square clicked again
                        board.deselectSquare();
                        board.resetHighlightedSquares();
                    }
                    updateBoardSquares();
                });

                // event handlers for drag and drop movement
                // triggered when the drag starts on bsp
                bsp.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
                    // dont allow click if not player's turn or it's computer vs computer
                    if ((game.getMode() != Game.GameMode.PVP && game.getCurrentTurn() != game.getPlayer1Color()) || game.getMode() == Game.GameMode.CVC) {
                        return;
                    }
                    if (!boardSquare.isOccupied() || !boardSquare.getPiece().getColor().equals(game.getCurrentTurn())) {
                        return;
                    }
                    // set the drag state and select/highlight the squares necessary; update the grid to reflect
                    bsp.setDragActive(true);
                    board.selectSquare(boardSquare);
                    board.resetHighlightedSquares();
                    for (BoardSquare bs : boardSquare.getPiece().getAvailableMoves(board, boardSquare)) {
                        board.addHighlightedSquare(bs);
                    }
                    updateBoardSquares();

                    // have the label follow the cursor
                    bsp.getLabel().setTranslateX(e.getX() - bsp.getWidth() / 2);
                    bsp.getLabel().setTranslateY(e.getY() - bsp.getHeight() / 2);
                    bsp.toFront();

                    // highlight the target square
                    BoardSquarePane target = getBoardSquarePaneAt(e.getSceneX(), e.getSceneY());
                    if (target != null) {
                        if (boardSquare.getPiece().legalMove(board, boardSquare, target.getBoardSquare(), true)) {
                            target.setDragTarget(true);
                            target.update();
                        }
                    }

                });

                bsp.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                    if (!bsp.isDragActive()) {
                        // nothing to do if there is no active drag
                        return;
                    }
                    BoardSquarePane target = getBoardSquarePaneAt(e.getSceneX(), e.getSceneY());
                    if (target != null && board.movePiece(boardSquare, target.getBoardSquare())) {
                        board.resetHighlightedSquares();
                        board.deselectSquare();
                        if (board.checkPromotion()) {
                            handlePawnPromotion();
                        }
                        this.game.executeTurn();
                    }
                    bsp.setDragActive(false);
                    board.resetHighlightedSquares();
                    board.deselectSquare();
                    redrawGrid();

                });


                this.grid.add(bsp, i, Board.NUM_ROWS - j);
            }
        }
    }

    private void handlePawnPromotion() {
        BoardSquare bs = game.getBoard().getPromotablePawn();
        chess.core.piece.Color color = bs.getPiece().getColor();
        Piece queen = new Queen(color);
        Piece rook = new Rook(color);
        Piece bishop = new Bishop(color);
        Piece knight = new Knight(color);

        ChoiceDialog<Piece> dialog = new ChoiceDialog<Piece>(queen, rook, bishop, knight);

        dialog.setTitle("Pawn Promotion");
        dialog.setHeaderText("Select a new piece:");
        dialog.setContentText("Piece:");

        Optional<Piece> result = dialog.showAndWait();

        result.ifPresent(piece -> this.game.getBoard().replacePawn(piece, bs));
    }

    /**
     * update each boardsquare
     */
    private void updateBoardSquares() {
        for (Node node : grid.getChildren()) {
            if (node instanceof BoardSquarePane) {
                ((BoardSquarePane) node).setDragTarget(false);
                ((BoardSquarePane) node).update();
            }
        }
    }

    /**
     * Save a game to file. Provide the user a popup to select file
     */
    private void save(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save to file");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fc.showSaveDialog(stage);
        if (file != null) {
            try {
                this.game.save(file);
            } catch (IOException e) {
                // TODO alert
                e.printStackTrace();
            }
        }
    }

    /**
     * Load a game from file. Provide the user a popup to select filepath
     */
    private void load(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load from file");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            try {
                this.game = new Game(file, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Undo the last move
     */
    private void undo() {
        if (!game.undo()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Undo Failed");
            alert.setHeaderText(null);
            alert.setContentText("Could not undo move.");
            alert.showAndWait();
            return;
        }
        redrawGrid();
    }

    /**
     * Show the game log
     */
    private void showGameLog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Log");
        alert.setHeaderText(null);
        alert.setContentText(null);

        Label label = new Label("Game Log:");
        VBox content = new VBox();
        ListView<MoveLabel> list = new ListView<>();
        content.getChildren().addAll(label, list);
        int i = 0;
        for (Move move : game.getBoard().getMoves()) {
            MoveLabel ml = new MoveLabel(move);
            list.getItems().add(ml);
            if (i % 2 == game.getPlayer1Color().ordinal() && game.getMode() != Game.GameMode.PVP && game.getMode() != Game.GameMode.CVC) {
                ml.setDisable(true);
            }
            i++;
        }

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType revertButtonType = new ButtonType("Revert", ButtonBar.ButtonData.OK_DONE);

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(revertButtonType, cancelButtonType);
        alert.getDialogPane().setContent(content);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(revertButtonType) && list.getSelectionModel().getSelectedItem() != null) {
            MoveLabel ml = list.getSelectionModel().getSelectedItem();
            Move move = ml.getMove();
            if (!ml.isDisable()) {
                // undo until move is the last move
                ArrayList<Move> moves = game.getStates().peek().getBoard().getMoves();
                int index = moves.indexOf(move);
                while (index <= moves.size() - 1 && game.undo(true)) {
                    moves = game.getStates().peek().getBoard().getMoves();
                }
                redrawGrid();
            }
        }
    }


    /**
     * Get the BoardSquarePane at a set of x, y coordinates if it exists
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the BoardSquarePane if it exists, else null
     */
    private BoardSquarePane getBoardSquarePaneAt(double x, double y) {

        // top left board square pane
        Node referenceNode = grid.getNodeByRowColumnIndex(0, 1);
        x = x - referenceNode.getLayoutX() + BoardSquarePane.SQUARE_SIZE;
        y = y - referenceNode.getLayoutY() * 2;
        if (!menuBar.isUseSystemMenuBar()) {
            // offset if menubar is not system
            y -= 40;
        }

        // adjust the x coordinate from the top left bsp based on the sie of the square label
        int col = (int) x / BoardSquarePane.SQUARE_SIZE;
        // really not sure why things work perfectly with the 2 * layoutY, but they do
        int row = (int) y / BoardSquarePane.SQUARE_SIZE;

        if (grid.getNodeByRowColumnIndex(row, col) instanceof BoardSquarePane) {
            return (BoardSquarePane) grid.getNodeByRowColumnIndex(row, col);
        }
        return null;
    }

    /**
     * Popup if the game is over
     */
    private void handleGameOver() {

        if (!game.isGameOver()) {
            return;
        }
        GameResult winner = game.getWinner();
        if (winner == GameResult.BLACKWIN_TIME || winner == GameResult.WHITEWIN_TIME) {
            game.getP1Clock().pause();
            game.getP2Clock().pause();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            chess.core.piece.Color color = winner == GameResult.WHITEWIN_TIME ? chess.core.piece.Color.WHITE : chess.core.piece.Color.BLACK;
            alert.setContentText(color + " has run out of time. Continue playing?");

            ButtonType continueButtonType = new ButtonType("Continue");
            ButtonType exitButtonType = new ButtonType("Exit");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(exitButtonType, continueButtonType);

            Platform.runLater(() -> {
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent() || result.get().equals(exitButtonType)) {
                    System.exit(0);
                } else if (result.get().equals(continueButtonType)) {
                    this.game.disableTimer(color);
                }
            });

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            if (winner == GameResult.DRAW) {
                alert.setContentText("The game is a " + winner.toString() + "!");
            } else {
                alert.setContentText(winner.toString() + " has won!");
            }

            ButtonType exitButtonType = new ButtonType("Exit");
            ButtonType newGameButtonType = new ButtonType("New Game");

            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(exitButtonType, newGameButtonType);

            Platform.runLater(() -> {
                Optional<ButtonType> result = alert.showAndWait();
                if (!result.isPresent() || result.get().equals(exitButtonType)) {
                    System.exit(0);
                } else if (result.get().equals(newGameButtonType)) {
                    this.game.end();
                    newGame(true);
                    redrawGrid();
                }
            });
        }
    }

    /**
     * To be called when a turn is complete, update and handle if game is over
     */
    public void turnComplete() {
        redrawGrid();
        handleGameOver();
    }

    /**
     * JavaFX listener, handle resize event
     *
     * @param stage
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void resizeListener(Stage stage, ObservableValue observable, Number oldValue, Number newValue) {
        double size = Math.min(stage.getHeight(), stage.getWidth());
        BoardSquarePane.SQUARE_SIZE = (int) size / 10;

        redrawGrid();
    }

    /**
     * Popup the dialog for a new game with parameters
     *
     * @param redraw should the game be redrawn when the user clicks ok
     */
    private void newGame(boolean redraw) {
        Dialog<GameParameters> dialog = new Dialog<>();
        dialog.setTitle("Select Parameters");
        dialog.setHeaderText("Select game parameters ");
        dialog.setContentText("Parameters:");
        dialog.getDialogPane().setMinWidth(250);
        dialog.getDialogPane().setMinHeight(225);

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        VBox items = new VBox(8);
        dialogPane.setContent(items);

        ChoiceBox<chess.core.piece.Color> colorSelect = new ChoiceBox<>(FXCollections.observableArrayList(chess.core.piece.Color.WHITE, chess.core.piece.Color.BLACK));
        colorSelect.getSelectionModel().select(this.gameParameters.color);
        BorderPane colorSelectBox = new BorderPane();
        colorSelectBox.setLeft(new Label("Color:"));
        colorSelectBox.setRight(colorSelect);
        colorSelect.setMinWidth(175);

        ChoiceBox<Integer> depthSelect = new ChoiceBox<>();
        depthSelect.getItems().addAll(1, 2, 3);
        depthSelect.getSelectionModel().select(Integer.valueOf(this.gameParameters.depth));
        BorderPane depthSelectBox = new BorderPane();
        depthSelectBox.setLeft(new Label("Depth:"));
        depthSelectBox.setRight(depthSelect);
        depthSelect.setMinWidth(175);

        ChoiceBox<Game.GameMode> modeSelect = new ChoiceBox<>(FXCollections.observableArrayList(Game.GameMode.values()));
        BorderPane modeSelectBox = new BorderPane();
        modeSelectBox.setLeft(new Label("Mode:"));
        modeSelectBox.setRight(modeSelect);
        modeSelect.setMinWidth(175);
        modeSelect.getSelectionModel().selectedItemProperty().addListener((observableValue, number, number2) -> {
            items.getChildren().clear();
            if (observableValue.getValue() == Game.GameMode.PVP) {
                items.getChildren().addAll(modeSelectBox);
            } else if (observableValue.getValue() == Game.GameMode.CVC) {
                items.getChildren().addAll(modeSelectBox, depthSelectBox);
            } else if (observableValue.getValue() == Game.GameMode.DUMB_COMPUTER) {
                items.getChildren().addAll(modeSelectBox, colorSelectBox);
            } else {
                items.getChildren().addAll(modeSelectBox, depthSelectBox, colorSelectBox);
            }
            dialog.getDialogPane().setContent(items);
            dialog.getDialogPane().setContent(dialog.getDialogPane().getContent());
        });
        modeSelect.getSelectionModel().select(this.gameParameters.mode); // trigger update ^

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new GameParameters(depthSelect.getValue(), colorSelect.getValue(), modeSelect.getValue());
            }
            return null;
        });
        Optional<GameParameters> result = dialog.showAndWait();
        if (result.isPresent()) {
            GameParameters results = result.get();
            this.game = new Game(results.mode, this, results.color, results.depth);
            this.gameParameters = results;
            if (redraw) {
                redrawGrid();
            }
        } else {
            Platform.exit();
        }

    }

    private class GameParameters {
        int depth;
        chess.core.piece.Color color;
        Game.GameMode mode;

        GameParameters(int depth, chess.core.piece.Color color, Game.GameMode mode) {
            this.depth = depth;
            this.color = color;
            this.mode = mode;
        }

        GameParameters() {
            this(2, chess.core.piece.Color.WHITE, Game.GameMode.DEFAULT);
        }
    }
}
