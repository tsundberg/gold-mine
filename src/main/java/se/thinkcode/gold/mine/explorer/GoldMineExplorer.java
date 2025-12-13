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

    public Position up() {
        goldMine.moveUp();
        return goldMine.currentPosition();
    }

    public Position down() {
        goldMine.moveDown();
        return goldMine.currentPosition();
    }

    public Position right() {
        goldMine.moveRight();
        return goldMine.currentPosition();
    }

    public Position left() {
        goldMine.moveLeft();
        return goldMine.currentPosition();
    }

    public View lookUp() {
        return goldMine.lookUp();
    }

    public View lookRight() {
        return goldMine.lookRight();
    }

    public View lookLeft() {
        return goldMine.lookLeft();
    }

    public View lookDown() {
        return goldMine.lookDown();
    }

    static String clearScreen() {
        // https://espterm.github.io/docs/VT100%20escape%20codes.html
        // https://stackoverflow.com/questions/48773272/write-print-to-the-bottom-of-terminal

        return "\033[2J";
    }

    static String cursorHome() {
        return "\033[H";
    }

    static String cursorTo(int row, int column) {
        return String.format("\033[%d;%dH", row, column);
    }
}
