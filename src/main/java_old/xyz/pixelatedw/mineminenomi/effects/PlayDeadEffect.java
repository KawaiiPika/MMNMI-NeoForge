package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class PlayDeadEffect extends BaseEffect implements IBindBodyEffect, IBindHandsEffect {
   public PlayDeadEffect() {
      super(MobEffectCategory.NEUTRAL, 0);
      this.m_19472_(Attributes.f_22279_, "2727e176-e9e8-4523-92f8-22619308d9ee", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "323ffb58-0b57-434e-bdfc-354670e22d5f", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "e8cd65cb-2768-4fd8-aa54-bdcda029aaff", (double)-1.0F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "777f14bf-fe3f-4f19-9d2c-eb69c04d5209", (double)-4.0F, Operation.ADDITION);
      this.m_19472_(Attributes.f_22278_, "f8b2474d-4cdb-42b0-a868-327443a2a505", (double)1.0F, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      int k = 20 >> amplifier;
      if (k > 0) {
         return duration % k == 0;
      } else {
         return true;
      }
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (entity.m_21223_() < entity.m_21233_()) {
         entity.m_5634_(5.0F);
      }

   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean isBlockingRotation() {
      return true;
   }

   public boolean isBlockingSwings() {
      return true;
   }
}
