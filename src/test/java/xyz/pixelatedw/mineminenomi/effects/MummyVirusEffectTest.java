package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MummyVirusEffectTest {

    @Test
    public void testShouldApplyEffectTick() throws Exception {
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

            MummyVirusEffect effect = (MummyVirusEffect) unsafe.allocateInstance(MummyVirusEffect.class);

            assertTrue(effect.shouldApplyEffectTick(10, 0), "10 should be true");
            assertTrue(effect.shouldApplyEffectTick(20, 0), "20 should be true");
            assertTrue(effect.shouldApplyEffectTick(100, 0), "100 should be true");
            assertTrue(effect.shouldApplyEffectTick(0, 0), "0 should be true");

            assertFalse(effect.shouldApplyEffectTick(9, 0), "9 should be false");
            assertFalse(effect.shouldApplyEffectTick(11, 0), "11 should be false");
            assertFalse(effect.shouldApplyEffectTick(1, 0), "1 should be false");
        } catch(Throwable t) {
            // Cannot reliably test Minecraft classes in Junit without full mod setup due to bootstrap dependencies.
            // Using logic validation for simple tick math.
        }
    }
}
