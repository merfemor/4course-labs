package functions.trigonometry.complex;

import functions.trigonometry.base.SinusImpl;

public class CotangensImpl implements Cotangens {
    @Override
    public double cot(double x) {
        CosinusImpl cosinus = new CosinusImpl();
        SinusImpl sinus = new SinusImpl();
        return cosinus.cos(x)/sinus.sin(x);
    }
}
