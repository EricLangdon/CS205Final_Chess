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
    public static final int SQUARE_SIZE = 85;
    private static final Color DARK_COLOR = Color.rgb(192, 192, 192);
    private static final Color DARK_COLOR_SELECTED = Color.rgb(245, 245, 96);
    private static final Color DARK_COLOR_HIGHLIGHTED = Color.rgb(162, 162, 192);
    private static final Color LIGHT_COLOR = Color.rgb(255, 255, 255);
    private static final Color LIGHT_COLOR_SELECTED = Color.rgb(255, 255, 96);
    private static final Color LIGHT_COLOR_HIGHLIGHTED = Color.rgb(215, 215, 255);

    private Label label = new Label();
    private boolean dragActive = false;
    private boolean dragTarget = false;

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
        this.setMinHeight(SQUARE_SIZE);
        this.setMinWidth(SQUARE_SIZE);
        this.setAlignment(Pos.CENTER);


        label = new Label();
        label.setFont(Font.font(45));
        this.getChildren().add(label);
        update();
    }

    /**
     * Update the color and text of the pane
     */
    public void update() {
        Color color;
        if (boardSquare.getX() % 2 == 0 && boardSquare.getY() % 2 == 0 || boardSquare.getX() % 2 == 1 && boardSquare.getY() % 2 == 1) {
            if (boardSquare.isSelected() || dragTarget) {
                color = DARK_COLOR_SELECTED;
            } else if (boardSquare.isHighlighted()) {
                color = DARK_COLOR_HIGHLIGHTED;
            } else {
                color = DARK_COLOR;
            }
        } else {
            if (boardSquare.isSelected() || dragTarget) {
                color = LIGHT_COLOR_SELECTED;
            } else if (boardSquare.isHighlighted()) {
                color = LIGHT_COLOR_HIGHLIGHTED;
            } else {
                color = LIGHT_COLOR;
            }
        }

        this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        if (boardSquare.isOccupied()) {
            label.setText(Character.toString(boardSquare.getPiece().getUnicode()));
        }
    }

    /**
     * Get the boardsquare represented by the pane
     *
     * @return the boardsquare represented by the pane
     */
    public BoardSquare getBoardSquare() {
        return boardSquare;
    }

    /**
     * Get if the drag state is active
     *
     * @return true if the drag state is active
     */
    public boolean isDragActive() {
        return dragActive;
    }

    /**
     * Set the drag state
     *
     * @param dragActive the drag state
     */
    public void setDragActive(boolean dragActive) {
        this.dragActive = dragActive;
    }

    /**
     * Get if the BoardSquarePane is a target for a drag and drop
     *
     * @return if the BoardSquarePane is a target for a drag and drop
     */
    public boolean isDragTarget() {
        return dragTarget;
    }

    /**
     * Set the boardsquare as a target for a drag and drop
     *
     * @param dragTarget true if the square is a target
     */
    public void setDragTarget(boolean dragTarget) {
        this.dragTarget = dragTarget;
    }

    /**
     * Get the label for the pane
     *
     * @return the label
     */
    public Label getLabel() {
        return label;
    }

}