package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class FatigueEffect extends MobEffect {
    public FatigueEffect() {
        super(MobEffectCategory.HARMFUL, 0);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "fatigue_speed"), -0.01, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "fatigue_damage"), -0.1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "fatigue_attack_speed"), -1.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.REGEN_RATE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "fatigue_regen"), -0.1, AttributeModifier.Operation.ADD_VALUE);
    }
}
