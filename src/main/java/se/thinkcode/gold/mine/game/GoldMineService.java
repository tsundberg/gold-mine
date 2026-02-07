package se.thinkcode.gold.mine.game;

import se.thinkcode.gold.mine.model.*;

import java.util.HashMap;
import java.util.Map;

public class GoldMineService implements GoldMine {
    private final Map<Player, GoldMineGame> games = new HashMap<>();

    public void add(Player player, GoldMineGame game) {
        games.put(player, game);
    }

    @Override
    public Position currentPosition(Player player) {
        GoldMineGame game = games.get(player);
        return game.currentPosition();
    }

    @Override
    public void moveUp(Player player) {
        GoldMineGame game = games.get(player);
        game.moveUp();
    }

    @Override
    public void moveDown(Player player) {
        GoldMineGame game = games.get(player);
        game.moveDown();
    }

    @Override
    public void moveRight(Player player) {
        GoldMineGame game = games.get(player);
        game.moveRight();
    }

    @Override
    public void moveLeft(Player player) {
        GoldMineGame game = games.get(player);
        game.moveLeft();
    }

    @Override
    public View look(Player player) {
        GoldMineGame game = games.get(player);
        return game.look();
    }

    @Override
    public View lookUp(Player player) {
        GoldMineGame game = games.get(player);
        return game.lookUp();
    }

    @Override
    public View lookRight(Player player) {
        GoldMineGame game = games.get(player);
        return game.lookRight();
    }

    @Override
    public View lookLeft(Player player) {
        GoldMineGame game = games.get(player);
        return game.lookLeft();
    }

    @Override
    public View lookDown(Player player) {
        GoldMineGame game = games.get(player);
        return game.lookDown();
    }

    @Override
    public void pickUpGold(Player player) {
        GoldMineGame game = games.get(player);
        game.pickUpGold();
    }

    @Override
    public void emptyGoldStash(Player player) {
        GoldMineGame game = games.get(player);
        game.emptyGoldStash();
    }

    @Override
    public GoldStash currentGoldStash(Player player) {
        GoldMineGame game = games.get(player);
        return game.currentGoldStash();
    }

    @Override
    public Points currentPoints(Player player) {
        GoldMineGame game = games.get(player);
        return game.currentPoints();
    }

}
