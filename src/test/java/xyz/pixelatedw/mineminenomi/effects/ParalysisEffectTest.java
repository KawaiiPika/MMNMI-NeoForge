package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.mockito.Mockito;

class ParalysisEffectTest {

    @BeforeAll
    static void setup() throws Exception {
        SharedConstants.tryDetectVersion();

        try {
            Class<?> loadingModListClass = Class.forName("net.neoforged.fml.loading.LoadingModList");
            Object mockList = Mockito.mock(loadingModListClass);
            Mockito.when(loadingModListClass.getMethod("getModFiles").invoke(mockList)).thenReturn(Collections.emptyList());

            Field instanceField = loadingModListClass.getDeclaredField("INSTANCE");
            instanceField.setAccessible(true);
            instanceField.set(null, mockList);
        } catch (Throwable e) {}

        Bootstrap.bootStrap();
    }

    @Test
    void shouldApplyEffectTick_alwaysReturnsTrue() {
        ParalysisEffect paralysisEffect = new ParalysisEffect();

        boolean result = paralysisEffect.shouldApplyEffectTick(10, 0);
        assertTrue(result, "shouldApplyEffectTick should always return true");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 0",
            "100, 1",
            "0, 0",
            "-1, -1",
            "9999, 5"
    })
    void shouldApplyEffectTick_withVariousDurationsAndAmplifiers_alwaysReturnsTrue(int duration, int amplifier) {
        ParalysisEffect paralysisEffect = new ParalysisEffect();

        boolean result = paralysisEffect.shouldApplyEffectTick(duration, amplifier);
        assertTrue(result, "shouldApplyEffectTick should return true for duration " + duration + " and amplifier " + amplifier);
    }
}
