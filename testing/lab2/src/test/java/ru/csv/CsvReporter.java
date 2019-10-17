package ru.csv;

import ru.system.ModulesFactory;
import ru.system.XLowerZeroFunction;

import java.io.IOException;
import java.util.function.Function;

public class CsvReporter {

    public static void main(String[] args) {
        String fileName = "lower-zero-results.csv";
        double startX = -200 * Math.PI;
        double diff = Math.PI / 2;
        int repeat = 10;

        XLowerZeroFunction xLowerZeroFunction = ModulesFactory.createXLowerZeroFunctionModule(false);
        Function<Double, Double> testingFunction = xLowerZeroFunction::apply;

        logIntoCsv(fileName, startX, diff, repeat, testingFunction);
    }

    public static void logIntoCsv(String filename, double startX, double diff, int repeat, Function<Double, Double> function) {
        CsvResultsLogger csvResultsLogger = new CsvResultsLogger(filename);

        double x = startX;
        for (int i = 0; i < repeat; i++) {
            double result = function.apply(x);
            csvResultsLogger.logResult(x, result);
            x += diff;
        }
        try {
            csvResultsLogger.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
