package functions.logarithm.base;

public class NaturalLog implements Logarithm {
    private static final double EPS = 1E-6;
    private static double buf = 0;

    @Override
    public double log(double x) {
        buf = ((x - 1) * (x - 1)) / ((x + 1) * (x + 1));
        double sum = 0;
        double currentValue = (x-1)/(x+1);
        int i = 1;
        while (Math.abs(currentValue)>EPS){
            sum+=currentValue;
            currentValue=getNextValue(currentValue,i);
            i++;
        }
        sum*=2;
        return sum;
    }

    private double getNextValue(double current, int step){
        return (2 * step - 1) * current * buf / (2 * step + 1);
    }
}
