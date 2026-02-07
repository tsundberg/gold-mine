package se.thinkcode.gold.mine.game;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.*;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryGoldMineTest {

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

    private final Player player = new Player();

    @Test
    void should_delegate_current_position() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        Position actual = goldMine.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(1, 1));
    }

    @Test
    void should_delegate_look() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        View actual = goldMine.look(player);

        assertThat(actual).isEqualTo(View.HOME);
    }

    @Test
    void should_delegate_look_up() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        View actual = goldMine.lookUp(player);

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_down() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        View actual = goldMine.lookDown(player);

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_left() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        View actual = goldMine.lookLeft(player);

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_right() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = createGoldMine(game);

        View actual = goldMine.lookRight(player);

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_move_up() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = createGoldMine(game);

        goldMine.moveUp(player);
        Position actual = goldMine.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(2, 1));
    }

    @Test
    void should_delegate_move_down() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = createGoldMine(game);

        goldMine.moveDown(player);
        Position actual = goldMine.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(2, 3));
    }

    @Test
    void should_delegate_move_left() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = createGoldMine(game);

        goldMine.moveLeft(player);
        Position actual = goldMine.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(1, 2));
    }

    @Test
    void should_delegate_move_right() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = createGoldMine(game);

        goldMine.moveRight(player);
        Position actual = goldMine.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(3, 2));
    }

    @Test
    void should_pick_up_gold_and_empty_stash_at_home_and_get_one_point() {
        Level level = new Level("""
                XHXX
                XG E
                XXXX
                """);
        GoldMineGame game = new GoldMineGame(level);
        GoldMine goldMine = createGoldMine(game);
        goldMine.moveDown(player);

        goldMine.pickUpGold(player);

        goldMine.moveUp(player);

        GoldStash actualGoldStash = goldMine.currentGoldStash(player);
        assertThat(actualGoldStash.stash()).isEqualTo(1);

        Points actualPoints = goldMine.currentPoints(player);
        assertThat(actualPoints.points()).isEqualTo(0);

        goldMine.emptyGoldStash(player);

        actualGoldStash = goldMine.currentGoldStash(player);
        assertThat(actualGoldStash.stash()).isEqualTo(0);

        actualPoints = goldMine.currentPoints(player);
        assertThat(actualPoints.points()).isEqualTo(1);
    }

    private GoldMine createGoldMine(GoldMineGame game) {
        GoldMineService service = new GoldMineService();
        service.add(player, game);
        return new InMemoryGoldMine(service);
    }
}
