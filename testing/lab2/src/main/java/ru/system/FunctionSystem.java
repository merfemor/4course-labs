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
        if (x <= 0) {
            return xLowerZeroFunction.apply(x);
        } else {
            return xMoreZeroFunction.apply(x);
        }
    }
}