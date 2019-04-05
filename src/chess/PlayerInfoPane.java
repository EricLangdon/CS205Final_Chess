package chess;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class PlayerInfoPane extends VBox {

    private chess.Color player;
    private Game game;

    private static final Color TEXT_COLOR = Color.web("#ccc");

    public PlayerInfoPane(Game game, chess.Color player) {
        super(0);
        this.player = player;
        this.game = game;
        draw();
    }

    private void draw() {
        this.setPrefHeight(395);
        this.setPadding(new Insets(0, 10, 0, 10));
        this.setAlignment(Pos.TOP_LEFT);

        Label timerLabel = new Label("Time Left");
        timerLabel.setFont(new Font(32));
        timerLabel.setTextFill(TEXT_COLOR);
        timerLabel.setPadding(new Insets(10, 0,0,0));

        Label timer = new Label("5:00");
        timer.setFont(new Font(30));
        timer.setTextFill(TEXT_COLOR);
        timer.setPadding(new Insets(0, 0,8,0));

        Label scoreLabel = new Label("Score");
        scoreLabel.setFont(new Font(32));
        scoreLabel.setTextFill(TEXT_COLOR);
        scoreLabel.setPadding(new Insets(8, 0,0,0));

        Label score = new Label(this.game.getScore(player) + " points");
        score.setFont(new Font(30));
        score.setTextFill(TEXT_COLOR);
        score.setPadding(new Insets(0, 0,8,0));

        Label capturedLabel = new Label("Captured");
        capturedLabel.setFont(new Font(32));
        capturedLabel.setTextFill(TEXT_COLOR);
        capturedLabel.setPadding(new Insets(8, 0,0,0));

        FlowPane captured = new FlowPane();
        for (Piece piece : this.game.getBoard().getCaptured()) {
            if (piece.getColor() == player) continue;
            Label label = new Label(Character.toString(piece.getUnicode()));
            label.setFont(Font.font(32));
            if(piece.getColor() == chess.Color.BLACK){
                label.setTextFill(Color.web("#111"));
            }else {
                label.setTextFill(TEXT_COLOR);
            }
            captured.getChildren().add(label);
        }
        if (captured.getChildren().isEmpty()){
            Label label = new Label("(none)");
            label.setFont(new Font(24));
            label.setTextFill(TEXT_COLOR);
            captured.getChildren().add(label);
        }

        Separator sep = new Separator();
        sep.setMaxWidth(150);
        sep.setOpacity(0.33);
        Separator sep2 = new Separator();
        sep2.setMaxWidth(150);
        sep2.setOpacity(0.33);



        this.getChildren().addAll(timerLabel, timer, sep, scoreLabel, score, sep2, capturedLabel, captured);
    }


}
