package system;

import functions.Pow;
import functions.trigonometry.Cosecans;
import functions.trigonometry.Cosinus;
import functions.trigonometry.Secans;
import functions.trigonometry.Sinus;

public class XLowerZeroFunctionImpl implements XLowerZeroFunction {
    private final Sinus sinus;
    private final Cosinus cosinus;
    private final Secans secans;
    private final Cosecans cosecans;
    private final Pow pow;

    public XLowerZeroFunctionImpl(Sinus sinus, Cosinus cosinus, Secans secans, Cosecans cosecans, Pow pow) {
        this.sinus = sinus;
        this.cosinus = cosinus;
        this.secans = secans;
        this.cosecans = cosecans;
        this.pow = pow;
    }

    @Override
    public double apply(double x) {
        if (x > 0) {
            throw new IllegalArgumentException("x must be <= 0");
        }
        if (x < 0) {
            while (Math.abs(x) > Math.PI * 2) {
                x += Math.PI * 2;
            }
        }
        return sinus.sin(x) / cosecans.csc(x) + sinus.sin(x) * secans.scs(x) / (cosinus.cos(x) + secans.scs(x)) - sinus.sin(x) * pow.pow(cosinus.cos(x * x), 3);
    }
}
