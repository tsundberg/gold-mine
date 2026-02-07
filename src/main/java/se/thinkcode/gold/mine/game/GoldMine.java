package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.GoldStash;
import se.thinkcode.gold.mine.model.Points;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public interface GoldMine {
    Position currentPosition();

    void moveUp();

    void moveDown();

    void moveRight();

    void moveLeft();

    View look();

    View lookUp();

    View lookRight();

    View lookLeft();

    View lookDown();

    void pickUpGold();

    void emptyGoldStash();

    GoldStash currentGoldStash();

    Points currentPoints();
}