package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class IceOniEffect extends MobEffect {
    public IceOniEffect() {
        super(MobEffectCategory.HARMFUL, 0xADD8E6);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            entity.addEffect(new MobEffectInstance(ModEffects.FROSTBITE, 40, amplifier));
        }
        return true;
    }
}
