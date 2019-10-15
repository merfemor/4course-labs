package Functions;

public class Log10 implements Ln {
    @Override
    public double ln(double x) {
        NaturalLog Ln = new NaturalLog();
        return Ln.ln(x)/Ln.ln(10);
    }
}
