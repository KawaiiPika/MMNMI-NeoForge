package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HandcuffedEffectTest {

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
    void shouldApplyEffectTick_AlwaysReturnsTrue() {
        HandcuffedEffect effect = Mockito.mock(HandcuffedEffect.class, Mockito.CALLS_REAL_METHODS);

        assertTrue(effect.shouldApplyEffectTick(0, 0), "Should apply effect tick for 0 duration, 0 amplifier");
        assertTrue(effect.shouldApplyEffectTick(100, 1), "Should apply effect tick for positive duration, positive amplifier");
        assertTrue(effect.shouldApplyEffectTick(-1, -1), "Should apply effect tick for negative duration, negative amplifier");
        assertTrue(effect.shouldApplyEffectTick(Integer.MAX_VALUE, Integer.MAX_VALUE), "Should apply effect tick for max integer values");
    }
}
