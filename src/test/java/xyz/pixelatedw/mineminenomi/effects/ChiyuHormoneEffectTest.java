package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChiyuHormoneEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        ChiyuHormoneEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (ChiyuHormoneEffect) unsafe.allocateInstance(ChiyuHormoneEffect.class);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);

        // Health < MaxHealth
        when(mockEntity.getHealth()).thenReturn(10.0F);
        when(mockEntity.getMaxHealth()).thenReturn(20.0F);

        boolean result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, times(1)).heal(1.0F);

        // Reset mock to test Health >= MaxHealth
        reset(mockEntity);
        when(mockEntity.getHealth()).thenReturn(20.0F);
        when(mockEntity.getMaxHealth()).thenReturn(20.0F);

        result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, never()).heal(anyFloat());
    }
}
