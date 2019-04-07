package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class ChessGUI extends Application {

    private Game game;
    private GridPane grid;
    private BorderPane bp;

    private static final Color BACKGROUND_COLOR = Color.DARKSLATEGRAY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main, 900, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");

        bp = new BorderPane();
        main.setCenter(bp);

        // menubars
        MenuBar menuBar = new MenuBar();
        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
        }
        Menu fileMenu = new Menu("File");

        Menu newMenu = new Menu("New Game");
        for (Game.GameMode mode : Game.GameMode.values()) {
            MenuItem item = new MenuItem(mode.toString());
            item.setOnAction(event -> {
                this.game = new Game(mode);
                updateGrid();
            });
            newMenu.getItems().add(item);
        }

        // save
        MenuItem saveItem = new MenuItem("Save Game");
        saveItem.setOnAction(e -> this.save(primaryStage));
        // load
        MenuItem loadItem = new MenuItem("Load Game");
        loadItem.setOnAction(e -> this.load(primaryStage));
        //exit
        MenuItem exitItem = new MenuItem("Quit");
        exitItem.setOnAction(e -> Platform.exit());

        fileMenu.getItems().addAll(newMenu, saveItem, loadItem, exitItem);
        menuBar.getMenus().add(fileMenu);
        main.setTop(menuBar);

        this.game = new Game(Game.GameMode.SMART_COMPUTER);

        bp.setPadding(new Insets(0));
        bp.setLeft(null);

        // center grid with actual board
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setCenter(grid);
        updateGrid();

        // update player info on a timer so the timer appears to countdown
        Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> updatePlayerInfo());
            }
        }, 0, 500);

        primaryStage.show();

    }

    private void updatePlayerInfo() {
        bp.setTop(new PlayerInfoPane(this.game, chess.Color.BLACK));
        bp.setBottom(new PlayerInfoPane(this.game, chess.Color.WHITE));
    }

    /**
     * Redraw the entire board
     */
    private void updateGrid() {
        updatePlayerInfo();
        this.grid.getChildren().clear();
        for (int i = 0; i < Board.NUM_COLS + 1; i++) {
            for (int j = 0; j < Board.NUM_ROWS + 1; j++) {
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

                BoardSquare boardSquare = game.getBoard().getBoardSquareAt(i - 1, j - 1);
                BoardSquarePane bsp = new BoardSquarePane(boardSquare);
                Board board = this.game.getBoard();
                bsp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    // if a square is already selected, move its piece
                    if (board.getSelectedSquare() != null && !boardSquare.equals(board.getSelectedSquare())) {
                        if (board.movePiece(board.getSelectedSquare(), boardSquare)) {
                            board.resetHighlightedSquares();
                            board.deselectSquare();
                            this.game.executeTurn();
                        }
                    } else {
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
                    }
                    updateGrid();
                });
                this.grid.add(bsp, i, Board.NUM_ROWS - j);
            }
        }

    }

    /**
     * Save a game to file. Provide the user a popup to select file
     */
    private void save(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load from file");
        File file = fc.showSaveDialog(stage);
        if (file != null) {
            this.game.save(file);
        }
    }

    /**
     * Load a game from file. Provide the user a popup to select filepath
     */
    private void load(Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save to file");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fc.showOpenDialog(stage);
        if (file != null) {
            this.game.load(file);
        }
        // TODO implement
    }

    public static void main(String[] args) {
        launch(args);
    }
}
