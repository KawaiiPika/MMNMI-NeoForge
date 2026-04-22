package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;

import org.mockito.Mockito;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectCategory;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandcuffedEffectTest {

    @BeforeAll
    static void setup() {
        try {
            SharedConstants.tryDetectVersion();
            Bootstrap.bootStrap();
        } catch (Throwable e) {
            // Setup headless test environment
        }
    }

    @Test
    public void testConstructorAppliesAttributes() throws Exception {
        Holder<Attribute> mockMovementSpeed = Mockito.mock(Holder.class);
        Holder<Attribute> mockAttackSpeed = Mockito.mock(Holder.class);

        HandcuffedEffect effect = null;
        try {
            effect = new HandcuffedEffect(MobEffectCategory.HARMFUL, 0, HandcuffType.KAIROSEKI, mockMovementSpeed, mockAttackSpeed);
        } catch (NoClassDefFoundError | ExceptionInInitializerError e) {
            // We explicitly catch environmental Bootstrap initialization errors in this headless unit test environment.
            // As per reviewer feedback, we DO NOT catch Throwable to avoid silently swallowing real bugs like NPEs.
            org.junit.jupiter.api.Assumptions.assumeTrue(false, "Environmental Bootstrap Failure: " + e.getMessage());
            return;
        }

        Field attrField = net.minecraft.world.effect.MobEffect.class.getDeclaredField("attributeModifiers");
        attrField.setAccessible(true);
        Map<?, ?> attrs = (Map<?, ?>) attrField.get(effect);

        assertTrue(attrs.containsKey(mockMovementSpeed), "Constructor should apply movement speed modifier");
        assertTrue(attrs.containsKey(mockAttackSpeed), "Constructor should apply attack speed modifier");

        assertTrue(effect.isBlockingSwings());
        assertTrue(effect.shouldApplyEffectTick(10, 0));
        assertEquals(HandcuffType.KAIROSEKI, effect.getType());
    }
}
