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
            pair(0.0001, -1.3201142694904737E-4),
            pair(0.25, -8.770649360564282E-4),
            pair(0.5, -0.0017541298721128564),
            pair(0.75, -0.004226437069326902),
            pair(0.9999, -12.158093806691024),
            pair(1.0001, 12.159309676867144),
            pair(3, 0.0011067327279444907),
            pair(10, 5.280457077961937E-4),
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
