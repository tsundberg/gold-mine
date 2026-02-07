package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.*;

public class GoldMineGame {
    private final char[][] map;
    private Position position = null;
    private final GoldStash stash = new GoldStash();
    private final Points points = new Points();

    public GoldMineGame(Level level) {
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
            for (int x = 0; x < map[0].length; x++) {
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
        Position nextPos = new Position(x, y);

        if (isNotWall(nextPos)) {
            position = nextPos;
        }
    }

    public void moveRight() {
        int x = position.x() + 1;
        int y = position.y();
        Position nextPos = new Position(x, y);

        if (isNotWall(nextPos)) {
            position = nextPos;
        }
    }

    public void moveUp() {
        int x = position.x();
        int y = position.y() - 1;
        Position nextPos = new Position(x, y);

        if (isNotWall(nextPos)) {
            position = nextPos;
        }
    }

    public void moveDown() {
        int x = position.x();
        int y = position.y() + 1;
        Position nextPos = new Position(x, y);

        if (isNotWall(nextPos)) {
            position = nextPos;
        }
    }

    private boolean isNotWall(Position nexPos) {
        char candidate = map[nexPos.y()][nexPos.x()];

        return candidate != 'X';
    }

    public View look() {
        int x = position.x();
        int y = position.y();

        return getView(y, x);
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
        return new View(ViewType.fromChar(map[y][x]));
    }

    public void pickUpGold() {
        int x = position.x();
        int y = position.y();

        char content = map[y][x];
        ViewType currentContent = ViewType.fromChar(content);

        if (currentContent.equals(ViewType.GOLD)) {
            stash.add();
            map[y][x] = ' ';
        }
    }

    public void emptyGoldStash() {
        int x = position.x();
        int y = position.y();

        char content = map[y][x];
        ViewType currentContent = ViewType.fromChar(content);

        if (currentContent.equals(ViewType.HOME)) {
            int gold = stash.empty();
            points.add(gold);
        }
    }

    public GoldStash currentGoldStash() {
        return stash;
    }

    public Points currentPoints() {
        return points;
    }
}
