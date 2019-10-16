package functions.trigonometry.complex;

import functions.trigonometry.base.SinusImpl;

public class CosinusImpl implements Cosinus {
    @Override
    public double cos(double x) {
        SinusImpl sinus = new SinusImpl();
        return sinus.sin(x+Math.PI/2);
    }
}
