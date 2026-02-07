package se.thinkcode.gold.mine.game;

import org.junit.jupiter.api.Test;
import se.thinkcode.gold.mine.model.Level;
import se.thinkcode.gold.mine.model.Player;
import se.thinkcode.gold.mine.model.Position;

import static org.assertj.core.api.Assertions.assertThat;

class GoldMineServiceTest {

    @Test
    void should_delegate_current_position() {
        Level level = new Level("""
                XXX
                XHX
                XXX
                """);
        GoldMineGame game = new GoldMineGame(level);
        Player player = new Player("mas");

        GoldMineService service = new GoldMineService();
        service.add(player, game);

        Position actual = service.currentPosition(player);

        assertThat(actual).isEqualTo(new Position(1, 1));
    }
}
