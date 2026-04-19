package xyz.pixelatedw.mineminenomi.effects;

import java.awt.Color;
import xyz.pixelatedw.mineminenomi.api.effects.IColorOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ILinkedEffect;

public class DoaVanishEffect extends VanishEffect implements ILinkedEffect, IColorOverlayEffect {
   private static final Color OVERLAY_COLOR = new Color(0.2F, 0.68F, 0.53F, 0.5F);

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean disableParticles() {
      return true;
   }

   public Color getBodyOverlayColor(int duration, int amplifier) {
      return OVERLAY_COLOR;
   }
}
