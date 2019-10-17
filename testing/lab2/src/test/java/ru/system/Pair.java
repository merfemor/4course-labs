package ru.system;

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
}
