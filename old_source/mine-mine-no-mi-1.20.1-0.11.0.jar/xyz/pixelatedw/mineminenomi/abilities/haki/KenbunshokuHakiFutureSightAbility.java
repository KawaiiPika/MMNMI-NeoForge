package xyz.pixelatedw.mineminenomi.abilities.haki;

import java.awt.Color;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityOverlay;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DamageTakenComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.SkinOverlayComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.StackComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceElement;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.data.entity.ability.AbilityCapability;
import xyz.pixelatedw.mineminenomi.data.entity.ability.IAbilityData;
import xyz.pixelatedw.mineminenomi.data.entity.haki.HakiCapability;
import xyz.pixelatedw.mineminenomi.data.entity.haki.IHakiData;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.init.ModSounds;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class KenbunshokuHakiFutureSightAbility extends Ability {
   public static final RegistryObject<AbilityCore<KenbunshokuHakiFutureSightAbility>> INSTANCE = ModRegistry.registerAbility("kenbunshoku_haki_future_sight", "Kenbunshoku Haki: Future Sight", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Using Observation Haki allows the user to see a short period into the future to avoid attacks.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.HAKI, KenbunshokuHakiFutureSightAbility::new)).addDescriptionLine(desc).setUnlockCheck(KenbunshokuHakiFutureSightAbility::canUnlock).build("mineminenomi");
   });
   private static final int MIN_COOLDOWN = 100;
   private static final float PROTECTION_TIME = 10.0F;
   public static final AbilityOverlay OVERLAY;
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addStartEvent(this::startContinuityEvent).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final DamageTakenComponent damageTakenComponent = (new DamageTakenComponent(this)).addOnAttackEvent(this::damageTakenEvent);
   private final SkinOverlayComponent skinOverlayComponent;
   private final PoolComponent poolComponent;
   private final StackComponent stackComponent;
   private final AnimationComponent animationComponent;
   private boolean hasDodged;
   private float protTimer;

   public KenbunshokuHakiFutureSightAbility(AbilityCore<KenbunshokuHakiFutureSightAbility> core) {
      super(core);
      this.skinOverlayComponent = new SkinOverlayComponent(this, OVERLAY, new AbilityOverlay[0]);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.DODGE_ABILITY, new AbilityPool[0]);
      this.stackComponent = (new StackComponent(this)).addStackChangeEvent(this::changeStackEvent);
      this.animationComponent = new AnimationComponent(this);
      this.protTimer = 10.0F;
      this.setOGCD();
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.damageTakenComponent, this.skinOverlayComponent, this.poolComponent, this.stackComponent, this.animationComponent});
      this.addCanUseCheck(AbilityUseConditions::canEnableHaki);
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(this::useEvent);
      this.addEquipEvent(this::equipEvent);
   }

   private void equipEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setDefaultStacks(this.calculateMaxProtection(entity));
      this.stackComponent.revertStacksToDefault(entity, ability);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
   }

   private void changeStackEvent(LivingEntity entity, IAbility ability, int stacks) {
      if (stacks <= 0) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HAKI_GUARD.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         this.continuousComponent.stopContinuity(entity);
      }

   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.stackComponent.setDefaultStacks(this.calculateMaxProtection(entity));
      this.stackComponent.revertStacksToDefault(entity, ability);
      this.protTimer = 0.0F;
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.KENBUNSHOKU_HAKI_ON_SFX.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      boolean isOnMaxOveruse = HakiHelper.checkForHakiOveruse(entity, 2);
      if (isOnMaxOveruse) {
         WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.HAKI_GUARD.get(), entity, entity.m_20185_(), entity.m_20186_(), entity.m_20189_());
         this.continuousComponent.stopContinuity(entity);
      }

      if (this.protTimer > 0.0F) {
         --this.protTimer;
      } else if (this.protTimer <= 0.0F && this.hasDodged) {
         this.skinOverlayComponent.hideAll(entity);
         this.hasDodged = false;
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.KENBUNSHOKU_HAKI_OFF.get(), SoundSource.PLAYERS, 2.0F, 1.0F);
      this.skinOverlayComponent.hideAll(entity);
      float protLost = (float)((this.stackComponent.getDefaultStacks() - this.stackComponent.getStacks()) * 6);
      float cooldown = 100.0F + WyHelper.secondsToTicks(protLost);
      this.cooldownComponent.startCooldown(entity, cooldown);
      this.stackComponent.revertStacksToDefault(entity, this);
   }

   private int calculateMaxProtection(LivingEntity entity) {
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
      if (statsProps != null && hakiProps != null) {
         double dorikiPower = statsProps.getDoriki() / (double)5000.0F;
         double hakiPower = (double)(hakiProps.getKenbunshokuHakiExp() / 12.0F);
         return (int)Math.max(1L, Math.round(dorikiPower + hakiPower));
      } else {
         return 0;
      }
   }

   public float damageTakenEvent(LivingEntity entity, IAbility ability, DamageSource damageSource, float damage) {
      if (super.isContinuous() && !AbilityUseConditions.canUseMomentumAbilities(entity, this).isFail()) {
         IHakiData hakiProps = (IHakiData)HakiCapability.get(entity).orElse((Object)null);
         if (hakiProps == null) {
            return damage;
         } else {
            int hakiOveruse = 10 + hakiProps.getMaxOveruse() / 1180;
            IDamageSourceHandler handler = IDamageSourceHandler.getHandler(damageSource);
            boolean isUnavoidable = handler.isUnavoidable();
            boolean isExplosionOrShockwave = handler.hasElement(SourceElement.EXPLOSION) || handler.hasElement(SourceElement.SHOCKWAVE);
            if (!isUnavoidable && !isExplosionOrShockwave) {
               boolean isDamageTaken = true;
               if (!damageSource.m_276093_(DamageTypes.f_268566_) && !damageSource.m_276093_(DamageTypes.f_268464_) && !damageSource.m_269533_(DamageTypeTags.f_268524_)) {
                  if (handler.hasType(SourceType.BLUNT) || handler.hasType(SourceType.SLASH) || handler.hasType(SourceType.FIST) || handler.hasType(SourceType.PHYSICAL) || handler.hasType(SourceType.PROJECTILE) || handler.hasType(SourceType.INDIRECT) || handler.hasType(SourceType.BULLET)) {
                     isDamageTaken = false;
                  }
               } else {
                  isDamageTaken = false;
               }

               if (this.protTimer <= 0.0F) {
                  if (!isDamageTaken) {
                     this.skinOverlayComponent.showAll(entity);
                     this.animationComponent.start(entity, ModAnimations.DODGE, 5);
                     entity.m_9236_().m_5594_((Player)null, entity.m_20183_(), (SoundEvent)ModSounds.FUTURE_SIGHT_HIT.get(), SoundSource.PLAYERS, 2.0F, 0.75F + entity.m_217043_().m_188501_() / 2.0F);
                     hakiProps.alterHakiOveruse(hakiOveruse * 4 + (int)(damage * (float)hakiOveruse));
                     int stacks = Math.max(1, Math.round(damage / 15.0F));
                     this.stackComponent.addStacks(entity, this, -stacks);
                     this.protTimer = 10.0F;
                     this.hasDodged = true;
                  }

                  return isDamageTaken ? damage : 0.0F;
               } else {
                  return 0.0F;
               }
            } else {
               return damage;
            }
         }
      } else {
         return damage;
      }
   }

   private static boolean canUnlock(LivingEntity user) {
      IAbilityData abilityProps = (IAbilityData)AbilityCapability.get(user).orElse((Object)null);
      IHakiData hakiProps = (IHakiData)HakiCapability.get(user).orElse((Object)null);
      IEntityStats statsProps = (IEntityStats)EntityStatsCapability.get(user).orElse((Object)null);
      if (abilityProps != null && hakiProps != null && statsProps != null) {
         boolean hasAuraUnlocked = abilityProps.hasUnlockedAbility((AbilityCore)KenbunshokuHakiAuraAbility.INSTANCE.get());
         return hasAuraUnlocked && statsProps.getDoriki() > (double)6000.0F && (double)hakiProps.getKenbunshokuHakiExp() > HakiHelper.getKenbunshokuFutureSightExpNeeded(user);
      } else {
         return false;
      }
   }

   public int getProtectionStacks() {
      return this.stackComponent.getStacks();
   }

   public int getMaxProtectionStacks() {
      return this.stackComponent.getDefaultStacks();
   }

   static {
      OVERLAY = (new AbilityOverlay.Builder()).setTexture(ModResources.BUSOSHOKU_HAKI_ARM).setColor(new Color(255, 100, 200, 100)).build();
   }
}
