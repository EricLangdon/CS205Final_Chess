package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    private Game game;
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.game = new Game(Game.GameMode.DUMB_COMPUTER);

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(0));
        bp.setLeft(null);

        // right pane
        VBox right = new VBox();
        right.setPrefWidth(200);
        right.setBackground(new Background(new BackgroundFill(Color.BROWN.darker(), CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setRight(right);

        // center grid with actual board
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.grid.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        bp.setCenter(grid);
        updateGrid();

        Scene scene = new Scene(bp, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chess");
        primaryStage.show();

    }

    /**
     * Redraw the entire board
     */
    private void updateGrid() {
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
                        if(board.movePiece(board.getSelectedSquare(), boardSquare)) {
                            board.resetHighlightedSquares();
                            board.deselectSquare();
                            this.game.executeTurn();
                        }
                    }else {
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

    public static void main(String[] args) {
        launch(args);
    }
}
