package se.thinkcode.gold.mine.model;

import java.util.Objects;

public final class GoldStash {
    private int stash;

    public GoldStash() {
        stash = 0;
    }

    public void add() {
        stash++;
    }

    public int empty() {
        int current = stash;
        stash = 0;
        return current;
    }

    public int stash() {
        return stash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GoldStash) obj;
        return this.stash == that.stash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stash);
    }

    @Override
    public String toString() {
        return "GoldStash[" +
                "stash=" + stash + ']';
    }
}
