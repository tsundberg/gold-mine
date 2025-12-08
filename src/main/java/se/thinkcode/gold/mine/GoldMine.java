package se.thinkcode.gold.mine;


public class GoldMine {
    private final char[][] map;
    private Position position = null;

    public GoldMine(Level level) {
        map = createMap(level);

        findStartPosition(map);
    }

    private static char[][] createMap(Level level) {
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

    private void findStartPosition(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == 'H') {
                    position = new Position(x, y);
                }
            }
        }
    }

    public View lookLeft() {
        int x = position.x() - 1;
        int y = position.y();

        if (map[y][x] == 'X') {
            return new View("Wall");
        }
        if (map[y][x] == ' ') {
            return new View("Empty");
        }

        throw new RuntimeException("Unknown map element");
    }

    public View lookRight() {
        int x = position.x() + 1;
        int y = position.y();

        if (map[y][x] == 'X') {
            return new View("Wall");
        }
        if (map[y][x] == ' ') {
            return new View("Empty");
        }

        throw new RuntimeException("Unknown map element");
    }

    public View lookUp() {
        int x = position.x();
        int y = position.y() - 1;

        if (map[y][x] == 'X') {
            return new View("Wall");
        }
        if (map[y][x] == ' ') {
            return new View("Empty");
        }

        throw new RuntimeException("Unknown map element");
    }

    public View lookDown() {
        int x = position.x();
        int y = position.y() + 1;

        if (map[y][x] == 'X') {
            return new View("Wall");
        }
        if (map[y][x] == ' ') {
            return new View("Empty");
        }

        throw new RuntimeException("Unknown map element");
    }
}
