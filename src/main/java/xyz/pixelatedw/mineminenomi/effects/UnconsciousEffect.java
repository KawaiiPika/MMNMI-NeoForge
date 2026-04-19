package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class UnconsciousEffect extends MobEffect {
    public UnconsciousEffect() {
        super(MobEffectCategory.HARMFUL, 0x000000);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unconscious_speed"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unconscious_swim"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unconscious_jump"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unconscious_damage"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(ModAttributes.MINING_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "unconscious_mining"), -1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}
