package chess;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGUI extends Application {

    private Game game;
    private GridPane grid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.game = new Game(Game.GameMode.PVP);

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(5));
        bp.setLeft(null);

        // right pane
        VBox right = new VBox();
        right.setPrefWidth(200);
        bp.setRight(right);

        // center grid with actual board
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
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
        for (int i = 0; i < Board.NUM_COLS; i++) {
            for (int j = 0; j < Board.NUM_ROWS; j++) {
                BoardSquare boardSquare = game.getBoard().getBoardSquareAt(i, j);
                BoardSquarePane bsp = new BoardSquarePane(boardSquare);
                Board board = this.game.getBoard();
                bsp.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    // move the currently selected piece to the square clicked square
                    if (board.getSelectedSquare() != null && !boardSquare.equals(board.getSelectedSquare())) {
                        if(board.movePiece(board.getSelectedSquare(), boardSquare)) {
                            board.resetHighlightedSquares();
                            board.deselectSquare();
                            this.game.executeTurn();
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
