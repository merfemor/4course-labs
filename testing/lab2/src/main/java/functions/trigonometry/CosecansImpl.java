package functions.trigonometry;

public class CosecansImpl implements Cosecans {
    private final Sinus sinus;

    public CosecansImpl(Sinus sinus) {
        this.sinus = sinus;
    }

    @Override
    public double csc(double x) {
        return 1 / sinus.sin(x);
    }
}
