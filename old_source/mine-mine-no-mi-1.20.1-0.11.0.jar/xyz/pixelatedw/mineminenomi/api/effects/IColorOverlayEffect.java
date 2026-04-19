package xyz.pixelatedw.mineminenomi.api.effects;

import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public interface IColorOverlayEffect {
   @Nullable Color getBodyOverlayColor(int var1, int var2);

   default @Nullable Color getViewOverlayColor(int duration, int amplifier) {
      return this.getBodyOverlayColor(duration, amplifier);
   }
}
