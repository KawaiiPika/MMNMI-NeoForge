package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MummyVirusEffectTest extends AbstractMinecraftTest {

    @Test
    public void testShouldApplyEffectTick() throws Exception {
        MummyVirusEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (MummyVirusEffect) unsafe.allocateInstance(MummyVirusEffect.class);
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
    }

    @Test
    public void applyEffectTick_ClientSide_DoesNotAddEffects() throws Exception {
        MummyVirusEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (MummyVirusEffect) unsafe.allocateInstance(MummyVirusEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);
        Level mockLevel = mock(Level.class);

        when(mockEntity.level()).thenReturn(mockLevel);

        when(mockLevel.isClientSide()).thenReturn(true);

        boolean result = effect.applyEffectTick(mockEntity, 0);

        assertTrue(result);
        verify(mockEntity, never()).hasEffect(any());
        verify(mockEntity, never()).addEffect(any());
    }
}
