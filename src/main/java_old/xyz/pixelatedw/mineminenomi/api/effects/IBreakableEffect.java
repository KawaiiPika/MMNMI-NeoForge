package xyz.pixelatedw.mineminenomi.api.effects;

public interface IBreakableEffect {
   default float getBreakingMultiplier() {
      return 2.0F;
   }
}
