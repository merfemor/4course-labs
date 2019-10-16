package functions;

public class PowImpl implements Pow {
    @Override
    public double pow(final double num, final int p) {
        double res = num;
        for (int i = 1; i < p; i++) {
            res *= num;
        }
        return res;
    }
}
