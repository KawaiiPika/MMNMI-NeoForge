package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class TensionHormoneEffect extends MobEffect {
    public TensionHormoneEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xDB545C);
        
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "tension_damage"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "tension_speed"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "tension_jump"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "tension_knockback"), 10.0, AttributeModifier.Operation.ADD_VALUE);
    }
}
