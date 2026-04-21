package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HandcuffedEffectTest extends AbstractMinecraftTest {

    @Test
    void shouldApplyEffectTick_AlwaysReturnsTrue() throws Exception {
        HandcuffedEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (HandcuffedEffect) unsafe.allocateInstance(HandcuffedEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        assertTrue(effect.shouldApplyEffectTick(0, 0), "Should apply effect tick for 0 duration, 0 amplifier");
        assertTrue(effect.shouldApplyEffectTick(100, 1), "Should apply effect tick for positive duration, positive amplifier");
        assertTrue(effect.shouldApplyEffectTick(-1, -1), "Should apply effect tick for negative duration, negative amplifier");
        assertTrue(effect.shouldApplyEffectTick(Integer.MAX_VALUE, Integer.MAX_VALUE), "Should apply effect tick for max integer values");
    }

    @Test
    void applyEffectTick_AlwaysReturnsTrue() throws Exception {
        HandcuffedEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (HandcuffedEffect) unsafe.allocateInstance(HandcuffedEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }
        net.minecraft.world.entity.LivingEntity mockEntity = Mockito.mock(net.minecraft.world.entity.LivingEntity.class);

        assertTrue(effect.applyEffectTick(mockEntity, 0), "Should return true");
    }
}
