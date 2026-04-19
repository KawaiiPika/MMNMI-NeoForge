package xyz.pixelatedw.mineminenomi.effects;

import java.util.function.Function;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class DamageOverTimeEffect extends BaseEffect {
   private final float baseDamage;
   private final int freq;
   private Function<LivingEntity, DamageSource> damageFunction;
   private Function<Integer, Float> damageScaling;

   public DamageOverTimeEffect() {
      this((entity) -> entity.m_269291_().m_269425_(), 2.0F, 20);
   }

   public DamageOverTimeEffect(Function<LivingEntity, DamageSource> damageFunction, float baseDamage, int freq) {
      this(WyHelper.hexToRGB("#000000").getRGB(), damageFunction, baseDamage, freq);
   }

   public DamageOverTimeEffect(int color, Function<LivingEntity, DamageSource> damageFunction, float baseDamage, int freq) {
      super(MobEffectCategory.HARMFUL, color);
      this.damageFunction = damageFunction;
      this.baseDamage = baseDamage;
      this.freq = freq;
      this.damageScaling = (amp) -> this.baseDamage * (float)amp;
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % this.freq == 1;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      float damage = Math.max(this.baseDamage, (Float)this.damageScaling.apply(amplifier));
      entity.f_19802_ = 0;
      entity.m_6469_((DamageSource)this.damageFunction.apply(entity), damage);
   }

   public float getBaseDamage() {
      return this.baseDamage;
   }

   public Function<Integer, Float> getDamageScaling() {
      return this.damageScaling;
   }

   public void setDamageScaling(Function<Integer, Float> func) {
      this.damageScaling = func;
   }
}
