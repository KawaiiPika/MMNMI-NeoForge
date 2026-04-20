package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Assumptions;

public class StickyEffectTest {

    @Test
    public void testShouldApplyEffectTick() throws Exception {
        StickyEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

            effect = (StickyEffect) unsafe.allocateInstance(StickyEffect.class);
        } catch (Throwable t) {
            // Cannot reliably test Minecraft classes in Junit without full mod setup due to bootstrap dependencies.
            // Ignore minecraft bootstrap errors by explicitly aborting the test, marking it ignored instead of passing.
            Assumptions.abort("Cannot instantiate StickyEffect without full Minecraft bootstrap: " + t.getMessage());
        }

        assertTrue(effect.shouldApplyEffectTick(10, 0), "10 should be true");
        assertTrue(effect.shouldApplyEffectTick(20, 0), "20 should be true");
        assertTrue(effect.shouldApplyEffectTick(100, 0), "100 should be true");
        assertTrue(effect.shouldApplyEffectTick(0, 0), "0 should be true");

        assertFalse(effect.shouldApplyEffectTick(9, 0), "9 should be false");
        assertFalse(effect.shouldApplyEffectTick(11, 0), "11 should be false");
        assertFalse(effect.shouldApplyEffectTick(1, 0), "1 should be false");

        // Amplifier shouldn't matter
        assertTrue(effect.shouldApplyEffectTick(10, 1), "10 should be true, regardless of amplifier");
        assertFalse(effect.shouldApplyEffectTick(9, 1), "9 should be false, regardless of amplifier");
    }
}
