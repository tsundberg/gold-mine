package se.thinkcode.gold.mine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldMineTest {

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

    @Test
    void should_see_wall_to_the_left_level_0() {
        View expected = new View("Wall");
        GoldMine goldMine = new GoldMine(level0);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_to_the_right_level_0() {
        View expected = new View("Wall");
        GoldMine goldMine = new GoldMine(level0);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_up_level_0() {
        View expected = new View("Wall");
        GoldMine goldMine = new GoldMine(level0);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_down_level_0() {
        View expected = new View("Wall");
        GoldMine goldMine = new GoldMine(level0);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_to_the_left_level_1() {
        View expected = new View("Empty");
        GoldMine goldMine = new GoldMine(level1);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_to_the_right_level_1() {
        View expected = new View("Empty");
        GoldMine goldMine = new GoldMine(level1);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_up_level_1() {
        View expected = new View("Empty");
        GoldMine goldMine = new GoldMine(level1);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_down_level_1() {
        View expected = new View("Empty");
        GoldMine goldMine = new GoldMine(level1);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(expected);
    }
}
