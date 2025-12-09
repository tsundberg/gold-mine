package se.thinkcode.gold.mine.explorer;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.game.GoldMine;
import se.thinkcode.gold.mine.game.Level;
import se.thinkcode.gold.mine.model.Position;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldMineExplorerTest {

    private final Level level1 = new Level("""
            XXXXX
            X   X
            X H X
            X   E
            XXXXX
            """);

    @Test
    void should_explore_level_1() {
        Position expected = new Position(4, 3);
        GoldMine goldMine = new GoldMine(level1);

        GoldMineExplorer explorer = new GoldMineExplorer(goldMine);

        explorer.down();
        explorer.right();

        Position actual = explorer.getExti();

        assertThat(actual).isEqualTo(expected);
    }
}
