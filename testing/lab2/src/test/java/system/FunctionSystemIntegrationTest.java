package system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Top-down integration testing
 */
public class FunctionSystemIntegrationTest {
    private static final double DELTA = 1E-3;

    private static final TestCase[] TEST_CASES = {
            tc(-5.5, 0.9246108118371832),
            tc(-3.418, 0.14654630876019004),
            tc(-3.29, 0.09734686542484962),
            tc(-2.6965, 0.019901473127664687)
            // TODO: здесь надо добавить тестов, на которых будем гонять
    };

    public static TestCase tc(double x, double y) {
        return new TestCase(x, y);
    }

    private void testSystem(FunctionSystem functionSystem) {
        for (TestCase test : TEST_CASES) {
            Assertions.assertEquals(test.y, functionSystem.apply(test.x), DELTA, "incorrect result for x = " + test.x);
        }

        // TODO: вывод в csv
    }

    // FIXME: remove all this commented code?
//    @Test
//    public void testSin() {
//        double x = -100.9987 * Math.PI;
//        SinusImpl Sinus = new SinusImpl();
//        assertEquals(Math.sin(x), Sinus.sin(x), DELTA);
//    }
//
//    @Test
//    public void testCos() {
//        double x = 00.9987 * Math.PI;
//        CosinusImpl Cosinus = new CosinusImpl();
//        assertEquals(Math.cos(x), Cosinus.cos(x), DELTA);
//    }
//
//    @Test
//    public void testCsc() {
//        double x = Math.PI * 0;
//        SecansImpl Secans = new SecansImpl();
//        assertEquals(1 / Math.cos(x), Secans.scs(x), DELTA);
//    }
//
//    @Test
//    public void testLn() {
//        double x = 1000;
//        LogarithmImpl Ln = new LogarithmImpl();
//        assertEquals(Math.log(x), Ln.log(x), DELTA);
//    }
//
//    @Test
//    public void testLog() {
//        double x = 0.3;
//        Log2 Log2 = new Log2();
//        Log5 Log5 = new Log5();
//        assertEquals(Math.log(x) / Math.log(2) * Math.log(x) / Math.log(5), Log2.log(x) * Log5.log(x), DELTA);
//    }

//
//    @Test
//    public void testFunc() {
//        double x = -0.5 * 2.2231 * Math.PI;
//        //system.Function f = mock(Function.class);
//        //Sinus sinus = mock(Sinus.class);
//        //when(sinus.sin(-Math.PI)).thenReturn(0.0);
//        //when(f.customFunc(0.0)).thenReturn(0.0);
//        // для заглушек
//        FunctionSystem f = new FunctionSystem();
//        assertEquals(f.strangeImpl(x), f.apply(x), DELTA);
//    }

    @Test
    void integrateNothing() {
        FunctionSystem functionSystem = new FunctionSystemFactory().build();

        testSystem(functionSystem);
    }

    @Test
    void integrateLowerZeroWithTrigStub() {
        FunctionSystem functionSystem = new FunctionSystemFactory()
                .implementLowerZeroModuleWithTrigStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateLowerZero() {
        FunctionSystem functionSystem = new FunctionSystemFactory()
                .implementLowerZeroModule()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateMoreZeroWithLogStub() {
        FunctionSystem functionSystem = new FunctionSystemFactory()
                .implementMoreZeroModuleWithLogStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateMoreZero() {
        FunctionSystem functionSystem = new FunctionSystemFactory()
                .implementMoreZeroModule()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateAll() {
        FunctionSystem functionSystem = new FunctionSystemFactory()
                .implementLowerZeroModule()
                .implementMoreZeroModule()
                .build();

        testSystem(functionSystem);
    }

    static class TestCase {
        final double x;
        final double y;

        TestCase(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
