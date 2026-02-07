package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.GoldStash;
import se.thinkcode.gold.mine.model.Points;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public class InMemoryGoldMine implements GoldMine {
    private final GoldMineGame game;

    public InMemoryGoldMine(GoldMineGame game) {
        this.game = game;
    }

    @Override
    public Position currentPosition() {
        return game.currentPosition();
    }

    @Override
    public void moveUp() {
        game.moveUp();
    }

    @Override
    public void moveDown() {
        game.moveDown();
    }

    @Override
    public void moveRight() {
        game.moveRight();
    }

    @Override
    public void moveLeft() {
        game.moveLeft();
    }

    @Override
    public View look() {
        return game.look();
    }

    @Override
    public View lookUp() {
        return game.lookUp();
    }

    @Override
    public View lookRight() {
        return game.lookRight();
    }

    @Override
    public View lookLeft() {
        return game.lookLeft();
    }

    @Override
    public View lookDown() {
        return game.lookDown();
    }

    @Override
    public void pickUpGold() {
        game.pickUpGold();
    }

    @Override
    public void emptyGoldStash() {
        game.emptyGoldStash();
    }

    @Override
    public GoldStash currentGoldStash() {
        return game.currentGoldStash();
    }

    @Override
    public Points currentPoints() {
        return game.currentPoints();
    }
}
