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
                target = findExplorationTarget();
                continue;
            }

            View[][] map = goldMineExplorer.getViewMap();
            List<String> path = findPath(map, current, target);

            if (path.isEmpty()) {
                // Can't reach this target, mark it as visited to avoid retrying
                lookedAround.add(target);
                target = findExplorationTarget();
                continue;
            }

            for (String step : path) {
                executeStep(step);
                safeLookAround();
            }

            target = findExplorationTarget();
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
        switch (step) {
            case "up" -> goldMineExplorer.up();
            case "down" -> goldMineExplorer.down();
            case "left" -> goldMineExplorer.left();
            case "right" -> goldMineExplorer.right();
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

        int rows = map.length;
        int cols = map[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (map[y][x] != null) {
                    continue;
                }
                int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
                for (int[] dir : directions) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (ny < 0 || ny >= rows || nx < 0 || nx >= cols) {
                        continue;
                    }
                    View view = map[ny][nx];
                    if (view != null && !view.equals(WALL)) {
                        Position candidate = new Position(nx, ny);
                        int distance = manhattan(current, candidate);
                        if (distance < bestDistance) {
                            bestDistance = distance;
                            bestTarget = candidate;
                        }
                    }
                }
            }
        }

        return bestTarget;
    }

    private Position findNearestUnvisitedEdgeCell(View[][] map, Position current) {
        Position bestTarget = null;
        int bestDistance = Integer.MAX_VALUE;

        int rows = map.length;
        int cols = map[0].length;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                View view = map[y][x];
                if (view == null || view.equals(WALL)) {
                    continue;
                }
                Position candidate = new Position(x, y);
                if (lookedAround.contains(candidate)) {
                    continue;
                }
                boolean isEdge = (x == 0 || x == cols - 1 || y == 0 || y == rows - 1);
                if (isEdge) {
                    int distance = manhattan(current, candidate);
                    if (distance < bestDistance) {
                        bestDistance = distance;
                        bestTarget = candidate;
                    }
                }
            }
        }

        return bestTarget;
    }

    public List<String> findPath(View[][] map, Position start, Position goal) {
        if (start.equals(goal)) {
            return List.of();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Position> closedSet = new HashSet<>();

        openSet.add(new Node(start, 0, manhattan(start, goal), null));

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

            if (ny < 0 || ny >= map.length || nx < 0 || nx >= map[0].length) {
                continue;
            }

            Position neighbor = new Position(nx, ny);

            if (closedSet.contains(neighbor)) {
                continue;
            }

            View view = map[ny][nx];
            if (view == null || view.equals(WALL)) {
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

            if (dx == 1) path.add("right");
            else if (dx == -1) path.add("left");
            else if (dy == 1) path.add("down");
            else if (dy == -1) path.add("up");

            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        final Position position;
        final int g;
        final int f;
        final Node parent;

        Node(Position position, int g, int f, Node parent) {
            this.position = position;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }
    }

    // https://medium.com/@AlexanderObregon/pathfinding-with-the-a-star-algorithm-in-java-3a66446a2352
    // https://en.wikipedia.org/wiki/A*_search_algorithm
}
