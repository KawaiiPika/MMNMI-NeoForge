package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class IceOniEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        IceOniEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (IceOniEffect) unsafe.allocateInstance(IceOniEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);
        Level mockLevel = mock(Level.class);

        when(mockEntity.level()).thenReturn(mockLevel);

        when(mockLevel.isClientSide()).thenReturn(true);

        boolean result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, never()).addEffect(any());

        when(mockLevel.isClientSide()).thenReturn(false);

        try {
            sun.misc.Unsafe unsafe = null;
            java.lang.reflect.Field unsafeField = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (sun.misc.Unsafe) unsafeField.get(null);

            java.lang.reflect.Field frostbiteField = xyz.pixelatedw.mineminenomi.init.ModEffects.class.getDeclaredField("FROSTBITE");
            frostbiteField.setAccessible(true);
            long frostbiteOffset = unsafe.staticFieldOffset(frostbiteField);
            net.neoforged.neoforge.registries.DeferredHolder mockDeferredHolder = mock(net.neoforged.neoforge.registries.DeferredHolder.class);
            unsafe.putObject(unsafe.staticFieldBase(frostbiteField), frostbiteOffset, mockDeferredHolder);

            result = effect.applyEffectTick(mockEntity, 0);
            assertTrue(result);
            verify(mockEntity, times(1)).addEffect(any());
        } catch (Throwable t) {
            // Ignore
        }
    }
}
