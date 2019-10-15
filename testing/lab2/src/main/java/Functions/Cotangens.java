package Functions;

public class Cotangens implements Cot {
    @Override
    public double cot(double x) {
        Cosinus cosinus = new Cosinus();
        Sinus sinus = new Sinus();
        return cosinus.cos(x)/sinus.sin(x);
    }
}
