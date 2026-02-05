package se.thinkcode.gold.mine.explorer.a.star;

import se.thinkcode.gold.mine.explorer.Explorer;
import se.thinkcode.gold.mine.explorer.GoldMineExplorer;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;
import se.thinkcode.gold.mine.util.AStarPathfinder;
import se.thinkcode.gold.mine.util.MapUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AStarExplorer implements Explorer {
    private final GoldMineExplorer goldMineExplorer;
    private final AStarPathfinder pathfinder = new AStarPathfinder();
    private final Set<Position> lookedAround = new HashSet<>();

    public AStarExplorer(GoldMineExplorer goldMineExplorer) {
        this.goldMineExplorer = goldMineExplorer;
    }

    @Override
    public void explore() {
        safeLookAround();

        Position target = findExplorationTarget();
        while (target != null) {
            Position current = goldMineExplorer.currentPosition();

            if (target.equals(current)) {
                safeLookAround();
            } else {
                navigateToTarget(target);
            }

            target = findExplorationTarget();
        }
    }

    private void navigateToTarget(Position target) {
        View[][] map = goldMineExplorer.getViewMap();
        List<String> path = pathfinder.findPath(map, goldMineExplorer.currentPosition(), target);

        if (path.isEmpty()) {
            lookedAround.add(target);
            return;
        }

        for (String step : path) {
            executeStep(step);
            safeLookAround();
        }
    }

    private void safeLookAround() {
        Position position = goldMineExplorer.currentPosition();
        lookedAround.add(position);
        goldMineExplorer.look();
        if (position.y() > 0) {
            goldMineExplorer.lookUp();
        }
        if (position.x() > 0) {
            goldMineExplorer.lookLeft();
        }
        goldMineExplorer.lookRight();
        goldMineExplorer.lookDown();
    }

    private void executeStep(String step) {
        if ("up".equals(step)) {
            goldMineExplorer.up();
            return;
        }

        if ("down".equals(step)) {
            goldMineExplorer.down();
            return;
        }

        if ("left".equals(step)) {
            goldMineExplorer.left();
            return;
        }

        if ("right".equals(step)) {
            goldMineExplorer.right();
        }
    }

    private Position findExplorationTarget() {
        View[][] map = goldMineExplorer.getViewMap();
        if (map == null) {
            return null;
        }

        Position current = goldMineExplorer.currentPosition();

        Position target = findNearestUnexploredNeighbor(map, current);
        if (target != null) {
            return target;
        }

        return findNearestUnvisitedEdgeCell(map, current);
    }

    private Position findNearestUnexploredNeighbor(View[][] map, Position current) {
        Position bestTarget = null;
        int bestDistance = Integer.MAX_VALUE;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] != null) {
                    continue;
                }
                Position candidate = closestWalkableNeighbor(map, x, y, current);
                if (candidate != null && MapUtils.manhattan(current, candidate) < bestDistance) {
                    bestDistance = MapUtils.manhattan(current, candidate);
                    bestTarget = candidate;
                }
            }
        }

        return bestTarget;
    }

    private Position closestWalkableNeighbor(View[][] map, int x, int y, Position current) {
        Position best = null;
        int bestDistance = Integer.MAX_VALUE;
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (MapUtils.isNotWalkable(map, nx, ny)) {
                continue;
            }
            Position candidate = new Position(nx, ny);
            int distance = MapUtils.manhattan(current, candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }

        return best;
    }

    private Position findNearestUnvisitedEdgeCell(View[][] map, Position current) {
        Position bestTarget = null;
        int bestDistance = Integer.MAX_VALUE;

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (!isUnvisitedEdgeCell(map, x, y)) {
                    continue;
                }
                Position candidate = new Position(x, y);
                int distance = MapUtils.manhattan(current, candidate);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestTarget = candidate;
                }
            }
        }

        return bestTarget;
    }

    private boolean isUnvisitedEdgeCell(View[][] map, int x, int y) {
        View view = map[y][x];
        if (view == null || view.equals(MapUtils.WALL)) {
            return false;
        }
        if (lookedAround.contains(new Position(x, y))) {
            return false;
        }
        return x == 0 || x == map[0].length - 1 || y == 0 || y == map.length - 1;
    }
}
