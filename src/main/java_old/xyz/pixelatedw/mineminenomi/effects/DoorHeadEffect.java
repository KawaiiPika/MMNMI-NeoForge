package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class DoorHeadEffect extends BaseEffect {
   public DoorHeadEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
   }

   public boolean shouldUpdateClient() {
      return true;
   }
}
