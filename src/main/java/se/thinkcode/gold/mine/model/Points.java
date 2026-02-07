package se.thinkcode.gold.mine.model;

import java.util.Objects;

public class Points {
    private int points;

    public Points() {
        points = 0;
    }

    public int points() {
        return points;
    }

    public void add(int points) {
        this.points += points;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Points points1 = (Points) o;
        return points == points1.points;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(points);
    }

    @Override
    public String toString() {
        return "Points{" +
                "points=" + points +
                '}';
    }
}
