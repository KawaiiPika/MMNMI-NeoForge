package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DokuPoisonEffectTest {

    @BeforeAll
    public static void setup() {
        // Initialize an empty ModList to bypass NullPointerException in NeoForge FeatureFlagLoader
        // during Minecraft Bootstrap.
        try {
            net.neoforged.fml.loading.LoadingModList.of(
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList(),
                java.util.Collections.emptyList(),
                java.util.Collections.emptyMap()
            );
        } catch (Throwable t) {
            // ignore if already set or fails
        }

        // Bootstrap Minecraft registries so that MobEffect can be instantiated.
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable t) {
            // ignore if already bootstrapped
        }
    }

    @ParameterizedTest(name = "duration={0}, amplifier={1} => expected={2}")
    @CsvSource({
            // Level 1 (amplifier 0): i = 25
            "50, 0, true",
            "25, 0, true",
            "0, 0, true",
            "24, 0, false",
            "10, 0, false",
            "-25, 0, true",
            "-24, 0, false",

            // Level 2 (amplifier 1): i = 12
            "24, 1, true",
            "12, 1, true",
            "0, 1, true",
            "11, 1, false",

            // Level 3 (amplifier 2): i = 6
            "12, 2, true",
            "6, 2, true",
            "0, 2, true",
            "5, 2, false",

            // Level 4 (amplifier 3): i = 3
            "6, 3, true",
            "3, 3, true",
            "0, 3, true",
            "2, 3, false",

            // Level 5 (amplifier 4): i = 1
            "2, 4, true",
            "1, 4, true",
            "0, 4, true",

            // Level 6+ (amplifier 5+): i = 0 (returns true always)
            "10, 5, true",
            "1, 5, true",
            "0, 5, true",
            "10, 6, true"
    })
    public void testShouldApplyEffectTick(int duration, int amplifier, boolean expected) {
        // We can now successfully instantiate the actual DokuPoisonEffect class
        DokuPoisonEffect effect = new DokuPoisonEffect();
        assertEquals(expected, effect.shouldApplyEffectTick(duration, amplifier));
    }
}
