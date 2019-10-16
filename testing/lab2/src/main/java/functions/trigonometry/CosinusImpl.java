package functions.trigonometry;

public class CosinusImpl implements Cosinus {
    private final Sinus sinus;

    public CosinusImpl(Sinus sinus) {
        this.sinus = sinus;
    }

    @Override
    public double cos(double x) {
        return sinus.sin(x+Math.PI/2);
    }
}
