package se.thinkcode.gold.mine.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlayerTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t"})
    void should_reject_invalid_name(String name) {
        assertThatThrownBy(() ->
                new Player(name)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be empty");
    }
}
