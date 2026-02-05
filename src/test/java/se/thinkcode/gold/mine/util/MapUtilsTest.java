package se.thinkcode.gold.mine.util;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import static org.assertj.core.api.Assertions.assertThat;

class MapUtilsTest {

    @Test
    void should_calculate_manhattan_distance() {
        Position a = new Position(1, 1);
        Position b = new Position(4, 5);

        int actual = MapUtils.manhattan(a, b);

        assertThat(actual).isEqualTo(7);
    }

    @Test
    void should_identify_wall_as_not_walkable() {
        View[][] map = {
                {View.WALL, View.EMPTY},
                {View.EMPTY, View.EXIT}
        };

        boolean actual = MapUtils.isNotWalkable(map, 0, 0);

        assertThat(actual).isTrue();
    }

    @Test
    void should_identify_out_of_bounds_as_not_walkable() {
        View[][] map = {
                {View.EMPTY, View.EMPTY},
                {View.EMPTY, View.EMPTY}
        };

        assertThat(MapUtils.isNotWalkable(map, -1, 0)).isTrue();
        assertThat(MapUtils.isNotWalkable(map, 0, -1)).isTrue();
        assertThat(MapUtils.isNotWalkable(map, 2, 0)).isTrue();
        assertThat(MapUtils.isNotWalkable(map, 0, 2)).isTrue();
    }

    @Test
    void should_identify_null_as_not_walkable() {
        View[][] map = {
                {View.EMPTY, null},
                {View.EMPTY, View.EMPTY}
        };

        boolean actual = MapUtils.isNotWalkable(map, 1, 0);

        assertThat(actual).isTrue();
    }

    @Test
    void should_identify_empty_cell_as_walkable() {
        View[][] map = {
                {View.WALL, View.EMPTY},
                {View.EMPTY, View.EXIT}
        };

        boolean actual = MapUtils.isNotWalkable(map, 1, 0);

        assertThat(actual).isFalse();
    }
}
