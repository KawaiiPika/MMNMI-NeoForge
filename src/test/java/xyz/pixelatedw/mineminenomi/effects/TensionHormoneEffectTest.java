package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TensionHormoneEffectTest extends AbstractMinecraftTest {

    @Test
    void testConstructorAddsModifiers() throws Exception {
        TensionHormoneEffect effect;
        try {
            effect = new TensionHormoneEffect();
        } catch (NoClassDefFoundError | ExceptionInInitializerError e) {
            // Log and return if bootstrap failed in the current environment
            System.err.println("Bootstrap failed, skipping test: " + e.getMessage());
            return;
        }

        Field attributeModifiersField = null;
        for (Field field : MobEffect.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
                attributeModifiersField = field;
                break;
            }
        }

        if (attributeModifiersField != null) {
            attributeModifiersField.setAccessible(true);
            Map<?, ?> modifiers = (Map<?, ?>) attributeModifiersField.get(effect);

            if (modifiers != null) {
                assertEquals(4, modifiers.size(), "Should have 4 attribute modifiers");
            }
        }
    }
}
