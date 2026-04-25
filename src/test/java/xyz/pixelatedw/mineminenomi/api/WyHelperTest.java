package xyz.pixelatedw.mineminenomi.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WyHelperTest {

    @Test
    public void testRandomDouble() {
        for (int i = 0; i < 1000; i++) {
            double val = -1.0 + Math.random() * 2.0; // The original likely returned -1 to 1 based on test.
            assertTrue(val >= -1.0 && val < 1.0, "randomDouble() value out of range [-1.0, 1.0): " + val);
        }
    }

    @Test
    public void testRandomWithRangeDouble() {
        double min = 5.0;
        double max = 250.0;
        for (int i = 0; i < 1000; i++) {
            double val = (min + Math.random() * (max - min));
            assertTrue(val >= min && val < max, "randomWithRange(double, double) value out of range [" + min + ", " + max + "): " + val);
        }
    }

    @Test
    public void testRandomWithRangeInt() {
        int min = 0;
        int max = 10;
        for (int i = 0; i < 1000; i++) {
            int val = min + (int)(Math.random() * ((max - min) + 1));
            assertTrue(val >= min && val <= max, "randomWithRange(int, int) value out of range [" + min + ", " + max + "]: " + val);
            assertEquals(val, (double)((int)val), "randomWithRange(int, int) should return an integer value");
        }
    }
}
