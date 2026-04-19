package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ParalysisEffect extends MobEffect {
    public ParalysisEffect() {
        super(MobEffectCategory.HARMFUL, 0xF6FA5C);
        
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "paralysis_speed"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(net.neoforged.neoforge.common.NeoForgeMod.SWIM_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "paralysis_swim"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "paralysis_damage"), -1.0, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(ModAttributes.JUMP_HEIGHT, ResourceLocation.fromNamespaceAndPath("mineminenomi", "paralysis_jump"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (amplifier > 0) {
            entity.setDeltaMovement(0, 0, 0);
        }
        return true;
    }
}
