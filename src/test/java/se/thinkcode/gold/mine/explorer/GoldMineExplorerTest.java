package se.thinkcode.gold.mine.explorer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.thinkcode.gold.mine.explorer.naive.NaiveExplorer;
import se.thinkcode.gold.mine.game.GoldMine;
import se.thinkcode.gold.mine.game.Level;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldMineExplorerTest {

    private final static Level level1 = new Level("""
            XXXXX
            X   X
            X H X
            X   E
            XXXXX
            """);

    private final static Level level2 = new Level("""
            XXXXXX
            X    X
            X    X
            X H  X
            X    E
            XXXXXX
            """);

    private final static Level level3 = new Level("""
            XXXXXXX
            X     X
            X     X
            X     X
            X H   X
            X     E
            XXXXXXX
            """);

    private final static Level level4 = new Level("""
            XXXXXXXX
            X      X
            X      X
            X      X
            X H    X
            X      E
            XXXXXXXX
            """);

    private final static Level level5 = new Level("""
            XXXXXXXXX
            X       X
            X   H   X
            X       X
            X       X
            X       X
            XXXXXEXXX
            """);

    private final static Level level6 = new Level("""
            XXXXXXXXXXXXXX
            X            X
            X       H    X
            X            X
            X            X
            X            X
            X            X
            X            X
            X            X
            XXXXXXXEXXXXXX
            """);

    private final static Level level7 = new Level("""
            XXXXHXXXXXXXXX
            X            X
            X            X
            X            X
            X            X
            X            X
            X            X
            X            X
            X            X
            XXXXXXXEXXXXXX
            """);

    @Test
    void should_got_to_exit_level_one() {
        GoldMine goldMine = new GoldMine(level1);

        GoldMineExplorer explorer = new GoldMineExplorer(goldMine);

        Position actualDown = explorer.down();
        Position actuaRight = explorer.right();
        View actualExit = explorer.lookRight();

        assertThat(actualDown).isEqualTo(new Position(2, 3));
        assertThat(actuaRight).isEqualTo(new Position(3, 3));
        assertThat(actualExit).isEqualTo(new View("Exit"));
    }

    @Test
    void should_got_to_exit_level_the_a_long_way() {
        GoldMine goldMine = new GoldMine(level1);

        GoldMineExplorer explorer = new GoldMineExplorer(goldMine);


        Position actualPosition = explorer.up();
        Position expected = new Position(2, 1);
        assertThat(actualPosition).isEqualTo(expected);

        View actualView = explorer.lookUp();
        View expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Home");
        assertThat(actualView).isEqualTo(expectedView);


        actualPosition = explorer.left();
        expected = new Position(1, 1);
        assertThat(actualPosition).isEqualTo(expected);

        actualView = explorer.lookUp();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);


        actualPosition = explorer.down();
        expected = new Position(1, 2);
        assertThat(actualPosition).isEqualTo(expected);

        actualView = explorer.lookUp();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Home");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);


        actualPosition = explorer.down();
        expected = new Position(1, 3);
        assertThat(actualPosition).isEqualTo(expected);

        actualView = explorer.lookUp();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);


        actualPosition = explorer.right();
        expected = new Position(2, 3);
        assertThat(actualPosition).isEqualTo(expected);

        actualView = explorer.lookUp();
        expectedView = new View("Home");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);


        actualPosition = explorer.right();
        expected = new Position(3, 3);
        assertThat(actualPosition).isEqualTo(expected);

        actualView = explorer.lookUp();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookRight();
        expectedView = new View("Exit");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookLeft();
        expectedView = new View("Empty");
        assertThat(actualView).isEqualTo(expectedView);

        actualView = explorer.lookDown();
        expectedView = new View("Wall");
        assertThat(actualView).isEqualTo(expectedView);
    }

    @Test
    void an_unused_map_should_be_empty() {
        GoldMine goldMine = new GoldMine(level1);
        GoldMineExplorer explorer = new GoldMineExplorer(goldMine);

        String actual = explorer.getMap();

        assertThat(actual).isEmpty();
    }

    @Test
    void should_render_map() {
        GoldMine goldMine = new GoldMine(level1);
        GoldMineExplorer explorer = new GoldMineExplorer(goldMine);
        String expected = level1.level();
        explorer.up();
        explorer.lookAround();
        explorer.left();
        explorer.lookAround();
        explorer.down();
        explorer.lookAround();
        explorer.down();
        explorer.lookAround();
        explorer.right();
        explorer.lookAround();
        explorer.right();
        explorer.lookAround();
        explorer.up();
        explorer.lookAround();
        explorer.up();
        explorer.lookAround();


        String actual = explorer.getMap();


        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("levels")
    void should_explore_all_levels_with_naive_explorer(Level level) {
        GoldMine goldMine = new GoldMine(level);
        GoldMineExplorer goldMineExplorer = new GoldMineExplorer(goldMine);
        String expected = level.level();
        Explorer naiveExplorer = new NaiveExplorer(goldMine, goldMineExplorer);


        goldMineExplorer.explore(naiveExplorer);
        String actual = goldMineExplorer.getMap();


        assertThat(actual).isEqualTo(expected);
    }


    static List<Level> levels() {
        return List.of(
                level1,
                level2,
                level3,
                level4,
                level5,
                level6
        );
    }

    @Test
    void should_clear_screen_on_a_vt_100_terminal() {
        GoldMine goldMine = new GoldMine(level1);

        String actual = GoldMineExplorer.clearScreen();

        assertThat(actual).isEqualTo("[2J");
    }

    @Test
    void should_send_cursor_to_home_on_a_vt_100_terminal() {
        GoldMine goldMine = new GoldMine(level1);

        String actual = GoldMineExplorer.cursorHome();

        assertThat(actual).isEqualTo("[H");
    }

    @Test
    void should_send_cursor_to_x_and_y_coordinates_on_a_vt_100_terminal() {
        GoldMine goldMine = new GoldMine(level1);

        String actual = GoldMineExplorer.cursorTo(1, 2);

        assertThat(actual).isEqualTo("[1;2H");
    }
}
