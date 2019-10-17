package ru.system;

import java.util.Objects;

public final class Pair {
    final double x;
    final double y;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Pair pair(double x, double y) {
        return new Pair(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Double.compare(pair.x, x) == 0 &&
                Double.compare(pair.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
