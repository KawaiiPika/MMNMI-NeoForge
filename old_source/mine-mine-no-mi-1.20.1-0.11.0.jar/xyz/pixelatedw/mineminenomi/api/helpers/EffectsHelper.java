package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Optional;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.AbilityMobEffectInstance;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.mixins.ILivingEntityMixin;
import xyz.pixelatedw.mineminenomi.mixins.IMobEffectInstanceMixin;

public class EffectsHelper {
   public static void removeAbilityEffect(LivingEntity entity, AbilityMobEffectInstance instance) {
      entity.m_21220_().remove(instance);
      ((ILivingEntityMixin)entity).invokeOnEffectRemoved(instance);
   }

   public static void removeAbilityEffect(LivingEntity entity, MobEffect effect, IAbility ability) {
      for(MobEffectInstance inst : entity.m_21220_()) {
         if (inst != null && inst.m_19544_().equals(effect) && inst instanceof AbilityMobEffectInstance abilityEffect) {
            if (abilityEffect.getParent().equals(ability)) {
               removeAbilityEffect(entity, abilityEffect);
            }
         }
      }

   }

   public static boolean hasEffectOfType(LivingEntity entity, Class<? extends MobEffect> clz) {
      return getEffectOfType(entity, clz).isPresent();
   }

   public static Optional<MobEffectInstance> getEffectOfType(LivingEntity entity, Class<? extends MobEffect> clz) {
      for(MobEffectInstance inst : entity.m_21220_()) {
         if (inst != null && inst.m_19544_().getClass() == clz) {
            return Optional.of(inst);
         }
      }

      return Optional.empty();
   }

   public static void updateEffectDuration(LivingEntity entity, MobEffectInstance inst, int newDuration) {
      ((IMobEffectInstanceMixin)inst).setDuration(newDuration);
      if (entity.m_20194_() != null) {
         WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 128, inst);
      }

   }

   public static void updateEffect(LivingEntity entity, MobEffectInstance inst, int newDuration, int newAmplifier) {
      ((IMobEffectInstanceMixin)inst).setDuration(newDuration);
      ((IMobEffectInstanceMixin)inst).setAmplifier(newAmplifier);
      if (entity.m_20194_() != null) {
         WyHelper.sendApplyEffectToAllNearby(entity, entity.m_20182_(), 128, inst);
      }

   }
}
