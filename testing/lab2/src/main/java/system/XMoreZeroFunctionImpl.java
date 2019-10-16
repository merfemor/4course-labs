package system;

import functions.logarithm.Logarithm;

public class XMoreZeroFunctionImpl implements XMoreZeroFunction {
    private final Logarithm log;

    public XMoreZeroFunctionImpl(Logarithm log) {
        this.log = log;
    }

    private static double pow(double n, int p) {
        double res = n;
        for (int i = 0; i < p; i++) {
            res *= n;
        }
        return res;
    }

    @Override
    public double apply(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be > 0");
        }
        return pow(log.log2(x) - log.log5(x) - log.log3(x), 3) /
                (pow(log.log10(x), 2) * log.log10(x) * log.log2(x));
    }
}
