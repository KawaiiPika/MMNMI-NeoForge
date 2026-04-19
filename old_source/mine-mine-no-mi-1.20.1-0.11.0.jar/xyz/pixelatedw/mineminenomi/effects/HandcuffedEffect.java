package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.effects.IBindHandsEffect;
import xyz.pixelatedw.mineminenomi.api.enums.HandcuffType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;

public class HandcuffedEffect extends BaseEffect implements IBindHandsEffect {
   private HandcuffType type;

   public HandcuffedEffect(HandcuffType type) {
      super(MobEffectCategory.HARMFUL, 0);
      this.type = type;
      this.m_19472_(Attributes.f_22279_, "b8154269-30c6-429f-9a5f-1fd7fdafc0cd", (double)-0.3F, Operation.MULTIPLY_TOTAL);
      this.m_19472_(Attributes.f_22283_, "caced4c2-b03a-4f70-8fb3-4f702daa0a80", (double)-0.1F, Operation.MULTIPLY_TOTAL);
      this.m_19472_((Attribute)ModAttributes.MINING_SPEED.get(), "bd141f77-4c9d-41ef-aceb-96601c21dccb", (double)-0.1F, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public boolean m_6584_(int duration, int amplifier) {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      MobEffectInstance instance = entity.m_21124_(this);
      if (instance.m_19557_() <= 1) {
         entity.m_21195_(this);
         AbilityHelper.enableAbilities(entity, (ability) -> ability != null && ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
      }

      if (this.type == HandcuffType.KAIROSEKI) {
         AbilityHelper.disableAbilities(entity, instance.m_19557_(), (ability) -> ability != null && ability.getCore().getCategory() == AbilityCategory.DEVIL_FRUITS);
      }

   }

   public boolean isBlockingSwings() {
      return true;
   }

   public HandcuffType getType() {
      return this.type;
   }
}
