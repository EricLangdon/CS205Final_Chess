package chess;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BoardSquarePane extends VBox {
    private BoardSquare boardSquare;

    private static final Color DARK_COLOR = Color.rgb(192, 192, 192);
    private static final Color DARK_COLOR_SELECTED = Color.rgb(162, 162, 182);
    private static final Color DARK_COLOR_HIGHLIGHTED = Color.rgb(172, 172, 182);
    private static final Color LIGHT_COLOR = Color.rgb(255, 255, 255);
    private static final Color LIGHT_COLOR_SELECTED = Color.rgb(225, 225, 245);
    private static final Color LIGHT_COLOR_HIGHLIGHTED = Color.rgb(235, 235, 245);

    /**
     * Constructor
     *
     * @param boardSquare the boardsquare for the pane to represent
     */
    public BoardSquarePane(BoardSquare boardSquare) {
        super(10);
        this.boardSquare = boardSquare;
        draw();
    }

    /**
     * Draw the pane
     */
    public void draw() {
        this.setMinHeight(85);
        this.setMinWidth(85);
        this.setAlignment(Pos.CENTER);
        Color color;

        if (boardSquare.getX() % 2 == 0 && boardSquare.getY() % 2 == 0 || boardSquare.getX() % 2 == 1 && boardSquare.getY() % 2 == 1) {
            if (boardSquare.isSelected()) {
                color = LIGHT_COLOR_SELECTED;
            } else if (boardSquare.isHighlighted()) {
                color = LIGHT_COLOR_HIGHLIGHTED;
            } else {
                color = LIGHT_COLOR;
            }
        } else {
            if (boardSquare.isSelected()) {
                color = DARK_COLOR_SELECTED;
            } else if (boardSquare.isHighlighted()) {
                color = DARK_COLOR_HIGHLIGHTED;
            } else {
                color = DARK_COLOR;
            }
        }

        this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        if (boardSquare.isOccupied()) {
            Label label = new Label(Character.toString(boardSquare.getPiece().getUnicode()));
            label.setFont(Font.font(45));
            this.getChildren().add(label);
        }
    }

    /**
     * get the boardsquare represented by the pane
     *
     * @return the boardsquare represented by the pane
     */
    public BoardSquare getBoardSquare() {
        return boardSquare;
    }
}