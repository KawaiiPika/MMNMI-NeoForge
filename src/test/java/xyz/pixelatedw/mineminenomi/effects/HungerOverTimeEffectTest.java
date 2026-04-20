package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import net.minecraft.SharedConstants;

import static org.junit.jupiter.api.Assertions.*;

class HungerOverTimeEffectTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable t) {
            // Ignore bootstrap errors
        }
    }

    @Test
    void testShouldApplyEffectTick() throws Exception {
        // Allocate instance without calling constructors to avoid Minecraft initialization issues (MobEffect class initialization crashes without proper bootstrap)
        sun.misc.Unsafe unsafe = getUnsafe();
        HungerOverTimeEffect effect = (HungerOverTimeEffect) unsafe.allocateInstance(HungerOverTimeEffect.class);

        // Set private fields via reflection
        Field freqField = HungerOverTimeEffect.class.getDeclaredField("freq");
        freqField.setAccessible(true);
        freqField.setInt(effect, 20);

        // Test shouldApplyEffectTick logic
        // When duration is a multiple of freq (20)
        assertTrue(effect.shouldApplyEffectTick(20, 0));
        assertTrue(effect.shouldApplyEffectTick(40, 0));
        assertTrue(effect.shouldApplyEffectTick(0, 0)); // 0 % 20 == 0

        // When duration is not a multiple of freq
        assertFalse(effect.shouldApplyEffectTick(10, 0));
        assertFalse(effect.shouldApplyEffectTick(19, 0));
        assertFalse(effect.shouldApplyEffectTick(21, 0));
    }

    private static sun.misc.Unsafe getUnsafe() throws Exception {
        Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return (sun.misc.Unsafe) f.get(null);
    }
}
