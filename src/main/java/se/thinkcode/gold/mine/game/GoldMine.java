package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.*;

public interface GoldMine {
    Position currentPosition(Player player);

    void moveUp(Player player);

    void moveDown(Player player);

    void moveRight(Player player);

    void moveLeft(Player player);

    View look(Player player);

    View lookUp(Player player);

    View lookRight(Player player);

    View lookLeft(Player player);

    View lookDown(Player player);

    void pickUpGold(Player player);

    void emptyGoldStash(Player player);

    GoldStash currentGoldStash(Player player);

    Points currentPoints(Player player);

}
