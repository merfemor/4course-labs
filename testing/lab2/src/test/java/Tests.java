import FuncSystem.Function;
import Functions.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class Tests {
    private static final double DELTA = 1E-3;

    @Test
    public void testSin() {
        double x = -100.9987 * Math.PI;
        Sinus Sinus = new Sinus();
        assertEquals(Math.sin(x), Sinus.sin(x), DELTA);
    }

    @Test
    public void testCos() {
        double x = 00.9987 * Math.PI;
        Cosinus Cosinus = new Cosinus();
        assertEquals(Math.cos(x), Cosinus.cos(x), DELTA);
    }

    @Test
    public void testCsc() {
        double x = Math.PI * 0;
        Secans Secans = new Secans();
        assertEquals(1 / Math.cos(x), Secans.scs(x), DELTA);
    }

    @Test
    public void testLn() {
        double x = 1000;
        NaturalLog Ln = new NaturalLog();
        assertEquals(Math.log(x), Ln.ln(x), DELTA);
    }

    @Test
    public void testLog() {
        double x = 0.3;
        Log2 Log2 = new Log2();
        Log5 Log5 = new Log5();
        assertEquals(Math.log(x) / Math.log(2) * Math.log(x) / Math.log(5), Log2.ln(x) * Log5.ln(x), DELTA);
    }


    @Test
    public void testFunc() {
        double x = -0.5*2.2231*Math.PI;
        //FuncSystem.Function f = mock(Function.class);
        //Sinus sinus = mock(Sinus.class);
        //when(sinus.sin(-Math.PI)).thenReturn(0.0);
        //when(f.customFunc(0.0)).thenReturn(0.0);
        // для заглушек
        FuncSystem.Function f = new Function();
        assertEquals(f.func(x), f.customFunc(x), DELTA);
    }
}
