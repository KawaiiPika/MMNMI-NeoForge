package xyz.pixelatedw.mineminenomi.api.helpers;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xyz.pixelatedw.mineminenomi.abilities.LogiaInvulnerabilityAbility;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.GuardAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponentKey;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.effects.GuardingEffect;
import xyz.pixelatedw.mineminenomi.init.ModAbilityComponents;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.init.i18n.ModI18nAbilities;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class CombatHelper {
   public static @Nullable LivingEntity canGrab(LivingEntity entity, boolean sendMessage) {
      double reach = Math.sqrt(AttributeHelper.getSquaredAttackRangeDistance(entity, (double)3.0F));
      return canGrab(entity, (float)reach, 0.4F, sendMessage);
   }

   public static @Nullable LivingEntity canGrab(LivingEntity entity, float distance, float size, boolean sendMessage) {
      Optional<LivingEntity> opt = TargetHelper.getEntitiesInLine(entity, distance, size, (TargetPredicate)null, LivingEntity.class).stream().findFirst();
      if (opt.isPresent()) {
         LivingEntity target = (LivingEntity)opt.get();
         if (target != null && target.m_6084_() && !isTargetBlocking(entity, target)) {
            return target;
         }
      } else if (sendMessage && entity instanceof ServerPlayer) {
         ServerPlayer player = (ServerPlayer)entity;
         player.m_213846_(ModI18nAbilities.MESSAGE_NO_TARGET);
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public static boolean isTargetBlocking(LivingEntity attacker, LivingEntity target) {
      if (isLogiaBlocking(target)) {
         return true;
      } else if (isShieldBlocking(target)) {
         return true;
      } else if (isGuardBlocking(target)) {
         return true;
      } else {
         return isDodging(target);
      }
   }

   public static boolean isHakiBlocking(LivingEntity attacker, LivingEntity target) {
      float attackerHakiExp = (Float)HakiCapability.get(attacker).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
      float targetHakiExp = (Float)HakiCapability.get(target).map((props) -> props.getTotalHakiExp()).orElse(0.0F);
      if (targetHakiExp < 5.0F) {
         return false;
      } else {
         float diff = attackerHakiExp - targetHakiExp;
         if (diff < -5.0F) {
            return true;
         } else if (diff > 5.0F) {
            return false;
         } else {
            float clampedDiff = Mth.m_14036_(diff, 0.0F, 5.0F);
            float mod = clampedDiff / 400.0F * -1.0F;
            float chance = 0.95F + mod;
            float rand = target.m_217043_().m_188501_();
            if (rand < chance) {
               attacker.m_9236_().m_5594_((Player)null, attacker.m_20183_(), (SoundEvent)ModSounds.HAKI_GUARD.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HAKI_GUARD.get(), target, target.m_20185_(), target.m_20186_(), target.m_20189_());
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static boolean isLogiaBlocking(LivingEntity target) {
      MobEffectInstance inst = target.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
      if (inst != null && inst.m_19564_() > 0) {
         return false;
      } else if (!target.m_21023_((MobEffect)ModEffects.KAIROSEKI_WEAKNESS.get()) && !target.m_21023_((MobEffect)ModEffects.WATER_WEAKNESS.get())) {
         IAbilityData props = (IAbilityData)AbilityCapability.get(target).orElse((Object)null);
         if (props == null) {
            return false;
         } else {
            Stream var10000 = props.getPassiveAbilities(AbilityCategory.DEVIL_FRUITS.isAbilityPartofCategory()).stream();
            Objects.requireNonNull(LogiaInvulnerabilityAbility.class);
            var10000 = var10000.filter(LogiaInvulnerabilityAbility.class::isInstance).limit(1L);
            Objects.requireNonNull(LogiaInvulnerabilityAbility.class);
            Optional<LogiaInvulnerabilityAbility> logiaAbility = var10000.map(LogiaInvulnerabilityAbility.class::cast).filter((a) -> !a.isPaused()).findFirst();
            boolean hasLogiaDefence = logiaAbility.isPresent();
            return hasLogiaDefence;
         }
      } else {
         return false;
      }
   }

   public static boolean isShieldBlocking(LivingEntity target) {
      MobEffectInstance inst = target.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
      if (inst != null && inst.m_19564_() > 0) {
         return false;
      } else {
         boolean hasShield = target.m_21254_();
         if (hasShield) {
            target.m_5496_(SoundEvents.f_12346_, 1.0F, 0.8F + target.m_9236_().f_46441_.m_188501_() * 0.4F);
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean isGuardBlocking(LivingEntity target) {
      MobEffectInstance inst = target.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
      if (inst != null && inst.m_19564_() > 0) {
         return false;
      } else {
         boolean hasLegacyGuardEffect = target.m_21220_().stream().anyMatch((instance) -> instance.m_19544_() instanceof GuardingEffect);
         boolean hasGuardActive = (Boolean)AbilityCapability.get(target).map((props) -> props.getEquippedAbilities((abl) -> abl instanceof GuardAbility).stream().anyMatch((abl) -> abl.isContinuous())).orElse(false);
         if (!hasLegacyGuardEffect && !hasGuardActive) {
            return false;
         } else {
            target.m_9236_().m_5594_((Player)null, target.m_20183_(), (SoundEvent)ModSounds.GUARD.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
            WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.GUARD.get(), target, target.m_20185_(), target.m_20186_(), target.m_20189_());
            return true;
         }
      }
   }

   public static boolean isDodging(LivingEntity entity) {
      MobEffectInstance inst = entity.m_21124_((MobEffect)ModEffects.UNCONSCIOUS.get());
      if (inst != null && inst.m_19564_() > 0) {
         return false;
      } else {
         IAbilityData abilityData = (IAbilityData)AbilityCapability.get(entity).orElse((Object)null);
         if (abilityData == null) {
            return false;
         } else {
            boolean isDodgingAbilityActive = abilityData.getEquippedAbilitiesInPool(ModAbilityPools.DODGE_ABILITY).filter((ability) -> ability.hasComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())).map((ability) -> ability.getComponent((AbilityComponentKey)ModAbilityComponents.CONTINUOUS.get())).filter(Optional::isPresent).anyMatch((comp) -> ((ContinuousComponent)comp.get()).isContinuous());
            return isDodgingAbilityActive;
         }
      }
   }
}
