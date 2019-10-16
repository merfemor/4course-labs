package system;

import functions.Pow;
import functions.PowImpl;
import functions.logarithm.Logarithm;
import functions.logarithm.LogarithmImpl;
import functions.trigonometry.*;

/**
 * Modules:
 * - trigonometry
 * - logarithm
 * - 1 part of function system, x <= 0
 * - 2 part of function system, x > 0
 * - function system
 */
public class ModulesFactory {
    private static Sinus createSinusModuleStub() {
        // TODO: заменить на таблицу ответов с помденой через mockito
        return Math::sin;
    }

    private static Logarithm createLogarithmModuleStub() {
        // TODO: заменить на таблицу ответов с помденой через mockito
        return Math::log;
    }

    public static XMoreZeroFunction createXMoreZeroFunctionModule(boolean stubLogarithm) {
        Logarithm result;
        if (stubLogarithm) {
            result = createLogarithmModuleStub();
        } else {
            result = new LogarithmImpl();
        }
        return new XMoreZeroFunctionImpl(result);
    }

    public static XMoreZeroFunction createXMoreZeroFunctionModuleStub() {
        // TODO: заменить на таблицу ответов с помденой через mockito
        throw new UnsupportedOperationException();
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
        Pow pow = new PowImpl();
        return new XLowerZeroFunctionImpl(sinus, cosinus, secans, cosecans, pow);
    }

    public static XLowerZeroFunction createXLowerZeroFunctionModuleStub() {
        // TODO: заменить на таблицу ответов с помденой через mockito
        throw new UnsupportedOperationException();
    }

}
