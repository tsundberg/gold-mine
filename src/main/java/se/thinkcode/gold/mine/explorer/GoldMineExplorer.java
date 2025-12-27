package se.thinkcode.gold.mine.explorer;

import se.thinkcode.gold.mine.game.GoldMine;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.ArrayList;
import java.util.List;

public class GoldMineExplorer {
    private final GoldMine goldMine;
    private Position exit = null;
    private View[][] map = null;

    public GoldMineExplorer(GoldMine goldMine) {
        this.goldMine = goldMine;
    }

    public Position getExit() {
        return exit;
    }

    public void explore(Explorer explorer) {
        explorer.explore();
    }

    public Position up() {
        goldMine.moveUp();
        return goldMine.currentPosition();
    }

    public Position down() {
        goldMine.moveDown();
        return goldMine.currentPosition();
    }

    public Position right() {
        goldMine.moveRight();
        return goldMine.currentPosition();
    }

    public Position left() {
        goldMine.moveLeft();
        return goldMine.currentPosition();
    }

    public void look() {
        Position position = goldMine.currentPosition();
        View view = goldMine.look();
        updateMap(position, view);
    }

    public View lookUp() {
        View view = goldMine.lookUp();
        Position position = goldMine.currentPosition();
        Position upPosition = getUpPosition(position);
        updateMap(upPosition, view);

        return view;
    }

    private Position getUpPosition(Position position) {
        return new Position(position.x(), position.y() - 1);
    }

    public View lookRight() {
        View view = goldMine.lookRight();
        Position position = goldMine.currentPosition();
        Position rightPosition = getRightPosition(position);
        updateMap(rightPosition, view);

        return view;
    }

    private Position getRightPosition(Position position) {
        return new Position(position.x() + 1, position.y());
    }

    public View lookLeft() {
        View view = goldMine.lookLeft();
        Position position = goldMine.currentPosition();
        Position leftPosition = getLeftPosition(position);
        updateMap(leftPosition, view);

        return view;
    }

    private Position getLeftPosition(Position position) {
        return new Position(position.x() - 1, position.y());
    }

    public View lookDown() {
        View view = goldMine.lookDown();
        Position position = goldMine.currentPosition();
        Position downPosition = getDownPosition(position);
        updateMap(downPosition, view);

        return view;
    }

    private Position getDownPosition(Position position) {
        return new Position(position.x(), position.y() + 1);
    }

    private void updateMap(Position position, View view) {
        map = growMap(position, map);

        int x = position.x();
        int y = position.y();

        map[y][x] = view;
    }

    private View[][] growMap(Position position, View[][] map) {
        int y = position.y() + 1;
        int x = position.x() + 1;
        if (map == null) {
            map = new View[y][x];
        }

        map = growAsNeeded(map, y, x);
        return fixCorners(map);
    }

    private View[][] growAsNeeded(View[][] map, int y, int x) {
        if (map.length < y || map[0].length < x) {
            y = Math.max(map.length, y);
            x = Math.max(map[0].length, x);

            View[][] newMap = new View[y][x];
            for (y = 0; y < map.length; y++) {
                for (x = 0; x < map[0].length; x++) {
                    newMap[y][x] = map[y][x];
                }
            }

            return newMap;
        }

        return map;
    }

    private View[][] fixCorners(View[][] map) {
        View wall = new View("Wall");

        // Upper, left corner
        int depth = 0;
        int right = 0;
        if (map[depth][right] == null &&
                map[depth][right + 1] != null &&
                map[depth][right + 1].equals(wall) &&
                map[depth + 1][right] != null) {
            map[depth][right] = wall;
        }

        // Upper, right corner
        depth = 0;
        right = map[0].length - 1;
        if (map[0][right] == null &&
                map[depth][right - 1] != null &&
                map[depth][right - 1].equals(wall) &&
                map[depth + 1][right] != null) {
            map[depth][right] = wall;
        }

        // Lower, left corner
        depth = map.length - 1;
        right = 0;
        if (map[depth][0] == null &&
                map[depth][right + 1] != null &&
                map[depth][right + 1].equals(wall) &&
                map[depth - 1][right] != null) {
            map[depth][right] = wall;
        }

        // Lower, right corner
        depth = map.length - 1;
        right = map[depth].length - 1;
        if (map[depth][right] == null &&
                map[depth][right - 1] != null &&
                map[depth][right - 1].equals(wall) &&
                map[depth - 1][right] != null) {
            map[depth][right] = wall;
        }

        return map;
    }

    public void lookAround() {
        look();
        lookUp();
        lookRight();
        lookLeft();
        lookDown();
    }

    public String getMap() {
        if (map == null) {
            return "";
        }

        View exit = new View("Exit");
        View wall = new View("Wall");
        View home = new View("Home");
        View empty = new View("Empty");

        List<String> matrix = new ArrayList<>();
        for (View[] views : map) {
            StringBuilder row = new StringBuilder();
            for (View view : views) {
                if (view == null) {
                    row.append("?");
                    continue;
                }
                if (view.equals(exit)) {
                    row.append("E");
                    continue;
                }
                if (view.equals(wall)) {
                    row.append("X");
                    continue;
                }
                if (view.equals(home)) {
                    row.append("H");
                    continue;
                }
                if (view.equals(empty)) {
                    row.append(" ");
                }
            }
            matrix.add(row.toString());
        }

        StringBuilder transformedMap = new StringBuilder();
        for (String row : matrix) {
            transformedMap.append(row).append("\n");
        }

        return transformedMap.toString();
    }

    public View getView(Position position) {
        int y = position.y();
        int x = position.x();

        return map[y][x];
    }

    public Position currentPosition() {
        return goldMine.currentPosition();
    }

    static String clearScreen() {
        // https://espterm.github.io/docs/VT100%20escape%20codes.html
        // https://stackoverflow.com/questions/48773272/write-print-to-the-bottom-of-terminal

        return "\033[2J";
    }

    static String cursorHome() {
        return "\033[H";
    }

    static String cursorTo(int row, int column) {
        return String.format("\033[%d;%dH", row, column);
    }
}
