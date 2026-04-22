package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.AbstractMinecraftTest;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TensionHormoneEffectTest extends AbstractMinecraftTest {

    @Test
    void testConstructorAddsModifiers() throws Exception {
        TensionHormoneEffect effect = new TensionHormoneEffect();

        Field attributeModifiersField = null;
        for (Field field : MobEffect.class.getDeclaredFields()) {
            if (Map.class.isAssignableFrom(field.getType())) {
                attributeModifiersField = field;
                break;
            }
        }

        if (attributeModifiersField == null) {
            fail("Could not find attribute modifiers map field in MobEffect");
        }

        attributeModifiersField.setAccessible(true);
        Map<?, ?> modifiers = (Map<?, ?>) attributeModifiersField.get(effect);

        assertNotNull(modifiers, "Modifiers map should not be null");
        assertEquals(4, modifiers.size(), "Should have 4 attribute modifiers");

        // Use reflection to check keys to avoid Holder vs Attribute issues if we don't know the exact type
        boolean hasAttackDamage = false;
        boolean hasMovementSpeed = false;
        boolean hasJumpHeight = false;
        boolean hasKnockbackResistance = false;

        for (Object key : modifiers.keySet()) {
            String s = key.toString();
            if (s.contains("attack_damage")) hasAttackDamage = true;
            if (s.contains("movement_speed")) hasMovementSpeed = true;
            if (s.contains("jump_height")) hasJumpHeight = true;
            if (s.contains("knockback_resistance")) hasKnockbackResistance = true;
        }

        assertTrue(hasAttackDamage, "Should contain attack_damage modifier");
        assertTrue(hasMovementSpeed, "Should contain movement_speed modifier");
        assertTrue(hasJumpHeight, "Should contain jump_height modifier");
        assertTrue(hasKnockbackResistance, "Should contain knockback_resistance modifier");
    }
}
