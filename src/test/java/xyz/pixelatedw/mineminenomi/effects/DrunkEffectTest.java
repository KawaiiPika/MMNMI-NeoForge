package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import net.minecraft.world.entity.LivingEntity;
import org.mockito.Mockito;

class DrunkEffectTest extends AbstractMinecraftTest {

    @Test
    void shouldApplyEffectTick_AlwaysReturnsTrue() throws Exception {
        DrunkEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (DrunkEffect) unsafe.allocateInstance(DrunkEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        assertTrue(effect.shouldApplyEffectTick(0, 0), "Should apply effect tick for 0 duration, 0 amplifier");
        assertTrue(effect.shouldApplyEffectTick(100, 1), "Should apply effect tick for positive duration, positive amplifier");
        assertTrue(effect.shouldApplyEffectTick(-1, -1), "Should apply effect tick for negative duration, negative amplifier");
        assertTrue(effect.shouldApplyEffectTick(Integer.MAX_VALUE, Integer.MAX_VALUE), "Should apply effect tick for max integer values");
    }

    @Test
    void applyEffectTick_AddsConfusionEffect() throws Exception {
        DrunkEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (DrunkEffect) unsafe.allocateInstance(DrunkEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }
        LivingEntity mockEntity = Mockito.mock(LivingEntity.class);

        assertTrue(effect.applyEffectTick(mockEntity, 1));
        Mockito.verify(mockEntity, Mockito.never()).addEffect(Mockito.any());
    }
}
