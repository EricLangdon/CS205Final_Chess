package chess.gui;

import chess.core.game.ChessClock;
import chess.core.game.Game;
import chess.core.piece.Piece;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class PlayerInfoPane extends HBox {

    private chess.core.piece.Color player;
    private Game game;
    private ChessClock timer;

    private static final Color TEXT_COLOR = Color.web("#ccc");
    private static final Color BACKGROUND_COLOR = Color.DARKSLATEGRAY;

    public PlayerInfoPane(Game game, chess.core.piece.Color player) {
        super(0);
        this.player = player;
        this.game = game;
        this.timer = new ChessClock(player);
        draw();
    }

    private void draw() {
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0));
        this.setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        Label scoreLabel = new Label("Score: " + this.game.getScore(player));
        scoreLabel.setFont(new Font(22));
        scoreLabel.setTextFill(TEXT_COLOR);

        FlowPane captured = new FlowPane();
        captured.setAlignment(Pos.CENTER_LEFT);
        for (Piece piece : this.game.getBoard().getCaptured()) {
            if (piece.getColor() == player) continue;
            Label label = new Label(Character.toString(piece.getUnicode()));
            label.setFont(Font.font(22));
            if (piece.getColor() == chess.core.piece.Color.BLACK) {
                label.setTextFill(Color.web("#111"));
            } else {
                label.setTextFill(TEXT_COLOR);
            }
            captured.getChildren().add(label);
        }


        Label timer = new Label("Time Left: " + game.getTimeRemaining(player)); //TODO: update time
        timer.setFont(new Font(22));
        timer.setTextFill(TEXT_COLOR);
        timer.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(scoreLabel, captured, timer);
    }


}
