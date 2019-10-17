package ru.system;

import org.mockito.Mockito;
import ru.functions.Pow;
import ru.functions.PowImpl;
import ru.functions.logarithm.Logarithm;
import ru.functions.logarithm.LogarithmImpl;
import ru.functions.trigonometry.*;

import static java.lang.Math.PI;
import static ru.system.Pair.pair;

/**
 * Modules:
 * - trigonometry
 * - logarithm
 * - 1 part of function system, x <= 0
 * - 2 part of function system, x > 0
 * - function system
 */
public class ModulesFactory {
    private static final Pair[] X_LOWER_MODULE_STUBS = {
            pair(-2 * PI, -1.2246467991473527E-16),
            pair(-2 * PI + PI / 8, 0.11493074212616675),
            pair(-2 * PI + PI / 4, 0.8830161731427136),
            pair(-2 * PI + 3 * PI / 8, 1.6565152334789208),
            pair(-1.5 * PI, 2.0),
            pair(-1.5 * PI + PI / 8, 1.6565152334789204),
            pair(-1.5 * PI + PI / 4, 0.8830161731427131),
            pair(-1.5 * PI + 3 * PI / 8, 0.11493074212616586),
            pair(-PI / 2, Double.NaN),
            pair(-PI / 8, 0.17796247668728657),
            pair(0, Double.NaN),
    };
    private static final Pair[] X_MORE_MODULE_STUBS = {
            pair(0.0001, 6.450540248401554E-4),
            pair(0.25, 0.00428565),
            pair(0.5, 0.008571292351346774),
            pair(0.75, 0.02065185041409495),
            pair(0.9999, 59.40870063784119),
            pair(1.0001, -59.41464180497293),
            pair(3, -0.005407883371024116),
            pair(10, -0.0025802160993606362),
    };

    private static Sinus createSinusModuleStub() {
        return Math::sin;
    }

    private static Logarithm createLogarithmModuleStub() {
        return Math::log;
    }

    public static XMoreZeroFunction createXMoreZeroFunctionModule(boolean stubLogarithm) {
        Logarithm result;
        if (stubLogarithm) {
            result = createLogarithmModuleStub();
        } else {
            result = new LogarithmImpl();
        }
        return new XMoreZeroFunctionImpl(result, new PowImpl());
    }

    public static XMoreZeroFunction createXMoreZeroFunctionModuleStub() {
        XMoreZeroFunction functionMock = Mockito.mock(XMoreZeroFunction.class);
        for (Pair pair : X_MORE_MODULE_STUBS) {
            Mockito.when(functionMock.apply(pair.x))
                    .thenReturn(pair.y);
        }
        return functionMock;
    }

    public static XLowerZeroFunction createXLowerZeroFunctionModule(boolean stubTigonometry) {
        Sinus sinus;
        if (stubTigonometry) {
            sinus = createSinusModuleStub();
        } else {
            sinus = new SinusImpl();
        }
        Cosinus cosinus = new CosinusImpl(sinus);
        Secans secans = new SecansImpl(cosinus);
        Cosecans cosecans = new CosecansImpl(sinus);
        Cotangens cotangens = new CotangensImpl(cosinus, sinus);
        Pow pow = new PowImpl();
        return new XLowerZeroFunctionImpl(sinus, cosinus, secans, cosecans, cotangens, pow);
    }

    public static XLowerZeroFunction createXLowerZeroFunctionModuleStub() {
        XLowerZeroFunction functionMock = Mockito.mock(XLowerZeroFunction.class);
        for (Pair pair : X_LOWER_MODULE_STUBS) {
            Mockito.when(functionMock.apply(pair.x))
                    .thenReturn(pair.y);
        }
        return functionMock;
    }
}
