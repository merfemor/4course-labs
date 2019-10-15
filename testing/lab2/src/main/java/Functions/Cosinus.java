package Functions;

public class Cosinus implements Cos {
    @Override
    public double cos(double x) {
        Sinus sinus = new Sinus();
        return sinus.sin(x+Math.PI/2);
    }
}
