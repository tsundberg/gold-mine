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
        int y = 1;
        int x = 1;
        if (map == null) {
            map = new View[y][x];
        }

        while (y <= position.y()) {
            y++;
        }
        while (x <= position.x()) {
            x++;
        }

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

    public void lookAround() {
        lookUp();
        lookRight();
        lookLeft();
        lookDown();
    }

    public String getMap() {
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

        fixFirstRow(matrix);
        fixLastRow(matrix);

        StringBuilder transformedMap = new StringBuilder();
        for (String row : matrix) {
            transformedMap.append(row).append("\n");
        }

        return transformedMap.toString();
    }

    private void fixFirstRow(List<String> matrix) {
        String first = matrix.getFirst();
        if (first.startsWith("?")) {
            StringBuilder firstRow = new StringBuilder(first);
            firstRow.setCharAt(0, 'X');
            first = firstRow.toString();
        }
        if (first.endsWith("?")) {
            StringBuilder firstRow = new StringBuilder(first);
            firstRow.setCharAt(firstRow.length() - 1, 'X');
            first = firstRow.toString();
        }
        matrix.removeFirst();
        matrix.addFirst(first);
    }

    private void fixLastRow(List<String> matrix) {
        String last = matrix.getLast();
        if (last.startsWith("?")) {
            StringBuilder lastRow = new StringBuilder(last);
            lastRow.setCharAt(0, 'X');
            last = lastRow.toString();
        }
        if (last.endsWith("?")) {
            StringBuilder lastRow = new StringBuilder(last);
            lastRow.setCharAt(lastRow.length() - 1, 'X');
            last = lastRow.toString();
        }
        matrix.removeLast();
        matrix.addLast(last);
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
