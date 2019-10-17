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

    // TODO: delete it?
//    public double strangeImpl(double x) {
//        double y = 0;
//        if (x <= 0) {
//            while (Math.abs(x) > Math.PI * 2) {
//                if (x > 0) x -= Math.PI * 2;
//                if (x < 0) x += Math.PI * 2;
//            }
//            double a = sin(x) * sin(x);
//            double b = sin(x) / (Precision.round(cos(x), 10) * (Precision.round(cos(x), 10) + 1 / Precision.round(cos(x), 10)));
//            double c = -1 * sin(x) * pow(cos(x * x), 3);
//            //y = sin(x) / (1 / sin(x)) + sin(x) / cos(x) / (cos(x) + 1 / cos(x)) - sin(x) * pow(cos(x * x), 3);
//            y = a + b + c;
//        } else if (x > 0) {
//            y = pow(((log(x) / log(2) - log(x) / log(5)) - log(x) / log(3)), 3) / (pow(log10(x), 2) * log10(x) * log(x) / log(2));
//        }
//        return y;
//    }

    public double apply(double x) {
        if (x <= 0) {
            return xLowerZeroFunction.apply(x);
        } else {
            return xMoreZeroFunction.apply(x);
        }
    }
}