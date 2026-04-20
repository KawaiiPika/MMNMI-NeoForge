package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DokuPoisonEffect extends MobEffect {
    public DokuPoisonEffect() {
        super(MobEffectCategory.HARMFUL, 0x8A2BE2);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        int i = 25 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.getHealth() > 1.0F) {
            entity.hurt(xyz.pixelatedw.mineminenomi.init.ModDamageSources.getInstance().poison(entity), 1.0F);
        }
        return true;
    }
}
