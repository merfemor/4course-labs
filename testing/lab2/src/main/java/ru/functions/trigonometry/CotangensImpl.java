package ru.functions.trigonometry;

public class CotangensImpl implements Cotangens {
    private final Cosinus cosinus;
    private final Sinus sinus;

    public CotangensImpl(Cosinus cosinus, Sinus sinus) {
        this.cosinus = cosinus;
        this.sinus = sinus;
    }

    @Override
    public double cot(double x) {
        return cosinus.cos(x)/sinus.sin(x);
    }
}
