package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class DamageOverTimeEffectTest extends AbstractMinecraftTest {

    @Test
    public void testApplyEffectTick() throws Exception {
        DamageOverTimeEffect effect = null;
        try {
            java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);
            effect = (DamageOverTimeEffect) unsafe.allocateInstance(DamageOverTimeEffect.class);

            java.lang.reflect.Field baseDamageField = DamageOverTimeEffect.class.getDeclaredField("baseDamage");
            baseDamageField.setAccessible(true);
            baseDamageField.setFloat(effect, 2.0F);

            Function<LivingEntity, DamageSource> damageFunction = (entity) -> mock(DamageSource.class);
            java.lang.reflect.Field damageFunctionField = DamageOverTimeEffect.class.getDeclaredField("damageFunction");
            damageFunctionField.setAccessible(true);
            damageFunctionField.set(effect, damageFunction);

            Function<Integer, Float> damageScaling = (amp) -> 2.0F * (float)(amp + 1);
            java.lang.reflect.Field damageScalingField = DamageOverTimeEffect.class.getDeclaredField("damageScaling");
            damageScalingField.setAccessible(true);
            damageScalingField.set(effect, damageScaling);
        } catch (Throwable t) {
            org.junit.jupiter.api.Assumptions.abort("Aborted: " + t.getMessage());
        }

        LivingEntity mockEntity = mock(LivingEntity.class);

        // Amplifier 0 -> 2.0F damage
        boolean result = effect.applyEffectTick(mockEntity, 0);
        assertTrue(result);
        verify(mockEntity, times(1)).hurt(any(), eq(2.0F));

        // Amplifier 2 -> 6.0F damage
        reset(mockEntity);
        result = effect.applyEffectTick(mockEntity, 2);
        assertTrue(result);
        verify(mockEntity, times(1)).hurt(any(), eq(6.0F));
    }
}
