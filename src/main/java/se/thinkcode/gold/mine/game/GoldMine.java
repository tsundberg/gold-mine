package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public class GoldMine {
    private final char[][] map;
    private Position position = null;

    public GoldMine(Level level) {
        map = createMap(level);
        position = findStartPosition(map);
    }

    private char[][] createMap(Level level) {
        String[] parts = level.level().split("\n");
        char[][] map = new char[parts.length][parts[0].length()];
        for (int y = 0; y < parts.length; y++) {
            String row = parts[y];
            for (int x = 0; x < parts[y].length(); x++) {
                char col = row.charAt(x);
                map[y][x] = col;
            }
        }

        return map;
    }

    private Position findStartPosition(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'H') {
                    return new Position(x, y);
                }
            }
        }

        throw new RuntimeException("No starting position found");
    }

    public Position currentPosition() {
        return position;
    }

    public void moveLeft() {
        int x = position.x() - 1;
        int y = position.y();

        position = new Position(x, y);
    }

    public void moveRight() {
        int x = position.x() + 1;
        int y = position.y();

        position = new Position(x, y);
    }

    public void moveUp() {
        int x = position.x();
        int y = position.y() - 1;

        position = new Position(x, y);
    }

    public void moveDown() {
        int x = position.x();
        int y = position.y() + 1;

        position = new Position(x, y);
    }

    public View lookLeft() {
        int x = position.x() - 1;
        int y = position.y();

        return getView(y, x);
    }

    public View lookRight() {
        int x = position.x() + 1;
        int y = position.y();

        return getView(y, x);
    }

    public View lookUp() {
        int x = position.x();
        int y = position.y() - 1;

        return getView(y, x);
    }

    public View lookDown() {
        int x = position.x();
        int y = position.y() + 1;

        return getView(y, x);
    }

    View getView(int y, int x) {
        if (map[y][x] == 'X') {
            return new View("Wall");
        }
        if (map[y][x] == 'E') {
            return new View("Exit");
        }
        if (map[y][x] == ' ') {
            return new View("Empty");
        }

        throw new RuntimeException("Unknown map element");
    }
}
