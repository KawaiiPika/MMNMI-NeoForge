package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class FatigueEffect extends BaseEffect {
   public FatigueEffect() {
      super(MobEffectCategory.HARMFUL, 0);
      this.m_19472_(Attributes.f_22279_, "75bc5540-523d-43e6-b72a-f5fe9fb2f407", -0.01, Operation.ADDITION).m_19472_(Attributes.f_22283_, "75bc5540-523d-43e6-b72a-f5fe9fb2f407", -0.1, Operation.ADDITION).m_19472_(Attributes.f_22281_, "75bc5540-523d-43e6-b72a-f5fe9fb2f407", (double)-1.0F, Operation.ADDITION).m_19472_((Attribute)ModAttributes.REGEN_RATE.get(), "e6a25b36-894a-4051-961d-6de8869b2900", -0.1, Operation.ADDITION);
   }
}
