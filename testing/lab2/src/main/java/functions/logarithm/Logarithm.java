package functions.logarithm;

public interface Logarithm {
    double ln(double x);

    default double log2(double x) {
        return ln(x) / ln(2);
    }

    default double log3(double x) {
        return ln(x) / ln(3);
    }

    default double log5(double x) {
        return ln(x) / ln(5);
    }

    default double log10(double x) {
        return ln(x) / ln(10);
    }
}
