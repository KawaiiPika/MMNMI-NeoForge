package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;

public class SeismicBubbleEffect extends BaseEffect {
   public SeismicBubbleEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
   }

   public boolean shouldUpdateClient() {
      return true;
   }
}
