package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;

public class AntidoteEffect extends MobEffect {
    public AntidoteEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x36BE4E);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide()) {
            Collection<MobEffectInstance> effects = entity.getActiveEffects();
            Collection<net.minecraft.core.Holder<MobEffect>> toRemove = new ArrayList<>();

            for (MobEffectInstance inst : effects) {
                if (this.isImmuneTo(inst.getEffect().value())) {
                    toRemove.add(inst.getEffect());
                }
            }

            for (var eff : toRemove) {
                entity.removeEffect(eff);
            }
        }
        return true;
    }

    private boolean isImmuneTo(MobEffect effect) {
        return effect == MobEffects.POISON || effect == MobEffects.WITHER || effect == MobEffects.BLINDNESS || effect == MobEffects.CONFUSION;
    }
}
