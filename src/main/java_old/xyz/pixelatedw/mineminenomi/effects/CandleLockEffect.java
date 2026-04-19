package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IWeakenedByHaoshokuEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class CandleLockEffect extends BaseEffect implements IWeakenedByHaoshokuEffect {
   public CandleLockEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#000000").getRGB());
      this.m_19472_(Attributes.f_22279_, "30ee2305-2aaa-4ed8-9c7b-de0ae33a2024", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22278_, "0d80c985-ee10-49c9-bc0b-d9eb0cdb9666", (double)1.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "b94c7ca4-ead0-4927-b4c1-51033159a9ff", (double)-1.0F, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isWeakenedByHaoshoku() {
      return true;
   }
}
