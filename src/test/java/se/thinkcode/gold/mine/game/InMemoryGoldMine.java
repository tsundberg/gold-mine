package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.*;

public class InMemoryGoldMine implements GoldMine {
    private final GoldMine goldMine;

    public InMemoryGoldMine(GoldMine goldMine) {
        this.goldMine = goldMine;
    }

    @Override
    public Position currentPosition(Player player) {
        return goldMine.currentPosition(player);
    }

    @Override
    public void moveUp(Player player) {
        goldMine.moveUp(player);
    }

    @Override
    public void moveDown(Player player) {
        goldMine.moveDown(player);
    }

    @Override
    public void moveRight(Player player) {
        goldMine.moveRight(player);
    }

    @Override
    public void moveLeft(Player player) {
        goldMine.moveLeft(player);
    }

    @Override
    public View look(Player player) {
        return goldMine.look(player);
    }

    @Override
    public View lookUp(Player player) {
        return goldMine.lookUp(player);
    }

    @Override
    public View lookRight(Player player) {
        return goldMine.lookRight(player);
    }

    @Override
    public View lookLeft(Player player) {
        return goldMine.lookLeft(player);
    }

    @Override
    public View lookDown(Player player) {
        return goldMine.lookDown(player);
    }

    @Override
    public void pickUpGold(Player player) {
        goldMine.pickUpGold(player);
    }

    @Override
    public void emptyGoldStash(Player player) {
        goldMine.emptyGoldStash(player);
    }

    @Override
    public GoldStash currentGoldStash(Player player) {
        return goldMine.currentGoldStash(player);
    }

    @Override
    public Points currentPoints(Player player) {
        return goldMine.currentPoints(player);
    }
}
