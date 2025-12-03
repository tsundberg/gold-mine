package se.thinkcode.gold.mine;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GoldMineTest {
    private final GoldMine goldMine = new GoldMine();

    @Test
    void should_create_a_gold_mine() {
        assertThat(goldMine).isNotNull();
    }
}
