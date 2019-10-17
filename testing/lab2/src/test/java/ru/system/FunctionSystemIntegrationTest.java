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
            pair(-PI / 8, 0.17796247668728657),
            pair(0, Double.NaN),
            pair(0.0001, 6.450540248401554E-4),
            pair(0.25, 0.00428565),
            pair(0.5, 0.008571292351346774),
            pair(0.75, 0.02065185041409495),
            pair(0.9999, 59.40810063784119),
            pair(1.0001, -59.41414180497293),
            pair(3, -0.005407883371024116),
            pair(10, -0.0025802160993606362),
    };

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
    void integrateLowerZeroWithFullTrigStub() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModuleWithFullTrigStub()
                .build();

        testSystem(functionSystem);
    }

    @Test
    void integrateLowerZeroWithTrigSinusStub() {
        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementLowerZeroModuleWithTrigSinusStub()
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
                .implementLowerZeroModuleWithTrigSinusStub()
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