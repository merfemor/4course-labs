package FuncSystem;

import Functions.*;
import org.apache.commons.math3.util.Precision;

import static java.lang.Math.*;

public class Function {
    public double func(double x) {
        double y = 0;
        if (x <= 0) {
            while (Math.abs(x) > Math.PI * 2) {
                if (x > 0) x -= Math.PI * 2;
                if (x < 0) x += Math.PI * 2;
            }
            double a = sin(x)*sin(x);
            double b = sin(x)/(Precision.round(cos(x),10)*(Precision.round(cos(x),10)+1/Precision.round(cos(x),10)));
            double c = -1 * sin(x) * pow(cos(x*x),3);
            //y = sin(x) / (1 / sin(x)) + sin(x) / cos(x) / (cos(x) + 1 / cos(x)) - sin(x) * pow(cos(x * x), 3);
            y=a+b+c;
        } else if (x > 0) {
            y = pow(((log(x) / log(2) - log(x) / log(5)) - log(x) / log(3)), 3) / (pow(log10(x), 2) * log10(x) * log(x) / log(2));
        }
        return y;
    }

    public double customFunc(double x) {
        double y = 0;
            if (x<0){
                while (Math.abs(x) > Math.PI * 2) {
                if (x > 0) x -= Math.PI * 2;
                if (x < 0) x += Math.PI * 2;
                }
            }
            Sinus sinus = new Sinus();
            Cosinus cosinus = new Cosinus();
            Cosecans cosecans = new Cosecans();
            Secans secans = new Secans();
            //Cotangens cotangens = new Cotangens();
            Log2 log2 = new Log2();
            Log3 log3 = new Log3();
            Log5 log5 = new Log5();
            Log10 log10 = new Log10();
            if (x <= 0) {
                y = sinus.sin(x) / cosecans.csc(x) + sinus.sin(x) * secans.scs(x) / (cosinus.cos(x) + secans.scs(x)) - sinus.sin(x) * pow(cosinus.cos(x * x), 3);
            } else if (x > 0) {
                y = pow(log2.ln(x) - log5.ln(x) - log3.ln(x), 3) / (pow(log10.ln(x), 2) * log10.ln(x) * log2.ln(x));
            }
            return y;
        }
}