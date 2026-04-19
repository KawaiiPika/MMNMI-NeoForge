package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class DrunkEffect extends BaseEffect {
   public DrunkEffect() {
      super(MobEffectCategory.HARMFUL, 0);
      this.m_19472_(Attributes.f_22279_, "51b6c31a-cc9a-42d9-aa4c-d4fd98dcef2a", -0.005, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      if (amplifier >= 2 && amplifier < 4) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 100, 0));
      } else if (amplifier >= 4) {
         entity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 100, 2));
      }

   }
}
