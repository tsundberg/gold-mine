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
    private Position maxDimension = new Position(0, 0);

    public GoldMineExplorer(GoldMine goldMine) {
        this.goldMine = goldMine;
    }

    public Position getExit() {
        return exit;
    }

    public void explore() {
        View exit = new View("Exit");
        Position position = goldMine.currentPosition();

        position = exploreUp(position, exit);
        position = exploreLeft(position, exit);
        position = exploreDown(position, exit);
        position = exploreRight(position, exit);
        position = exploreUp(position, exit);
        position = exploreLeft(position, exit);

        Position unknownPosition = unknown();
        while (unknownPosition != null) {
            visit(unknownPosition);
            look();
            unknownPosition = unknown();
        }
    }

    private void visit(Position position) {
        Position currentPosition = goldMine.currentPosition();

        while (!position.equals(currentPosition)) {
            if (currentPosition.y() - position.y() > 0) {
                up();
            }
            if (currentPosition.y() - position.y() < 0) {
                down();
            }

            if (currentPosition.x() - position.x() > 0) {
                left();
            }
            if (currentPosition.x() - position.x() < 0) {
                right();
            }

            currentPosition = goldMine.currentPosition();
        }
    }

    private Position unknown() {
        for (int y = 1; y < maxDimension.y() - 1; y++) {
            for (int x = 1; x < maxDimension.x() - 1; x++) {
                View view = map[y][x];
                if (view == null) {
                    return new Position(x, y);
                }
            }
        }

        return null;
    }

    private Position exploreLeft(Position position, View exit) {
        Position newPosition;
        newPosition = left();
        lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View left = lookLeft();
            if (left.equals(exit)) {
                lookAround();
            } else {
                newPosition = left();
                lookAround();
            }
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreDown(Position position, View exit) {
        Position newPosition;
        newPosition = down();
        lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View down = lookDown();
            if (down.equals(exit)) {
                lookAround();
            } else {
                newPosition = down();
                lookAround();
            }
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreRight(Position position, View exit) {
        Position newPosition;
        newPosition = right();
        lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View right = lookRight();
            if (right.equals(exit)) {
                lookAround();
            } else {
                newPosition = right();
                lookAround();
            }
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreUp(Position position, View exit) {
        Position newPosition;
        newPosition = up();
        lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View up = lookUp();
            if (up.equals(exit)) {
                lookAround();
            } else {
                newPosition = up();
                lookAround();
            }
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
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
