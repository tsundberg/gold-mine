package se.thinkcode.gold.mine.util;

import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public class MapUtils {
    public static boolean isNotWalkable(View[][] map, int x, int y) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return true;
        }
        View view = map[y][x];
        return view == null || view.equals(View.WALL);
    }

    public static int manhattan(Position a, Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }
}
