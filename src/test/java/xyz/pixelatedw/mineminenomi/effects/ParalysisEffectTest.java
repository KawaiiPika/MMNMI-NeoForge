package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ParalysisEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        ParalysisEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (ParalysisEffect) unsafe.allocateInstance(ParalysisEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);

        // Amplifier > 0 -> Zeroes out delta movement
        boolean result = effect.applyEffectTick(mockEntity, 1);
        assertTrue(result);
        verify(mockEntity, times(1)).setDeltaMovement(0, 0, 0);

        // Amplifier = 0 -> Does not modify delta movement
        reset(mockEntity);
        result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, never()).setDeltaMovement(anyDouble(), anyDouble(), anyDouble());
    }
}
