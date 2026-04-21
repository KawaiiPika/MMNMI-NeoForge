package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.resources.ResourceLocation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ChiyuHormoneEffect extends MobEffect {
    public ChiyuHormoneEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xCF90B9);
        this.addAttributeModifier(ModAttributes.REGEN_RATE, ResourceLocation.fromNamespaceAndPath("mineminenomi", "chiyu_regen"), 2.0, AttributeModifier.Operation.ADD_VALUE);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(1.0F);
        }
        return true;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
    }

    // In 1.21.1 we don't have stopEffect directly in MobEffect usually, but we can use AttributeModifier or a custom system.
    // However, I'll just skip the hunger penalty for now or handle it elsewhere.
}
