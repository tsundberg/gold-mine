package se.thinkcode.gold.mine.game;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.GoldStash;
import se.thinkcode.gold.mine.model.Points;
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

    private final Level level3 = new Level("""
            XHXX
            XG E
            XXXX
            """);

    @Test
    void should_see_wall_to_the_left_level_0() {
        View expected = View.WALL;
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_to_the_right_level_0() {
        View expected = View.WALL;
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_up_level_0() {
        View expected = View.WALL;
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_see_wall_down_level_0() {
        View expected = View.WALL;
        GoldMineGame goldMine = new GoldMineGame(level0);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_to_the_left_level_1() {
        View expected = View.EMPTY;
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_to_the_right_level_1() {
        View expected = View.EMPTY;
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_up_level_1() {
        View expected = View.EMPTY;
        GoldMineGame goldMine = new GoldMineGame(level1);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void should_not_see_wall_down_level_1() {
        View expected = View.EMPTY;
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

        assertThat(actual).isEqualTo(View.EXIT);
    }

    @Test
    void should_see_gold() {
        GoldMineGame goldMine = new GoldMineGame(level3);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(View.GOLD);
    }

    @Test
    void should_pick_up_gold() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        GoldStash initialStash = goldMine.currentGoldStash();
        assertThat(initialStash.stash()).isEqualTo(0);
        goldMine.moveDown();

        goldMine.pickUpGold();

        GoldStash actual = goldMine.currentGoldStash();
        assertThat(actual.stash()).isEqualTo(1);
    }

    @Test
    void when_gold_has_been_picked_up_the_gold_is_gone() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        goldMine.moveDown();

        goldMine.pickUpGold();
        goldMine.moveRight();

        View actual = goldMine.lookLeft();
        assertThat(actual).isEqualTo(View.EMPTY);
    }

    @Test
    void should_not_be_able_to_pick_up_gold_if_there_is_no_gold() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        goldMine.moveDown();
        goldMine.moveRight();

        goldMine.pickUpGold();

        GoldStash actual = goldMine.currentGoldStash();
        assertThat(actual.stash()).isEqualTo(0);
    }

    @Test
    void should_be_able_to_empty_stash() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        goldMine.moveDown();

        goldMine.pickUpGold();

        GoldStash initialStash = goldMine.currentGoldStash();
        assertThat(initialStash.stash()).isEqualTo(1);


        goldMine.emptyGoldStash();


        GoldStash actual = goldMine.currentGoldStash();
        assertThat(actual.stash()).isEqualTo(1);
    }

    @Test
    void should_only_be_able_to_empty_stash_at_home() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        goldMine.moveDown();
        goldMine.pickUpGold();
        GoldStash initialStash = goldMine.currentGoldStash();
        assertThat(initialStash.stash()).isEqualTo(1);
        goldMine.moveUp();


        goldMine.emptyGoldStash();


        GoldStash actual = goldMine.currentGoldStash();
        assertThat(actual.stash()).isEqualTo(0);
    }

    @Test
    void should_get_one_point_per_gold_when_emptying_gold_at_home() {
        GoldMineGame goldMine = new GoldMineGame(level3);
        Points initialPoints = goldMine.currentPoints();
        assertThat(initialPoints.points()).isEqualTo(0);


        goldMine.moveDown();
        goldMine.pickUpGold();
        goldMine.moveUp();


        goldMine.emptyGoldStash();


        Points actual = goldMine.currentPoints();
        assertThat(actual.points()).isEqualTo(1);
    }
}
