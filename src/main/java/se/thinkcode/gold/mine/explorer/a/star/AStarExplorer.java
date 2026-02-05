package se.thinkcode.gold.mine.explorer.a.star;

import se.thinkcode.gold.mine.explorer.Explorer;
import se.thinkcode.gold.mine.explorer.GoldMineExplorer;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.*;

public class AStarExplorer implements Explorer {
    private final GoldMineExplorer goldMineExplorer;
    private static final View WALL = new View("Wall");
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
        List<String> path = findPath(map, goldMineExplorer.currentPosition(), target);

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
                if (candidate != null && manhattan(current, candidate) < bestDistance) {
                    bestDistance = manhattan(current, candidate);
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
            if (isNotWalkable(map, nx, ny)) {
                continue;
            }
            Position candidate = new Position(nx, ny);
            int distance = manhattan(current, candidate);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = candidate;
            }
        }

        return best;
    }

    private boolean isNotWalkable(View[][] map, int x, int y) {
        if (y < 0 || y >= map.length || x < 0 || x >= map[0].length) {
            return true;
        }
        View view = map[y][x];
        return view == null || view.equals(WALL);
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
                int distance = manhattan(current, candidate);
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
        if (view == null || view.equals(WALL)) {
            return false;
        }
        if (lookedAround.contains(new Position(x, y))) {
            return false;
        }
        return x == 0 || x == map[0].length - 1 || y == 0 || y == map.length - 1;
    }

    public List<String> findPath(View[][] map, Position start, Position goal) {
        if (start.equals(goal)) {
            return List.of();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Position> closedSet = new HashSet<>();
        openSet.add(new Node(start, 0, manhattan(start, goal), null));

        return searchPath(map, goal, openSet, closedSet);
    }

    private List<String> searchPath(View[][] map, Position goal, PriorityQueue<Node> openSet, Set<Position> closedSet) {
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.position.equals(goal)) {
                return reconstructPath(current);
            }

            if (closedSet.contains(current.position)) {
                continue;
            }
            closedSet.add(current.position);

            expandNeighbors(map, current, goal, openSet, closedSet);
        }

        return List.of();
    }

    private void expandNeighbors(View[][] map, Node current, Position goal, PriorityQueue<Node> openSet, Set<Position> closedSet) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        for (int[] dir : directions) {
            int nx = current.position.x() + dir[0];
            int ny = current.position.y() + dir[1];
            if (isNotWalkable(map, nx, ny)) {
                continue;
            }

            Position neighbor = new Position(nx, ny);
            if (closedSet.contains(neighbor)) {
                continue;
            }

            int g = current.g + 1;
            int f = g + manhattan(neighbor, goal);
            openSet.add(new Node(neighbor, g, f, current));
        }
    }

    private int manhattan(Position a, Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private List<String> reconstructPath(Node node) {
        List<String> path = new ArrayList<>();
        while (node.parent != null) {
            int dx = node.position.x() - node.parent.position.x();
            int dy = node.position.y() - node.parent.position.y();
            path.add(directionFromDelta(dx, dy));
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private String directionFromDelta(int dx, int dy) {
        if (dx == 1) {
            return "right";
        }
        if (dx == -1) {
            return "left";
        }
        if (dy == 1) {
            return "down";
        }
        if (dy == -1) {
            return "up";
        }
        throw new IllegalArgumentException("Invalid delta: " + dx + ", " + dy);
    }

    private record Node(Position position, int g, int f, Node parent) {
    }
}
