package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class GanmenSeichoHormoneEffect extends BaseEffect {
   public GanmenSeichoHormoneEffect() {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#968fb1").getRGB());
      this.m_19472_(Attributes.f_22279_, "37360b52-ad8e-4c8a-a5a1-fd35a5e98d92", -0.2, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isLingering() {
      return true;
   }
}
