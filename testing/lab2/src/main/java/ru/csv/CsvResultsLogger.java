package ru.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvResultsLogger {
    private final String fileName;
    private final List<Double> args = new ArrayList<>();
    private final List<Double> results = new ArrayList<>();

    public CsvResultsLogger(String fileName) {
        this.fileName = fileName;
    }

    public void logResult(double arg, double result) {
        args.add(arg);
        results.add(result);
    }

    private byte[] createData() {
        final StringBuilder csvContentBuilder = new StringBuilder("X,Результаты модуля\n");

        for (int i = 0; i < args.size(); i++) {
            double arg = args.get(i);
            double result = results.get(i);

            csvContentBuilder.append(arg)
                    .append(",")
                    .append(result)
                    .append("\n");
        }
        return csvContentBuilder.toString().getBytes();
    }

    public void close() throws IOException {
        Files.write(Paths.get(fileName), createData());
    }
}
