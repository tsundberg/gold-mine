package se.thinkcode.gold.mine.model;

public record Player(String name) {
    public Player {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
    }
}
