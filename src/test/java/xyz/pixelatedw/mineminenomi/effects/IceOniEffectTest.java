package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IceOniEffectTest {

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion();
        System.setProperty("neoforge.test.loadModdedFlags", "false");
        try {
            Bootstrap.bootStrap();
        } catch (Throwable t) {
            // Ignore bootstrap exceptions since we bypass it completely for pure logic test
        }
    }

    @Test
    @DisplayName("Test shouldApplyEffectTick logic (duration % 20 == 0)")
    public void testShouldApplyEffectTick() throws Exception {
        // Mockito fails because we cannot mock the static class initialization of MobEffect.
        // It throws ExceptionInInitializerError even with CALLS_REAL_METHODS because ByteBuddy
        // forces class loading and `<clinit>` fires.

        // Therefore, we use Unsafe and Reflection to strictly test the logic of this one method.
        Method method = IceOniEffect.class.getDeclaredMethod("shouldApplyEffectTick", int.class, int.class);
        method.setAccessible(true);

        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) f.get(null);

        IceOniEffect effect = (IceOniEffect) unsafe.allocateInstance(IceOniEffect.class);

        // Happy paths: durations that are multiples of 20
        assertTrue((Boolean) method.invoke(effect, 20, 0), "Should apply on tick 20");
        assertTrue((Boolean) method.invoke(effect, 40, 0), "Should apply on tick 40");
        assertTrue((Boolean) method.invoke(effect, 0, 0), "Should apply on tick 0");

        // Edge/Error conditions: durations that are NOT multiples of 20
        assertFalse((Boolean) method.invoke(effect, 19, 0), "Should not apply on tick 19");
        assertFalse((Boolean) method.invoke(effect, 21, 0), "Should not apply on tick 21");
        assertFalse((Boolean) method.invoke(effect, 1, 0), "Should not apply on tick 1");
    }
}
