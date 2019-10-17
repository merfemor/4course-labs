package ru.system;

/**
 * Вариант - 911
 *
 * x <= 0 : (((((cot(x) * sin(x)) / csc(x)) / cot(x)) + (sin(x) * (sec(x) / (cos(x) + sec(x))))) - ((csc(x) - csc(x)) + (sin(x) * ((cos(x) ^ 3) ^ 2))))
 * x > 0 : (((((log_2(x) - log_5(x)) - log_3(x)) ^ 3) / (log_10(x) / log_10(x))) / ((log_10(x) ^ 2) * (log_10(x) * log_2(x))))
 */
public class FunctionSystem {
    private final XLowerZeroFunction xLowerZeroFunction;
    private final XMoreZeroFunction xMoreZeroFunction;

    public FunctionSystem(XLowerZeroFunction xLowerZeroFunction, XMoreZeroFunction xMoreZeroFunction) {
        this.xLowerZeroFunction = xLowerZeroFunction;
        this.xMoreZeroFunction = xMoreZeroFunction;
    }

    public double apply(double x) {
        double result;
        if (x <= 0) {
            result = xLowerZeroFunction.apply(x);
        } else {
            result = xMoreZeroFunction.apply(x);
        }
        return round(result);
    }

    private double round(double result) {
        return result;
//        return ((double) Math.round(result * 1e10)) / 1e10;
    }
}