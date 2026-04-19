package xyz.pixelatedw.mineminenomi.effects;

import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;

public class TokudaiCandlePoisonEffect extends DamageOverTimeEffect {
   public TokudaiCandlePoisonEffect() {
      super(WyHelper.hexToRGB("#bed4ce").getRGB(), (entity) -> ModDamageSources.getInstance().poison(), 10.0F, 30);
      this.setDamageScaling((amp) -> {
         if (amp <= 0) {
            amp = 1;
         }

         return this.getBaseDamage() * (float)amp;
      });
   }

   public boolean isLingering() {
      return true;
   }
}
