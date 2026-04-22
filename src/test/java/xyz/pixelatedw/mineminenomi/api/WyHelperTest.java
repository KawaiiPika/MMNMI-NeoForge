package xyz.pixelatedw.mineminenomi.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WyHelperTest {

    @Test
    public void testRandomDouble() {
        for (int i = 0; i < 1000; i++) {
            double val = WyHelper.randomDouble();
            assertTrue(val >= -1.0 && val < 1.0, "randomDouble() value out of range [-1.0, 1.0): " + val);
        }
    }

    @Test
    public void testRandomWithRangeDouble() {
        double min = 5.0;
        double max = 250.0;
        for (int i = 0; i < 1000; i++) {
            double val = WyHelper.randomWithRange(min, max);
            assertTrue(val >= min && val < max, "randomWithRange(double, double) value out of range [" + min + ", " + max + "): " + val);
        }
    }

    @Test
    public void testRandomWithRangeInt() {
        int min = 0;
        int max = 10;
        for (int i = 0; i < 1000; i++) {
            double val = WyHelper.randomWithRange(min, max);
            assertTrue(val >= min && val <= max, "randomWithRange(int, int) value out of range [" + min + ", " + max + "]: " + val);
            assertEquals(val, (double)((int)val), "randomWithRange(int, int) should return an integer value");
        }
    }
}
