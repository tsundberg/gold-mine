package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.*;

public class InMemoryGoldMine implements GoldMine {
    private final GoldMineGame game;

    public InMemoryGoldMine(GoldMineGame game) {
        this.game = game;
    }

    @Override
    public Position currentPosition(Player player) {
        return game.currentPosition();
    }

    @Override
    public void moveUp(Player player) {
        game.moveUp();
    }

    @Override
    public void moveDown(Player player) {
        game.moveDown();
    }

    @Override
    public void moveRight(Player player) {
        game.moveRight();
    }

    @Override
    public void moveLeft(Player player) {
        game.moveLeft();
    }

    @Override
    public View look(Player player) {
        return game.look();
    }

    @Override
    public View lookUp(Player player) {
        return game.lookUp();
    }

    @Override
    public View lookRight(Player player) {
        return game.lookRight();
    }

    @Override
    public View lookLeft(Player player) {
        return game.lookLeft();
    }

    @Override
    public View lookDown(Player player) {
        return game.lookDown();
    }

    @Override
    public void pickUpGold(Player player) {
        game.pickUpGold();
    }

    @Override
    public void emptyGoldStash(Player player) {
        game.emptyGoldStash();
    }

    @Override
    public GoldStash currentGoldStash(Player player) {
        return game.currentGoldStash();
    }

    @Override
    public Points currentPoints(Player player) {
        return game.currentPoints();
    }
}
