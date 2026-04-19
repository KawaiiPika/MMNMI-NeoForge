package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;

public class NoHandsEffect extends BaseEffect implements IBindHandsEffect {
   public NoHandsEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public boolean isBlockingSwings() {
      return true;
   }
}
