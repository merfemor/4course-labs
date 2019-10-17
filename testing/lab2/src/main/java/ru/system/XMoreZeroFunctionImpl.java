package ru.system;

import ru.functions.Pow;
import ru.functions.logarithm.Logarithm;

public class XMoreZeroFunctionImpl implements XMoreZeroFunction {
    private final Logarithm log;
    private final Pow pow;

    public XMoreZeroFunctionImpl(Logarithm log, Pow pow) {
        this.log = log;
        this.pow = pow;
    }

    @Override
    public double apply(double x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be > 0");
        }
        return pow.pow(log.log2(x) - log.log5(x) - log.log3(x), 3) /
                (pow.pow(log.log10(x), 2) * log.log10(x) * log.log2(x));
    }
}
