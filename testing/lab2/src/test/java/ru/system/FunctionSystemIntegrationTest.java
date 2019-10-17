package ru.system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static java.lang.Math.PI;
import static ru.system.Pair.pair;

/**
 * Top-down integration testing
 */
class FunctionSystemIntegrationTest {
    private static final double DELTA = 1E-3;

    private static final Pair[] TEST_CASES = {
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
            pair(0.0001, -1.3201142694904737E-4),
            pair(0.25, -8.770649360564282E-4),
            pair(0.5, -0.0017541298721128564),
            pair(0.75, -0.004226437069326902),
            pair(0.9999, -12.158093806691024),
            pair(1.0001, 12.159309676867144),
            pair(3, 0.0011067327279444907),
            pair(10, 5.280457077961937E-4),
            // TODO: need add +/- infinity?
    };

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

    private void testSystem(FunctionSystem functionSystem) {
        testFunction(functionSystem::apply);
    }

    private void testFunction(Function<Double, Double> function) {
        for (Pair testCase : TEST_CASES) {
            try {
                double result = function.apply(testCase.x);
                System.out.println("for x = " + testCase.x + " result was " + result);
            } catch (Exception e) {
                System.out.println("for x = " + testCase.x + " exception thrown: " + e.getMessage());
            }

        }
        for (Pair testCase : TEST_CASES) {
            Assertions.assertEquals(testCase.y, function.apply(testCase.x), DELTA,
                    "incorrect result for x = " + testCase.x);
        }
    }

    @Test
    void integrateNothing() {
        FunctionSystem functionSystem = new FunctionSystemBuilder().build();

        testSystem(functionSystem);
    }

    @Test
    void integrateLowerZeroWithTrigStub() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModuleWithTrigStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateLowerZero() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModule()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateMoreZeroWithLogStub() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementMoreZeroModuleWithLogStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateMoreZero() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementMoreZeroModule()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateTwoWithStubs() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModuleWithTrigStub()
                .implementMoreZeroModuleWithLogStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateAll() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModule()
                .implementMoreZeroModule()
                .build();

        testSystem(functionSystem);
    }
}