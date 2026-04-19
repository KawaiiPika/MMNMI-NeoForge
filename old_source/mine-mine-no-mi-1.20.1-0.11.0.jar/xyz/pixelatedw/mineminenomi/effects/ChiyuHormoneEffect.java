package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class ChiyuHormoneEffect extends BaseEffect {
   public ChiyuHormoneEffect() {
      super(MobEffectCategory.BENEFICIAL, WyHelper.hexToRGB("#cf90b9").getRGB());
      this.m_19472_((Attribute)ModAttributes.REGEN_RATE.get(), "5548045f-4680-4bc3-9278-5521ef1c2d00", (double)2.0F, Operation.ADDITION);
   }

   public boolean m_6584_(int duration, int amplifier) {
      return duration % 10 == 0;
   }

   public void m_6742_(LivingEntity entity, int amp) {
      if (entity.m_21223_() < entity.m_21233_()) {
         entity.m_5634_(1.0F);
      }

   }

   public void stopEffect(LivingEntity entity) {
      entity.m_7292_(new MobEffectInstance(MobEffects.f_19612_, 200, 1));
   }

   public boolean isLingering() {
      return true;
   }
}
