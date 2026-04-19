package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;

public class BubblyCoralEffect extends BaseEffect implements IColorOverlayEffect {
   private static final Color OVERLAY_COLOR = new Color(0.0F, 0.41F, 0.58F, 0.11F);

   public BubblyCoralEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#000000").getRGB());
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public Color getBodyOverlayColor(int duration, int amplifier) {
      return OVERLAY_COLOR;
   }
}
