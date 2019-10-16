package system;

import functions.logarithm.complex.Log10;
import functions.logarithm.complex.Log2;
import functions.logarithm.complex.Log3;
import functions.logarithm.complex.Log5;
import functions.trigonometry.base.SinusImpl;
import functions.trigonometry.complex.CosecansImpl;
import functions.trigonometry.complex.CosinusImpl;
import functions.trigonometry.complex.SecansImpl;
import org.apache.commons.math3.util.Precision;

import static java.lang.Math.*;

public class FunctionSystem {
    public double func(double x) {
        double y = 0;
        if (x <= 0) {
            while (Math.abs(x) > Math.PI * 2) {
                if (x > 0) x -= Math.PI * 2;
                if (x < 0) x += Math.PI * 2;
            }
            double a = sin(x) * sin(x);
            double b = sin(x) / (Precision.round(cos(x), 10) * (Precision.round(cos(x), 10) + 1 / Precision.round(cos(x), 10)));
            double c = -1 * sin(x) * pow(cos(x * x), 3);
            //y = sin(x) / (1 / sin(x)) + sin(x) / cos(x) / (cos(x) + 1 / cos(x)) - sin(x) * pow(cos(x * x), 3);
            y = a + b + c;
        } else if (x > 0) {
            y = pow(((log(x) / log(2) - log(x) / log(5)) - log(x) / log(3)), 3) / (pow(log10(x), 2) * log10(x) * log(x) / log(2));
        }
        return y;
    }

    public double customFunc(double x) {
        double y = 0;
        if (x < 0) {
            while (Math.abs(x) > Math.PI * 2) {
                if (x > 0) x -= Math.PI * 2;
                if (x < 0) x += Math.PI * 2;
            }
        }
        SinusImpl sinus = new SinusImpl();
        CosinusImpl cosinus = new CosinusImpl();
        CosecansImpl cosecans = new CosecansImpl();
        SecansImpl secans = new SecansImpl();
        //CotangensImpl cotangens = new CotangensImpl();
        Log2 log2 = new Log2();
        Log3 log3 = new Log3();
        Log5 log5 = new Log5();
        Log10 log10 = new Log10();
        if (x <= 0) {
            y = sinus.sin(x) / cosecans.csc(x) + sinus.sin(x) * secans.scs(x) / (cosinus.cos(x) + secans.scs(x)) - sinus.sin(x) * pow(cosinus.cos(x * x), 3);
        } else if (x > 0) {
            y = pow(log2.log(x) - log5.log(x) - log3.log(x), 3) / (pow(log10.log(x), 2) * log10.log(x) * log2.log(x));
        }
        return y;
    }
}