package Functions;

public class Cosecans implements Csc{
    @Override
    public double csc(double x) {
        Sinus Sinus = new Sinus();
        return 1/Sinus.sin(x);
    }
}
