package se.thinkcode.gold.mine.model;

public record View(ViewType type) {
    public static final View WALL = new View(ViewType.WALL);
    public static final View GOLD = new View(ViewType.GOLD);
    public static final View EXIT = new View(ViewType.EXIT);
    public static final View HOME = new View(ViewType.HOME);
    public static final View EMPTY = new View(ViewType.EMPTY);
}
