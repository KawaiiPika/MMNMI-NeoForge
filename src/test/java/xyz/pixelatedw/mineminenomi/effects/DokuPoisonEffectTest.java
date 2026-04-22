package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DokuPoisonEffectTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped or missing some parts
        }
    }

    @Test
    void testShouldApplyEffectTick() {
        DokuPoisonEffect effect = Mockito.mock(DokuPoisonEffect.class, Mockito.CALLS_REAL_METHODS);

        // When amplifier is 0, i = 25 >> 0 = 25
        assertTrue(effect.shouldApplyEffectTick(25, 0));
        assertTrue(effect.shouldApplyEffectTick(50, 0));
        assertFalse(effect.shouldApplyEffectTick(24, 0));

        // When amplifier is 1, i = 25 >> 1 = 12
        assertTrue(effect.shouldApplyEffectTick(12, 1));
        assertTrue(effect.shouldApplyEffectTick(24, 1));
        assertFalse(effect.shouldApplyEffectTick(11, 1));

        // When amplifier is 2, i = 25 >> 2 = 6
        assertTrue(effect.shouldApplyEffectTick(6, 2));
        assertTrue(effect.shouldApplyEffectTick(12, 2));
        assertFalse(effect.shouldApplyEffectTick(5, 2));

        // When amplifier is >= 5, i = 25 >> 5 = 0
        assertTrue(effect.shouldApplyEffectTick(1, 5));
        assertTrue(effect.shouldApplyEffectTick(2, 5));
        assertTrue(effect.shouldApplyEffectTick(1, 10));
    }
}
