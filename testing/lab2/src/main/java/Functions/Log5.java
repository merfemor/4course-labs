package Functions;

public class Log5 implements Ln {
    @Override
    public double ln(double x) {
        NaturalLog Ln = new NaturalLog();
        return Ln.ln(x)/Ln.ln(5);
    }
}
