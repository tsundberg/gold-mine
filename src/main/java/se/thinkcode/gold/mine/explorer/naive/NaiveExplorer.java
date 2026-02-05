package se.thinkcode.gold.mine.explorer.naive;

import se.thinkcode.gold.mine.explorer.Explorer;
import se.thinkcode.gold.mine.explorer.GoldMineExplorer;
import se.thinkcode.gold.mine.model.Position;
import se.thinkcode.gold.mine.model.View;

public class NaiveExplorer implements Explorer {
    private final GoldMineExplorer goldMineExplorer;
    private Position maxDimension = new Position(0, 0);

    public NaiveExplorer(GoldMineExplorer goldMineExplorer) {
        this.goldMineExplorer = goldMineExplorer;
    }

    @Override
    public void explore() {
        exploreEdges();
        exploreUnknownCells();
    }

    private void exploreEdges() {
        View exit = new View("Exit");
        Position position = goldMineExplorer.currentPosition();

        if (position.y() == 0) {
            position = exploreFromTopRow(position, exit);
        }
        if (position.x() == 0) {
            exploreFromLeftColumn(position, exit);
        } else {
            exploreFromRightColumn(position, exit);
        }
    }

    private Position exploreFromTopRow(Position position, View exit) {
        position = exploreDown(position, exit);
        position = exploreLeft(position, exit);
        position = exploreUp(position, exit);
        position = exploreRight(position, exit);
        position = exploreDown(position, exit);
        return exploreLeft(position, exit);
    }

    private void exploreFromLeftColumn(Position position, View exit) {
        position = exploreRight(position, exit);
        position = exploreDown(position, exit);
        position = exploreLeft(position, exit);
        position = exploreUp(position, exit);
        position = exploreRight(position, exit);
        position = exploreDown(position, exit);
        exploreLeft(position, exit);
    }

    private void exploreFromRightColumn(Position position, View exit) {
        position = exploreUp(position, exit);
        position = exploreLeft(position, exit);
        position = exploreDown(position, exit);
        position = exploreRight(position, exit);
        position = exploreUp(position, exit);
        exploreLeft(position, exit);
    }

    private void exploreUnknownCells() {
        Position unknownPosition = unknown();
        while (unknownPosition != null) {
            visit(unknownPosition);
            goldMineExplorer.lookAround();
            unknownPosition = unknown();
        }
    }

    private void visit(Position target) {
        Position current = goldMineExplorer.currentPosition();
        while (!target.equals(current)) {
            moveTowards(current, target);
            current = goldMineExplorer.currentPosition();
        }
    }

    private void moveTowards(Position from, Position to) {
        if (from.y() - to.y() > 0) {
            goldMineExplorer.up();
        }
        if (from.y() - to.y() < 0) {
            goldMineExplorer.down();
        }
        if (from.x() - to.x() > 0) {
            goldMineExplorer.left();
        }
        if (from.x() - to.x() < 0) {
            goldMineExplorer.right();
        }
    }

    private Position unknown() {
        for (int y = 0; y < maxDimension.y() - 1; y++) {
            for (int x = 0; x < maxDimension.x() - 1; x++) {
                Position position = new Position(x, y);
                View view = goldMineExplorer.getView(position);
                if (view == null) {
                    return position;
                }
            }
        }

        return null;
    }

    private Position exploreLeft(Position position, View exit) {
        Position newPosition = goldMineExplorer.left();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View left = goldMineExplorer.lookLeft();
            if (!left.equals(exit)) {
                newPosition = goldMineExplorer.left();
            }

            goldMineExplorer.lookAround();
        }

        updateMaxDimension(position);
        return position;
    }

    private Position exploreDown(Position position, View exit) {
        Position newPosition = goldMineExplorer.down();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View down = goldMineExplorer.lookDown();
            if (!down.equals(exit)) {
                newPosition = goldMineExplorer.down();
            }

            goldMineExplorer.lookAround();
        }

        updateMaxDimension(position);
        return position;
    }

    private Position exploreRight(Position position, View exit) {
        Position newPosition = goldMineExplorer.right();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View right = goldMineExplorer.lookRight();
            if (!right.equals(exit)) {
                newPosition = goldMineExplorer.right();
            }

            goldMineExplorer.lookAround();
        }

        updateMaxDimension(position);
        return position;
    }

    private Position exploreUp(Position position, View exit) {
        Position newPosition = goldMineExplorer.up();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View up = goldMineExplorer.lookUp();
            if (!up.equals(exit)) {
                newPosition = goldMineExplorer.up();
            }
            goldMineExplorer.lookAround();
        }

        updateMaxDimension(position);
        return position;
    }

    private void updateMaxDimension(Position position) {
        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));
    }
}
