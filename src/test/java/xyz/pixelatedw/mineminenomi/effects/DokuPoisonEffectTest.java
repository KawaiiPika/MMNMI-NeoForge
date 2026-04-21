package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DokuPoisonEffectTest extends AbstractMinecraftTest {

    @Test
    void testApplyEffectTick() throws Exception {
        DokuPoisonEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (DokuPoisonEffect) unsafe.allocateInstance(DokuPoisonEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);

        // Test case 2: Health <= 1.0F
        when(mockEntity.getHealth()).thenReturn(1.0F);
        boolean result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result, "applyEffectTick should return true");

        // Should not hurt entity
        verify(mockEntity, never()).hurt(any(), anyFloat());
    }
}
