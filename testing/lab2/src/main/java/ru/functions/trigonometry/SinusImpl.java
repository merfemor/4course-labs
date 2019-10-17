package ru.functions.trigonometry;

public class SinusImpl implements Sinus {
    private static final double EPS = 1E-29;

    @Override
    public double sin(double x) {
        double res = x;
        double prevRes;
        int i = 1;
        double sum = x;
        do {
            prevRes = res;
            i++;
            sum = +sum * x * x / i;
            i++;
            sum = sum / i;
            sum = -sum;
            res = prevRes + sum;
        } while (Math.abs(res - prevRes) > EPS);

        return res;
    }
}