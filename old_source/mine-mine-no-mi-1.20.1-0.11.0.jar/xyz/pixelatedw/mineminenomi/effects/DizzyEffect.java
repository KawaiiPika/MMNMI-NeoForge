package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBreakableEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class DizzyEffect extends BaseEffect implements IBreakableEffect {
   public DizzyEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
      this.m_19472_(Attributes.f_22279_, "75bc5540-523d-43e6-b72a-f5fe9fb2f407", -0.025, Operation.ADDITION).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "e6a25b36-894a-4051-961d-6de8869b2900", (double)-1.0F, Operation.MULTIPLY_TOTAL);
   }

   public float getBreakingMultiplier() {
      return 1.0F;
   }
}
