package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WashedEffectTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues
        }
    }

    @Test
    void testApplyEffectTick_RemovesEffectWhenInWater() throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

        WashedEffect effect = (WashedEffect) unsafe.allocateInstance(WashedEffect.class);

        LivingEntity entity = mock(LivingEntity.class);
        Level level = mock(Level.class);

        when(entity.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);

        MobEffectInstance effectInstance = mock(MobEffectInstance.class);
        // Use a duration that does not trigger particle effects (duration % 10 != 0)
        when(effectInstance.getDuration()).thenReturn(11);
        when(entity.getEffect(any(Holder.class))).thenReturn(effectInstance);

        // Simulate entity in water
        when(entity.isInWater()).thenReturn(true);

        boolean result = effect.applyEffectTick(entity, 0);

        assertTrue(result);
        verify(entity).removeEffect(any(Holder.class));
    }

    @Test
    void testApplyEffectTick_DoesNotRemoveEffectWhenNotInWater() throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

        WashedEffect effect = (WashedEffect) unsafe.allocateInstance(WashedEffect.class);

        LivingEntity entity = mock(LivingEntity.class);
        Level level = mock(Level.class);

        when(entity.level()).thenReturn(level);
        when(level.isClientSide()).thenReturn(false);

        MobEffectInstance effectInstance = mock(MobEffectInstance.class);
        when(effectInstance.getDuration()).thenReturn(11);
        when(entity.getEffect(any(Holder.class))).thenReturn(effectInstance);

        // Simulate entity NOT in water
        when(entity.isInWater()).thenReturn(false);

        boolean result = effect.applyEffectTick(entity, 0);

        assertTrue(result);
        verify(entity, never()).removeEffect(any(Holder.class));
    }
}
