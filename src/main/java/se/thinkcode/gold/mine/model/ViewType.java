package se.thinkcode.gold.mine.model;

public enum ViewType {
    WALL('X'),
    GOLD('G'),
    EXIT('E'),
    HOME('H'),
    EMPTY(' ');

    private final char mapChar;

    ViewType(char mapChar) {
        this.mapChar = mapChar;
    }

    public char toChar() {
        return mapChar;
    }

    public static ViewType fromChar(char c) {
        if (c == 'X') {
            return WALL;
        }
        if (c == 'E') {
            return EXIT;
        }
        if (c == 'G') {
            return GOLD;
        }
        if (c == 'H') {
            return HOME;
        }
        if (c == ' ') {
            return EMPTY;
        }
        throw new IllegalArgumentException("Unknown map char: " + c);
    }
}
