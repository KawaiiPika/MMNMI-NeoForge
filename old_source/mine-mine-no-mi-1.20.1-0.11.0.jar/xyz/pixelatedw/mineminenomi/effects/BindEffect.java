package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;

public class BindEffect extends BaseEffect implements IBindHandsEffect {
   public BindEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingSwings() {
      return true;
   }
}
