package xyz.pixelatedw.mineminenomi.abilities.suna;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.BonusOperation;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class DesertEncierroAbility extends Ability {
   private static final float COOLDOWN_BONUS = 0.8F;
   private static final int COOLDOWN = 300;
   private static final int CHARGE_TIME = 100;
   private static final int PULL_TIME = 200;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<DesertEncierroAbility>> INSTANCE = ModRegistry.registerAbility("desert_encierro", "Desert Encierro", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Quickly drains the enemy in front of the user of their moisture, leaving them weak for a few seconds.", (Object)null), ImmutablePair.of("While in a desert the cooldown of this ability is reduced by %s.", new Object[]{"§a" + Math.round(19.999998F) + "%§r"}));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, DesertEncierroAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ChargeComponent.getTooltip(100.0F), DealDamageComponent.getTooltip(20.0F)).setSourceType(SourceType.INTERNAL).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onContinuityEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, true, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final Interval particleInterval;

   public DesertEncierroAbility(AbilityCore<DesertEncierroAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.particleInterval = new Interval(6);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.chargeComponent, this.animationComponent, this.continuousComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (!this.chargeComponent.isCharging()) {
         if (this.continuousComponent.isContinuous()) {
            this.grabComponent.release(entity);
            this.continuousComponent.stopContinuity(entity);
         } else if (this.grabComponent.getState() == GrabEntityComponent.GrabState.IDLE && this.grabComponent.grabNearest(entity, false)) {
            this.grabComponent.triggerPulling(entity);
         } else {
            this.continuousComponent.startContinuity(entity);
         }

      }
   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      return this.continuousComponent.isContinuous() && !this.grabComponent.hasGrabbedEntity() ? HitTriggerComponent.HitResult.HIT : HitTriggerComponent.HitResult.PASS;
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.grabComponent.grabManually(entity, target)) {
         this.grabComponent.startPulling(entity);
      }

      target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANTI_KNOCKBACK.get(), 1));
      return false;
   }

   public void onPullStart(LivingEntity entity, IAbility ability) {
      this.continuousComponent.setThresholdTime(entity, 200.0F);
   }

   public void onPullEnd(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      if (this.grabComponent.canContinueGrab(entity)) {
         this.chargeComponent.startCharging(entity, 100.0F);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 300.0F);
         }

      }
   }

   public void onChargeStart(LivingEntity entity, IAbility ability) {
      SunaHelper.drainLiquids(this.grabComponent.getGrabbedEntity(), (int)WyHelper.randomWithRange(0, 1), (int)WyHelper.randomWithRange(1, 3), (int)WyHelper.randomWithRange(0, 1));
      this.animationComponent.start(entity, ModAnimations.POINT_RIGHT_ARM);
      this.particleInterval.restartIntervalToZero();
   }

   public void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            if (this.particleInterval.canTick()) {
               WyHelper.spawnParticleEffect((ParticleEffect)ModParticleEffects.DESERT_ENCIERRO.get(), entity, target.m_20185_(), target.m_20186_(), target.m_20189_());
            }

            DamageSource source = this.dealDamageComponent.getDamageSource(entity);
            IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
            sourceHandler.bypassLogia();
            sourceHandler.addAbilityPiercing(1.0F, this.getCore());
            this.dealDamageComponent.hurtTarget(entity, target, 2.0F, source);
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (this.grabComponent.canContinueGrab(entity)) {
         LivingEntity target = this.grabComponent.getGrabbedEntity();
         DamageSource source = this.dealDamageComponent.getDamageSource(entity);
         IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
         sourceHandler.bypassLogia();
         sourceHandler.addAbilityPiercing(1.0F, this.getCore());
         if (this.dealDamageComponent.hurtTarget(entity, target, 20.0F, source)) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DEHYDRATION.get(), 300, 2, false, true));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 300, 1, false, false));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 300, 1, false, false));
            target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 300, 1, false, false));
         }

         this.grabComponent.release(entity);
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.getBonusManager().removeBonus(SunaHelper.DESERT_COOLDOWN_BONUS);
      if (SunaHelper.isFruitBoosted(entity)) {
         this.cooldownComponent.getBonusManager().addBonus(SunaHelper.DESERT_COOLDOWN_BONUS, "Desert Cooldown Bonus", BonusOperation.MUL, 0.8F);
      }

      this.cooldownComponent.startCooldown(entity, 300.0F);
   }
}
