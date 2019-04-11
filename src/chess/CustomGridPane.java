package chess;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class CustomGridPane extends GridPane {

    public Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        for (Node node : this.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
}
