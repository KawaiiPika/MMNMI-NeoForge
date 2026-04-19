package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;

public class AntiKnockbackEffect extends BaseEffect {
   public AntiKnockbackEffect() {
      super(MobEffectCategory.NEUTRAL, WyHelper.hexToRGB("#000000").getRGB());
      super.m_19472_(Attributes.f_22278_, "37efb3b2-edb3-49de-8fb5-cbd49f26e3d1", (double)1.0F, Operation.ADDITION);
   }
}
