package se.thinkcode.gold.mine.explorer;

import se.thinkcode.gold.mine.game.GoldMine;
import se.thinkcode.gold.mine.model.Player;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.ArrayList;
import java.util.List;

public class GoldMineExplorer {
    private final GoldMine goldMine;
    private final Player player = new Player("mas");
    private Position exit = null;
    private View[][] map = null;
    private int mineWidth = -1;
    private int mineHeight = -1;

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
        goldMine.moveUp(player);
        return goldMine.currentPosition(player);
    }

    public Position down() {
        goldMine.moveDown(player);
        return goldMine.currentPosition(player);
    }

    public Position right() {
        goldMine.moveRight(player);
        return goldMine.currentPosition(player);
    }

    public Position left() {
        goldMine.moveLeft(player);
        return goldMine.currentPosition(player);
    }

    public void look() {
        Position position = goldMine.currentPosition(player);
        View view = goldMine.look(player);
        updateMap(position, view);
    }

    public View lookUp() {
        View view = goldMine.lookUp(player);
        Position position = goldMine.currentPosition(player);
        Position upPosition = getUpPosition(position);
        updateMap(upPosition, view);

        return view;
    }

    private Position getUpPosition(Position position) {
        return new Position(position.x(), position.y() - 1);
    }

    public View lookRight() {
        Position position = goldMine.currentPosition(player);
        if (mineWidth > 0 && position.x() + 1 >= mineWidth) {
            return null;
        }

        try {
            View view = goldMine.lookRight(player);
            Position rightPosition = getRightPosition(position);
            updateMap(rightPosition, view);
            return view;
        } catch (ArrayIndexOutOfBoundsException e) {
            mineWidth = position.x() + 1;
            return null;
        }
    }

    private Position getRightPosition(Position position) {
        return new Position(position.x() + 1, position.y());
    }

    public View lookLeft() {
        View view = goldMine.lookLeft(player);
        Position position = goldMine.currentPosition(player);
        Position leftPosition = getLeftPosition(position);
        updateMap(leftPosition, view);

        return view;
    }

    private Position getLeftPosition(Position position) {
        return new Position(position.x() - 1, position.y());
    }

    public View lookDown() {
        Position position = goldMine.currentPosition(player);
        if (mineHeight > 0 && position.y() + 1 >= mineHeight) {
            return null;
        }

        try {
            View view = goldMine.lookDown(player);
            Position downPosition = getDownPosition(position);
            updateMap(downPosition, view);
            return view;
        } catch (ArrayIndexOutOfBoundsException e) {
            mineHeight = position.y() + 1;
            return null;
        }
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
            int newHeight = Math.max(map.length, y);
            int newWidth = Math.max(map[0].length, x);
            return copyMap(map, newHeight, newWidth);
        }

        return map;
    }

    private View[][] copyMap(View[][] source, int newHeight, int newWidth) {
        View[][] newMap = new View[newHeight][newWidth];
        for (int y = 0; y < source.length; y++) {
            System.arraycopy(source[y], 0, newMap[y], 0, source[0].length);
        }
        return newMap;
    }

    private View[][] fixCorners(View[][] map) {
        if (map.length < 2 || map[0].length < 2) {
            return map;
        }

        View wall = View.WALL;
        int bottom = map.length - 1;
        int right = map[0].length - 1;

        fixCorner(map, 0, 0, 1, 1, wall);
        fixCorner(map, 0, right, 1, right - 1, wall);
        fixCorner(map, bottom, 0, bottom - 1, 1, wall);
        fixCorner(map, bottom, right, bottom - 1, right - 1, wall);

        return map;
    }

    private void fixCorner(View[][] map, int row, int col, int adjacentRow, int adjacentCol, View wall) {
        if (map[row][col] == null &&
                map[row][adjacentCol] != null &&
                map[row][adjacentCol].equals(wall) &&
                map[adjacentRow][col] != null) {
            map[row][col] = wall;
        }
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

        List<String> rows = buildRows();
        return joinRows(rows);
    }

    private List<String> buildRows() {
        List<String> rows = new ArrayList<>();
        for (View[] views : map) {
            StringBuilder row = new StringBuilder();
            for (View view : views) {
                row.append(viewToChar(view));
            }
            rows.add(row.toString());
        }
        return rows;
    }

    private String joinRows(List<String> rows) {
        StringBuilder result = new StringBuilder();
        for (String row : rows) {
            result.append(row).append("\n");
        }
        return result.toString();
    }

    private char viewToChar(View view) {
        if (view == null) {
            return '?';
        }
        return view.type().toChar();
    }

    public View[][] getViewMap() {
        return map;
    }

    public View getView(Position position) {
        int y = position.y();
        int x = position.x();

        return map[y][x];
    }

    public Position currentPosition() {
        return goldMine.currentPosition(player);
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
