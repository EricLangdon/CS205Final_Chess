package chess.core.piece;

public enum Color {
    BLACK, WHITE;

    /**
     * fromValue
     * returns the color that is at the same value position passed in
     *
     * @param val the position of the enum
     * @return the color at the specified ordinal position
     */
    public static Color fromValue(int val) {
        for (Color c : Color.values()) {
            if (c.ordinal() == val) {
                return c;
            }
        }
        return null;
    }

    /**
     * other
     *
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
