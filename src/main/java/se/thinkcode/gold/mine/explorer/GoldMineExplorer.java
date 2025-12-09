package se.thinkcode.gold.mine.explorer;

import se.thinkcode.gold.mine.game.GoldMine;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public class GoldMineExplorer {
    private final GoldMine goldMine;
    private Position exit = null;

    public GoldMineExplorer(GoldMine goldMine) {
        this.goldMine = goldMine;
    }

    public Position getExti() {
        return exit;
    }

    public void down() {
        goldMine.moveDown();
    }

    public void right() {
        goldMine.moveRight();

        View view = goldMine.lookRight();
        if (view.equals(new View("Exit"))) {
            Position current = goldMine.currentPosition();
            exit = new Position(current.x() + 1, current.y());
        }
    }
}
