package functions.logarithm.complex;

import functions.logarithm.base.Logarithm;
import functions.logarithm.base.NaturalLog;

public class Log2 implements Logarithm {
    @Override
    public double log(double x) {
        NaturalLog Ln = new NaturalLog();
        return Ln.log(x)/Ln.log(2);
    }
}
