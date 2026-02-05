package se.thinkcode.gold.mine.util;

import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.*;

public class AStarPathfinder {

    public List<String> findPath(View[][] map, Position start, Position goal) {
        if (start.equals(goal)) {
            return List.of();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Set<Position> closedSet = new HashSet<>();
        openSet.add(new Node(start, 0, MapUtils.manhattan(start, goal), null));

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
            if (MapUtils.isNotWalkable(map, nx, ny)) {
                continue;
            }

            Position neighbor = new Position(nx, ny);
            if (closedSet.contains(neighbor)) {
                continue;
            }

            int g = current.g + 1;
            int f = g + MapUtils.manhattan(neighbor, goal);
            openSet.add(new Node(neighbor, g, f, current));
        }
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
