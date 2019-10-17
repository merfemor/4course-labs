package ru.csv;

import ru.system.FunctionSystem;
import ru.system.FunctionSystemBuilder;

import java.io.IOException;
import java.util.function.Function;

public class CsvReporter {

    public static void main(String[] args) {
        String fileName = "lower-zero-results.csv";

        FunctionSystem functionSystem = new FunctionSystemBuilder()
                .implementMoreZeroModule()
                .implementLowerZeroModule()
                .build();

        logIntoCsv(fileName, -7.015, 0.01, 2, functionSystem::apply);
    }

    public static void logIntoCsv(String filename, double startX, double diff, double until, Function<Double, Double> function) {
        assert startX <= until;
        CsvResultsLogger csvResultsLogger = new CsvResultsLogger(filename);

        for (double x = startX; x < until; x += diff) {
            double result = function.apply(x);
            csvResultsLogger.logResult(x, result);
        }
        try {
            csvResultsLogger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
