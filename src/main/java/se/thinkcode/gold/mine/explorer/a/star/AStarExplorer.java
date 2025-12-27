package se.thinkcode.gold.mine.explorer.a.star;

import se.thinkcode.gold.mine.explorer.Explorer;
import se.thinkcode.gold.mine.explorer.GoldMineExplorer;

public class AStarExplorer implements Explorer {
    private final GoldMineExplorer goldMineExplorer;

    public AStarExplorer(GoldMineExplorer goldMineExplorer) {
        this.goldMineExplorer = goldMineExplorer;
    }

    @Override
    public void explore() {
        goldMineExplorer.look();


        System.out.println();
    }
}
