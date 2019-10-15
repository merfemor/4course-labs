package Functions;

public class Secans implements Scs {
    @Override
    public double scs(double x) {
        Cosinus cosinus = new Cosinus();
        return 1/cosinus.cos(x);
    }
}