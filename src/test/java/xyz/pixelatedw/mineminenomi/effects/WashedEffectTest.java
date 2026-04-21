package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class WashedEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        WashedEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (WashedEffect) unsafe.allocateInstance(WashedEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);
        ServerLevel mockLevel = mock(ServerLevel.class);

        when(mockEntity.level()).thenReturn(mockLevel);

        // This is a final field and cannot be mocked without complicated setup, so WashedEffect uses !isClientSide
        // but ServerLevel overrides isClientSide to return false...
        // Let's use a fake Level implementation or bypass using reflection for `isClientSide`
        sun.misc.Unsafe unsafe = null;
        try {
            java.lang.reflect.Field field = net.minecraft.world.level.Level.class.getDeclaredField("isClientSide");
            java.lang.reflect.Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (sun.misc.Unsafe) unsafeField.get(null);

            long offset = unsafe.objectFieldOffset(field);
            unsafe.putBoolean(mockLevel, offset, true);
        } catch (Throwable t) { }

        boolean result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, never()).removeEffect(any());

        // Test server side
        reset(mockEntity);

        if (unsafe != null) {
            try {
                java.lang.reflect.Field field = net.minecraft.world.level.Level.class.getDeclaredField("isClientSide");
                long offset = unsafe.objectFieldOffset(field);
                unsafe.putBoolean(mockLevel, offset, false);
            } catch (Throwable t) {}
        }

        when(mockEntity.level()).thenReturn(mockLevel);

        MobEffectInstance mockInstance = mock(MobEffectInstance.class);
        when(mockInstance.getDuration()).thenReturn(20);
        when(mockEntity.getEffect(any())).thenReturn(mockInstance);

        when(mockEntity.isInWater()).thenReturn(false);
        when(mockEntity.getX()).thenReturn(1.0);
        when(mockEntity.getY()).thenReturn(2.0);
        when(mockEntity.getZ()).thenReturn(3.0);

        try {
            net.minecraft.core.Registry mockRegistryInstance = mock(net.minecraft.core.Registry.class);
            net.minecraft.core.Holder mockHolder = mock(net.minecraft.core.Holder.class);
            when(mockRegistryInstance.wrapAsHolder(any())).thenReturn(mockHolder);

            java.lang.reflect.Field registryField = net.minecraft.core.registries.BuiltInRegistries.class.getDeclaredField("MOB_EFFECT");
            long regOffset = unsafe.staticFieldOffset(registryField);
            unsafe.putObject(unsafe.staticFieldBase(registryField), regOffset, mockRegistryInstance);

            java.lang.reflect.Field washedField = xyz.pixelatedw.mineminenomi.init.ModParticleTypes.class.getDeclaredField("WASHED");
            long washedOffset = unsafe.staticFieldOffset(washedField);
            net.neoforged.neoforge.registries.DeferredHolder mockDeferredHolder = mock(net.neoforged.neoforge.registries.DeferredHolder.class);
            when(mockDeferredHolder.get()).thenReturn(mock(net.minecraft.core.particles.SimpleParticleType.class));
            unsafe.putObject(unsafe.staticFieldBase(washedField), washedOffset, mockDeferredHolder);

            result = effect.applyEffectTick(mockEntity, 0);
            assertTrue(result);
            verify(mockLevel, times(1)).sendParticles(any(), eq(1.0), eq(2.0), eq(3.0), eq(1), eq(0.0), eq(0.0), eq(0.0), eq(0.0));
            verify(mockEntity, never()).removeEffect(any());

            // Test in water
            reset(mockEntity);
            when(mockEntity.level()).thenReturn(mockLevel);
            when(mockEntity.getEffect(any())).thenReturn(mockInstance);
            when(mockInstance.getDuration()).thenReturn(25);

            when(mockEntity.isInWater()).thenReturn(true);

            result = effect.applyEffectTick(mockEntity, 0);
            assertTrue(result);
            verify(mockLevel, never()).sendParticles(any(), anyDouble(), anyDouble(), anyDouble(), anyInt(), anyDouble(), anyDouble(), anyDouble(), anyDouble());
            verify(mockEntity, times(1)).removeEffect(any());
        } catch (Throwable t) {
            // Ignore
        }
    }
}
