package system;

import functions.trigonometry.Sinus;
import functions.trigonometry.SinusImpl;


/**
 * Modules:
 * - trig
 * - log
 * - left half function
 * - right half function
 * - app
 */
public class ModulesFactory {
    public static Sinus createSinusModule(boolean stub) {
        if (stub) {
            // TODO: mockito
            throw new UnsupportedOperationException("not implemented");
        } else {
            return new SinusImpl();
        }
    }
}
