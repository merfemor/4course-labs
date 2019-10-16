package functions.trigonometry.complex;

import functions.trigonometry.base.SinusImpl;

public class CosecansImpl implements Cosecans {
    @Override
    public double csc(double x) {
        SinusImpl sinus = new SinusImpl();
        return 1 / sinus.sin(x);
    }
}
