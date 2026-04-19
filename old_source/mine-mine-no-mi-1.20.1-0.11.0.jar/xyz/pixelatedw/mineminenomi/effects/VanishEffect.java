package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.effects.IVanishEffect;

public class VanishEffect extends BaseEffect implements IVanishEffect {
   public VanishEffect() {
      super(MobEffectCategory.NEUTRAL, 8356754);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isVanished(LivingEntity entity, int duration, int amplifier) {
      return true;
   }

   public boolean disableParticles() {
      return false;
   }
}
