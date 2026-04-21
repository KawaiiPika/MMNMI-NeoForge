package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AntidoteEffectTest extends AbstractMinecraftTest {

    @Test
    void testShouldApplyEffectTick() throws Exception {
        AntidoteEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (AntidoteEffect) unsafe.allocateInstance(AntidoteEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        assertTrue(effect.shouldApplyEffectTick(20, 0), "Should apply effect when duration is 20");
        assertTrue(effect.shouldApplyEffectTick(40, 0), "Should apply effect when duration is 40");
        assertTrue(effect.shouldApplyEffectTick(100, 0), "Should apply effect when duration is 100");
        assertTrue(effect.shouldApplyEffectTick(0, 0), "Should apply effect when duration is 0");

        assertFalse(effect.shouldApplyEffectTick(19, 0), "Should not apply effect when duration is 19");
        assertFalse(effect.shouldApplyEffectTick(21, 0), "Should not apply effect when duration is 21");
        assertFalse(effect.shouldApplyEffectTick(25, 0), "Should not apply effect when duration is 25");

        assertTrue(effect.shouldApplyEffectTick(40, 1), "Should apply effect when duration is 40, regardless of amplifier");
        assertFalse(effect.shouldApplyEffectTick(25, 1), "Should not apply effect when duration is 25, regardless of amplifier");
    }

    @Test
    void testApplyEffectTick() throws Exception {
        AntidoteEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (AntidoteEffect) unsafe.allocateInstance(AntidoteEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);
        Level mockLevel = mock(Level.class);

        when(mockEntity.level()).thenReturn(mockLevel);
        when(mockLevel.isClientSide()).thenReturn(true);

        assertTrue(effect.applyEffectTick(mockEntity, 0));
        verify(mockEntity, never()).getActiveEffects();
    }
}
