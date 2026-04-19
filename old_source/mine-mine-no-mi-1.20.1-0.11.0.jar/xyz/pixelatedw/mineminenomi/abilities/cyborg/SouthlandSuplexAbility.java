package xyz.pixelatedw.mineminenomi.abilities.cyborg;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityExplosion;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ExplosionComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.EntityStatsCapability;
import xyz.pixelatedw.mineminenomi.data.entity.stats.IEntityStats;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SouthlandSuplexAbility extends Ability {
   private static final int PULL_TIME = 200;
   private static final int CHARGE_TIME = 20;
   private static final int COOLDOWN = 140;
   private static final int DAMAGE = 20;
   private static final int COLLISION_DAMAGE = 10;
   public static final RegistryObject<AbilityCore<SouthlandSuplexAbility>> INSTANCE = ModRegistry.registerAbility("southland_suplex", "Southland Suplex", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("The user grabs a person with both of their arms and smashes the opponent's head by throwing them backwards", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.RACIAL, SouthlandSuplexAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setUnlockCheck(SouthlandSuplexAbility::canUnlock).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onContinuityEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, false, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final ExplosionComponent explosionComponent;

   public SouthlandSuplexAbility(AbilityCore<SouthlandSuplexAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.explosionComponent = new ExplosionComponent(this);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.dealDamageComponent, this.chargeComponent, this.animationComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent, this.explosionComponent});
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
      if (this.continuousComponent.isContinuous() && !this.grabComponent.hasGrabbedEntity()) {
         if (this.grabComponent.grabManually(entity, target)) {
            this.grabComponent.startPulling(entity);
         }

         target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.ANTI_KNOCKBACK.get(), 1));
         return false;
      } else {
         return true;
      }
   }

   public void onPullStart(LivingEntity entity, IAbility ability) {
      this.continuousComponent.setThresholdTime(entity, 200.0F);
   }

   public void onPullEnd(LivingEntity entity, IAbility ability) {
      this.continuousComponent.stopContinuity(entity);
      if (this.grabComponent.canContinueGrab(entity)) {
         this.chargeComponent.startCharging(entity, 20.0F);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 140.0F);
         }

      }
   }

   private void onChargeStart(LivingEntity entity, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
   }

   public void onChargeTick(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
            grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
            float distance = 1.0F;
            Vec3 lookVec = entity.m_20154_().m_82541_();
            Vec3 pos = (new Vec3(lookVec.f_82479_ * (double)distance, (double)entity.m_20206_(), lookVec.f_82481_ * (double)distance)).m_82490_((double)this.chargeComponent.getChargePercentage());
            AbilityHelper.setDeltaMovement(grabbedTarget, entity.m_20182_().m_82492_(pos.f_82479_, (double)(-EasingFunctionHelper.easeInOutSine((float)pos.f_82480_)), pos.f_82481_).m_82546_(grabbedTarget.m_20182_()), true);
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (this.grabComponent.canContinueGrab(entity)) {
         LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
         if (this.dealDamageComponent.hurtTarget(entity, grabbedTarget, 20.0F)) {
            grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0));
         }

         List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(entity.m_20182_(), entity.m_9236_(), (double)grabbedTarget.m_20205_(), (double)grabbedTarget.m_20206_(), (double)grabbedTarget.m_20205_(), ModEntityPredicates.getEnemyFactions(entity));
         targets.remove(grabbedTarget);
         if (!HakiHelper.hasHardeningActive(entity)) {
            targets.removeIf((targetx) -> (Boolean)DevilFruitCapability.get(entity).map((props) -> props.isLogia()).orElse(false));
         }

         for(LivingEntity target : targets) {
            if (this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
               AbilityHelper.setDeltaMovement(target, (double)0.0F, (double)-1.0F, (double)0.0F);
            }
         }

         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, grabbedTarget.m_20185_(), grabbedTarget.m_20186_(), grabbedTarget.m_20189_(), 1.0F);
         explosion.setStaticDamage(6.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(false);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.setDamageEntities(true);
         explosion.m_46061_();
         this.grabComponent.release(entity);
      }

      this.animationComponent.stop(entity);
      this.cooldownComponent.startCooldown(entity, 140.0F);
   }

   private static boolean canUnlock(LivingEntity entity) {
      IEntityStats props = (IEntityStats)EntityStatsCapability.get(entity).orElse((Object)null);
      return props == null ? false : props.isCyborg();
   }
}
