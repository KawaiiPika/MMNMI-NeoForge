package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SilentEffect extends BaseEffect {
   public SilentEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
   }

   public void startEffect(LivingEntity entity, MobEffectInstance instance) {
      entity.m_20225_(true);
   }

   public void stopEffect(LivingEntity entity) {
      entity.m_20225_(false);
   }

   public boolean shouldUpdateClient() {
      return true;
   }
}
