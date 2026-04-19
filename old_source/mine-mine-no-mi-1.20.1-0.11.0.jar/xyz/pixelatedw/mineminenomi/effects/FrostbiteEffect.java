package xyz.pixelatedw.mineminenomi.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.effects.IBindBodyEffect;
import xyz.pixelatedw.mineminenomi.api.effects.ITextureOverlayEffect;
import xyz.pixelatedw.mineminenomi.api.helpers.EffectsHelper;
import xyz.pixelatedw.mineminenomi.init.ModAttributes;
import xyz.pixelatedw.mineminenomi.init.ModDamageSources;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class FrostbiteEffect extends DamageOverTimeEffect implements ITextureOverlayEffect, IBindBodyEffect {
   private boolean autoIncrease;

   public FrostbiteEffect() {
      this(false);
   }

   public FrostbiteEffect(boolean autoIncrease) {
      super((entity) -> ModDamageSources.getInstance().frostbites(), 1.0F, 40);
      this.autoIncrease = autoIncrease;
      this.m_19472_(Attributes.f_22279_, "11147784-f615-47da-a28c-20c721cf5e9f", (double)-0.04F, Operation.MULTIPLY_TOTAL).m_19472_((Attribute)ModAttributes.JUMP_HEIGHT.get(), "6bd1439e-f380-47a6-806b-6864093a1c32", (double)-0.05F, Operation.MULTIPLY_TOTAL).m_19472_((Attribute)ForgeMod.SWIM_SPEED.get(), "f84aa019-b1b1-496e-a03a-249eaafe039e", (double)-0.05F, Operation.MULTIPLY_TOTAL).m_19472_(Attributes.f_22283_, "d23c3332-a0bf-4dde-80e1-1a6b936caf41", (double)-0.04F, Operation.MULTIPLY_TOTAL);
   }

   public boolean shouldUpdateClient() {
      return true;
   }

   public void m_6742_(LivingEntity entity, int amplifier) {
      super.m_6742_(entity, amplifier);
      MobEffectInstance instance = entity.m_21124_(this);
      int duration = instance.m_19557_();
      int amplifierAmount = instance.m_19564_();
      if (this.autoIncrease && duration % 120 == 0) {
         ((IMobEffectInstanceMixin)instance).setAmplifier(amplifierAmount + 1);
         WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 100, instance);
      }

      if (amplifier > 10) {
         MobEffectInstance forstbite = entity.m_21124_((MobEffect)ModEffects.FROSTBITE.get());
         if (forstbite != null) {
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.FROZEN.get(), duration, 0));
         }

         entity.m_21195_((MobEffect)ModEffects.FROSTBITE.get());
      }

   }

   public boolean isBlockingRotation() {
      return false;
   }

   public ResourceLocation getBodyTexture(int duration, int amplifier) {
      if (amplifier < 2) {
         return ModResources.FROSTBITE_1;
      } else if (amplifier >= 2 && amplifier < 4) {
         return ModResources.FROSTBITE_2;
      } else if (amplifier >= 4 && amplifier < 6) {
         return ModResources.FROSTBITE_3;
      } else {
         return amplifier >= 6 && amplifier < 8 ? ModResources.FROSTBITE_4 : ModResources.FROSTBITE_5;
      }
   }

   public static void addFrostbiteStacks(LivingEntity target, int stacks) {
      if (target != null) {
         if (stacks <= 0) {
            stacks = 1;
         }

         MobEffectInstance frostbite = target.m_21124_((MobEffect)ModEffects.FROSTBITE.get());
         if (frostbite == null) {
            frostbite = new MobEffectInstance((MobEffect)ModEffects.FROSTBITE.get(), 20 * stacks, stacks);
            target.m_7292_(frostbite);
         } else {
            EffectsHelper.updateEffect(target, frostbite, frostbite.m_19557_() + 20 * stacks, frostbite.m_19564_() + stacks);
         }

      }
   }
}
