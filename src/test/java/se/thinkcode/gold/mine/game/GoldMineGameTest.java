package se.thinkcode.gold.mine.game;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldMineGameTest {

    private final Level level0 = new Level("""
            XXX
            XHX
            XXX
            """);

    private final Level level1 = new Level("""
            XXXXX
            X   X
            X H X
            X   X
            XXXXX
            """);

    private final Level level2 = new Level("""
            XXXXX
            X   X
            X H X
            X   E
            XXXXX
            """);

    @Test
    void should_see_wall_to_the_left_level_0() {
        View expected = new View("Wall");
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_to_the_right_level_0() {
        View expected = new View("Wall");
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_up_level_0() {
        View expected = new View("Wall");
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_down_level_0() {
        View expected = new View("Wall");
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_to_the_left_level_1() {
        View expected = new View("Empty");
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_to_the_right_level_1() {
        View expected = new View("Empty");
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_up_level_1() {
        View expected = new View("Empty");
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_down_level_1() {
        View expected = new View("Empty");
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_get_current_position() {
        GoldMineGame goldMine = new GoldMineGame(level0);

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(1, 1));
    }

    @Test
    void should_move_to_the_left() {
        GoldMineGame goldMine = new GoldMineGame(level1);

        goldMine.moveLeft();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(1, 2));
    }

    @Test
    void should_move_to_the_right() {
        GoldMineGame goldMine = new GoldMineGame(level1);

        goldMine.moveRight();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(3, 2));
    }

    @Test
    void should_move_up() {
        GoldMineGame goldMine = new GoldMineGame(level1);

        goldMine.moveUp();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 1));
    }

    @Test
    void should_move_down() {
        GoldMineGame goldMine = new GoldMineGame(level1);

        goldMine.moveDown();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 3));
    }

    @Test
    void should_not_go_through_walls_to_the_left() {
        GoldMineGame goldMine = new GoldMineGame(level1);
        for (int steps = 0; steps < 5; steps++) {
            goldMine.moveLeft();
        }

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(1, 2));
    }

    @Test
    void should_not_go_through_walls_to_the_right() {
        GoldMineGame goldMine = new GoldMineGame(level1);
        for (int steps = 0; steps < 5; steps++) {
            goldMine.moveRight();
        }

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(3, 2));
    }

    @Test
    void should_not_go_through_walls_up() {
        GoldMineGame goldMine = new GoldMineGame(level1);
        for (int steps = 0; steps < 5; steps++) {
            goldMine.moveUp();
        }

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 1));
    }

    @Test
    void should_not_go_through_walls_down() {
        GoldMineGame goldMine = new GoldMineGame(level1);
        for (int steps = 0; steps < 5; steps++) {
            goldMine.moveDown();
        }

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 3));
    }

    @Test
    void should_find_exit() {
        GoldMineGame goldMine = new GoldMineGame(level2);

        View actual = goldMine.getView(3, 4);

        assertThat(actual).isEqualTo(new View("Exit"));
    }
}
