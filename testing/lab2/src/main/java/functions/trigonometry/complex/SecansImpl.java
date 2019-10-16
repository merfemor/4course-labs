package functions.trigonometry.complex;

public class SecansImpl implements Secans {
    @Override
    public double scs(double x) {
        CosinusImpl cosinus = new CosinusImpl();
        return 1/cosinus.cos(x);
    }
}