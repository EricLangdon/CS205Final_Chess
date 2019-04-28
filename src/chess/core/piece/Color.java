package chess.core.piece;

public enum Color {
    BLACK, WHITE;

    public static Color fromValue(int val) {
        for (Color c : Color.values()) {
            if (c.ordinal() == val) {
                return c;
            }
        }
        return null;
    }

    /**
     * @return the opposite color
     */
    public Color other() {
        if (this.equals(BLACK)) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
