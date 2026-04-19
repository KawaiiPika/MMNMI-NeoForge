package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.init.ModEffects;

public class MummyVirusEffect extends MobEffect {
    public MummyVirusEffect() {
        super(MobEffectCategory.HARMFUL, 0xBEB936);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % 10 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            if (!entity.hasEffect(ModEffects.HUNGER)) {
                entity.addEffect(new MobEffectInstance(ModEffects.HUNGER, 20, 0));
            }
            if (!entity.hasEffect(ModEffects.BLEEDING)) {
                entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING, 20, 0));
            }
        }
        return true;
    }
}
