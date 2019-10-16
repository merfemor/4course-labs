package functions.trigonometry;

public class SecansImpl implements Secans {
    private final Cosinus cosinus;

    public SecansImpl(Cosinus cosinus) {
        this.cosinus = cosinus;
    }

    @Override
    public double scs(double x) {
        return 1 / cosinus.cos(x);
    }
}