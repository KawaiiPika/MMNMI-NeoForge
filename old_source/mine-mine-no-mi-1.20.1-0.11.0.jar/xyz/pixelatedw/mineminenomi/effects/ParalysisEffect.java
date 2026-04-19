package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ParalysisEffect extends BaseEffect implements IBindHandsEffect {
   public ParalysisEffect() {
      super(MobEffectCategory.HARMFUL, WyHelper.hexToRGB("#f6fa5c").getRGB());
      this.m_19472_(Attributes.f_22279_, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.1, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "36de9be6-6645-4124-b99d-325163e8c15f", -0.1, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "f132c77e-9f47-42bb-8233-81c89b8ddcab", (double)-1.0F, Operation.ADDITION);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "18240623-27cc-4648-b148-ea3a6782bda6", -0.1, Operation.MULTIPLY_TOTAL);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (amplifier > 0) {
         entity.m_20334_((double)0.0F, (double)0.0F, (double)0.0F);
      }

   }

   public boolean isBlockingSwings() {
      return true;
   }

   public boolean isLingering() {
      return true;
   }
}
