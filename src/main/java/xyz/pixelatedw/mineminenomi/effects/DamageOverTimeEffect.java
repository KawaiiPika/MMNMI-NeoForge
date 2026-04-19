package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class DamageOverTimeEffect extends MobEffect {
    private final float baseDamage;
    private final int freq;
    private Function<LivingEntity, DamageSource> damageFunction;
    private Function<Integer, Float> damageScaling;

    public DamageOverTimeEffect(int color, Function<LivingEntity, DamageSource> damageFunction, float baseDamage, int freq) {
        super(MobEffectCategory.HARMFUL, color);
        this.damageFunction = damageFunction;
        this.baseDamage = baseDamage;
        this.freq = freq;
        this.damageScaling = (amp) -> this.baseDamage * (float)(amp + 1);
    }

    public boolean shouldApplyEffectTick(int duration, int amplifier) {
        return duration % this.freq == 0;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        float damage = Math.max(this.baseDamage, this.damageScaling.apply(amplifier));
        entity.invulnerableTime = 0;
        entity.hurt(this.damageFunction.apply(entity), damage);
        return true;
    }

    public float getBaseDamage() {
        return this.baseDamage;
    }

    public void setDamageScaling(Function<Integer, Float> func) {
        this.damageScaling = func;
    }
}
