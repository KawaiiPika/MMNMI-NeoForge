package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.resources.ResourceLocation;

public class DrunkEffect extends MobEffect {
    public DrunkEffect() {
        super(MobEffectCategory.HARMFUL, 0x8FBC8F);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("mineminenomi", "drunk_speed"), -0.005, AttributeModifier.Operation.ADD_VALUE);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (amplifier >= 2 && amplifier < 4) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
        } else if (amplifier >= 4) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 2));
        }
        return true;
    }
}
