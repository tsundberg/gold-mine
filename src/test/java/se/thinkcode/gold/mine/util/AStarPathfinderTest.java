package se.thinkcode.gold.mine.util;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.Direction;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AStarPathfinderTest {
    private final AStarPathfinder pathfinder = new AStarPathfinder();

    @Test
    void should_return_empty_path_when_start_is_goal() {
        View[][] map = {
                {new View("Wall"), new View("Wall"), new View("Wall")},
                {new View("Wall"), new View("Home"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Wall")}
        };

        Position start = new Position(1, 1);
        Position goal = new Position(1, 1);

        List<Direction> actual = pathfinder.findPath(map, start, goal);

        assertThat(actual).isEmpty();
    }

    @Test
    void should_find_path_to_exit() {
        List<Direction> expectedSteps = List.of(Direction.DOWN, Direction.DOWN);

        View[][] map = {
                {new View("Wall"), new View("Wall"), new View("Wall"), new View("Wall")},
                {new View("Wall"), new View("Empty"), new View("Empty"), new View("Wall")},
                {new View("Wall"), new View("Home"), new View("Empty"), new View("Wall")},
                {new View("Wall"), new View("Empty"), new View("Empty"), new View("Wall")},
                {new View("Wall"), new View("Exit"), new View("Wall"), new View("Wall")}
        };

        Position start = new Position(1, 2);
        Position goal = new Position(1, 4);

        List<Direction> actual = pathfinder.findPath(map, start, goal);

        assertThat(actual).isEqualTo(expectedSteps);
    }

    @Test
    void should_find_path_with_a_turn() {
        View[][] map = {
                {new View("Wall"), new View("Wall"), new View("Wall"), new View("Wall")},
                {new View("Wall"), new View("Home"), new View("Empty"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Empty"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Exit"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Wall"), new View("Wall")}
        };

        Position start = new Position(1, 1);
        Position goal = new Position(2, 3);

        List<Direction> actual = pathfinder.findPath(map, start, goal);

        assertThat(actual).isEqualTo(List.of(Direction.RIGHT, Direction.DOWN, Direction.DOWN));
    }

    @Test
    void should_return_empty_path_when_no_path_exists() {
        View[][] map = {
                {new View("Wall"), new View("Wall"), new View("Wall")},
                {new View("Wall"), new View("Home"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Wall")},
                {new View("Wall"), new View("Exit"), new View("Wall")},
                {new View("Wall"), new View("Wall"), new View("Wall")}
        };

        Position start = new Position(1, 1);
        Position goal = new Position(1, 3);

        List<Direction> actual = pathfinder.findPath(map, start, goal);

        assertThat(actual).isEmpty();
    }
}
