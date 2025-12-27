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
        View exit = new View("Exit");
        Position position = goldMineExplorer.currentPosition();

        position = exploreUp(position, exit);
        position = exploreLeft(position, exit);
        position = exploreDown(position, exit);
        position = exploreRight(position, exit);
        position = exploreUp(position, exit);
        exploreLeft(position, exit);

        Position unknownPosition = unknown();
        while (unknownPosition != null) {
            visit(unknownPosition);
            goldMineExplorer.lookAround();
            unknownPosition = unknown();
        }
    }

    private void visit(Position position) {
        Position currentPosition = goldMineExplorer.currentPosition();

        while (!position.equals(currentPosition)) {
            if (currentPosition.y() - position.y() > 0) {
                goldMineExplorer.up();
            }
            if (currentPosition.y() - position.y() < 0) {
                goldMineExplorer.down();
            }

            if (currentPosition.x() - position.x() > 0) {
                goldMineExplorer.left();
            }
            if (currentPosition.x() - position.x() < 0) {
                goldMineExplorer.right();
            }

            currentPosition = goldMineExplorer.currentPosition();
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
        Position newPosition;
        newPosition = goldMineExplorer.left();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View left = goldMineExplorer.lookLeft();
            if (!left.equals(exit)) {
                newPosition = goldMineExplorer.left();
            }

            goldMineExplorer.lookAround();
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreDown(Position position, View exit) {
        Position newPosition;
        newPosition = goldMineExplorer.down();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View down = goldMineExplorer.lookDown();
            if (!down.equals(exit)) {
                newPosition = goldMineExplorer.down();
            }

            goldMineExplorer.lookAround();
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreRight(Position position, View exit) {
        Position newPosition;
        newPosition = goldMineExplorer.right();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View right = goldMineExplorer.lookRight();
            if (!right.equals(exit)) {
                newPosition = goldMineExplorer.right();
            }

            goldMineExplorer.lookAround();
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }

    private Position exploreUp(Position position, View exit) {
        Position newPosition;
        newPosition = goldMineExplorer.up();
        goldMineExplorer.lookAround();
        while (!position.equals(newPosition)) {
            position = newPosition;
            View up = goldMineExplorer.lookUp();
            if (!up.equals(exit)) {
                newPosition = goldMineExplorer.up();
            }
            goldMineExplorer.lookAround();
        }

        maxDimension = new Position(Math.max(maxDimension.x(), position.x()),
                Math.max(maxDimension.y(), position.y()));

        return position;
    }
}
