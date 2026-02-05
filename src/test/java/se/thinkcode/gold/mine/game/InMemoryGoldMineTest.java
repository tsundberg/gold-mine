package se.thinkcode.gold.mine.game;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

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

    @Test
    void should_delegate_current_position() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(1, 1));
    }

    @Test
    void should_delegate_look() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        View actual = goldMine.look();

        assertThat(actual).isEqualTo(View.HOME);
    }

    @Test
    void should_delegate_look_up() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        View actual = goldMine.lookUp();

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_down() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        View actual = goldMine.lookDown();

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_left() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        View actual = goldMine.lookLeft();

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_look_right() {
        GoldMineGame game = new GoldMineGame(level0);
        GoldMine goldMine = new InMemoryGoldMine(game);

        View actual = goldMine.lookRight();

        assertThat(actual).isEqualTo(View.WALL);
    }

    @Test
    void should_delegate_move_up() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = new InMemoryGoldMine(game);

        goldMine.moveUp();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 1));
    }

    @Test
    void should_delegate_move_down() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = new InMemoryGoldMine(game);

        goldMine.moveDown();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(2, 3));
    }

    @Test
    void should_delegate_move_left() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = new InMemoryGoldMine(game);

        goldMine.moveLeft();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(1, 2));
    }

    @Test
    void should_delegate_move_right() {
        GoldMineGame game = new GoldMineGame(level1);
        GoldMine goldMine = new InMemoryGoldMine(game);

        goldMine.moveRight();
        Position actual = goldMine.currentPosition();

        assertThat(actual).isEqualTo(new Position(3, 2));
    }
}
