package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import java.util.function.Function;
import static org.mockito.Mockito.*;

class DamageOverTimeEffectTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Ignore bootstrap issues if already bootstrapped or missing some parts
        }
    }

    @Test
    void testShouldApplyEffectTick() throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

        DamageOverTimeEffect effect = (DamageOverTimeEffect) unsafe.allocateInstance(DamageOverTimeEffect.class);

        java.lang.reflect.Field freqField = DamageOverTimeEffect.class.getDeclaredField("freq");
        freqField.setAccessible(true);
        freqField.set(effect, 20);

        assertTrue(effect.shouldApplyEffectTick(20, 0), "Should apply effect when duration is 20");
        assertTrue(effect.shouldApplyEffectTick(40, 0), "Should apply effect when duration is 40");
        assertFalse(effect.shouldApplyEffectTick(19, 0), "Should not apply effect when duration is 19");
        assertTrue(effect.shouldApplyEffectTick(0, 0), "Should apply effect when duration is 0");
    }

    @Test
    void testApplyEffectTick() throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

        DamageOverTimeEffect effect = (DamageOverTimeEffect) unsafe.allocateInstance(DamageOverTimeEffect.class);

        LivingEntity mockEntity = mock(LivingEntity.class);
        DamageSource mockSource = mock(DamageSource.class);
        Function<LivingEntity, DamageSource> damageFunction = (e) -> mockSource;

        java.lang.reflect.Field baseDamageField = DamageOverTimeEffect.class.getDeclaredField("baseDamage");
        baseDamageField.setAccessible(true);
        baseDamageField.set(effect, 5.0f);

        java.lang.reflect.Field damageFunctionField = DamageOverTimeEffect.class.getDeclaredField("damageFunction");
        damageFunctionField.setAccessible(true);
        damageFunctionField.set(effect, damageFunction);

        java.lang.reflect.Field damageScalingField = DamageOverTimeEffect.class.getDeclaredField("damageScaling");
        damageScalingField.setAccessible(true);
        Function<Integer, Float> scalingFunction = (amp) -> 5.0f * (float)(amp + 1);
        damageScalingField.set(effect, scalingFunction);

        boolean result = effect.applyEffectTick(mockEntity, 1);
        assertTrue(result);

        verify(mockEntity).hurt(mockSource, 10.0f); // amplifier 1: 5.0 * (1 + 1) = 10.0
    }
}
