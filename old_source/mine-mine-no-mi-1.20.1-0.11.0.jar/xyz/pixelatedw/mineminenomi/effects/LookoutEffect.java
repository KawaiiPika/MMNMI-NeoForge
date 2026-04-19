package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import net.minecraft.world.effect.MobEffectCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;

public class LookoutEffect extends BaseEffect implements IColorOverlayEffect {
   private static final Color OVERLAY_COLOR = new Color(0.97F, 0.8F, 1.0F, 0.6F);

   public LookoutEffect() {
      super(MobEffectCategory.NEUTRAL, OVERLAY_COLOR.getRGB());
   }

   public boolean shouldUpdateClient() {
      return false;
   }

   public Color getBodyOverlayColor(int duration, int amplifier) {
      return OVERLAY_COLOR;
   }
}
