package xyz.pixelatedw.mineminenomi.effects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.mockito.Mockito;

public class StickyEffectTest extends AbstractMinecraftTest {

    @Test
    public void testShouldApplyEffectTick() throws Exception {
        StickyEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (StickyEffect) unsafe.allocateInstance(StickyEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        assertTrue(effect.shouldApplyEffectTick(10, 0), "10 should be true");
        assertTrue(effect.shouldApplyEffectTick(20, 0), "20 should be true");
        assertTrue(effect.shouldApplyEffectTick(100, 0), "100 should be true");
        assertTrue(effect.shouldApplyEffectTick(0, 0), "0 should be true");

        assertFalse(effect.shouldApplyEffectTick(9, 0), "9 should be false");
        assertFalse(effect.shouldApplyEffectTick(11, 0), "11 should be false");
        assertFalse(effect.shouldApplyEffectTick(1, 0), "1 should be false");

        assertTrue(effect.shouldApplyEffectTick(10, 1), "10 should be true, regardless of amplifier");
        assertFalse(effect.shouldApplyEffectTick(9, 1), "9 should be false, regardless of amplifier");
    }

    @Test
    public void testApplyEffectTick() throws Exception {
        StickyEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (StickyEffect) unsafe.allocateInstance(StickyEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        net.minecraft.world.entity.LivingEntity mockEntity = org.mockito.Mockito.mock(net.minecraft.world.entity.LivingEntity.class);
        net.minecraft.world.level.Level mockLevel = org.mockito.Mockito.mock(net.minecraft.world.level.Level.class);

        org.mockito.Mockito.when(mockEntity.isOnFire()).thenReturn(false);
        assertTrue(effect.applyEffectTick(mockEntity, 0));
        org.mockito.Mockito.verify(mockEntity, org.mockito.Mockito.never()).clearFire();

        org.mockito.Mockito.reset(mockEntity);
        org.mockito.Mockito.when(mockEntity.isOnFire()).thenReturn(true);
        mockEntity.tickCount = 0;
        assertTrue(effect.applyEffectTick(mockEntity, 0));
        org.mockito.Mockito.verify(mockEntity, org.mockito.Mockito.never()).clearFire();
    }
}
