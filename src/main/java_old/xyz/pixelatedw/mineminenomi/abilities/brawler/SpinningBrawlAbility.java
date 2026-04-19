package xyz.pixelatedw.mineminenomi.abilities.brawler;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.abilities.haki.HakiHelper;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.AbilityNode;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.NodeUnlockAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.actions.UnlockAbilityAction;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.NodeUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.abilities.nodes.conditions.RequiredStyleUnlockCondition;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.entities.charactercreator.FightingStyle;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.data.entity.devilfruit.DevilFruitCapability;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModEntityPredicates;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class SpinningBrawlAbility extends Ability {
   private static final double THROW_POWER_XZ = (double)2.0F;
   private static final double THROW_POWER_Y = (double)0.5F;
   private static final int SPIN_DAMAGE = 10;
   private static final int MAIN_DAMAGE = 30;
   private static final int COOLDOWN = 240;
   private static final int CHARGE_TIME = 60;
   private static final int THROW_TIME = 40;
   private static final int PULL_TIME = 200;
   public static final RegistryObject<AbilityCore<SpinningBrawlAbility>> INSTANCE = ModRegistry.registerAbility("spinning_brawl", "Spinning Brawl", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Grabs a nearby enemy spinning them around damaging any nearby entity it touches, and ending by throwing the grabbed entity a few blocks away.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, SpinningBrawlAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(240.0F), ChargeComponent.getTooltip(60.0F), DealDamageComponent.getTooltip(30.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(SpinningBrawlAbility::createNode).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::onContinuityTick).addEndEvent(this::onContinuityEnd);
   private final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, false, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final Interval clearHitsInterval;

   public SpinningBrawlAbility(AbilityCore<SpinningBrawlAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.clearHitsInterval = new Interval(20);
      super.addComponents(this.dealDamageComponent, this.chargeComponent, this.continuousComponent, this.hitTrackerComponent, this.animationComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent);
      super.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      super.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      if (this.chargeComponent.isCharging()) {
         this.chargeComponent.stopCharging(entity);
      } else {
         this.clearHitsInterval.restartIntervalToZero();
         if (!this.continuousComponent.isContinuous() || this.grabComponent.getState() != GrabEntityComponent.GrabState.IDLE && this.grabComponent.getState() != GrabEntityComponent.GrabState.PULLING) {
            if (this.grabComponent.getState() == GrabEntityComponent.GrabState.IDLE && this.grabComponent.grabNearest(entity, false)) {
               this.grabComponent.triggerPulling(entity);
            } else {
               this.continuousComponent.startContinuity(entity);
            }
         } else {
            this.grabComponent.release(entity);
            this.continuousComponent.stopContinuity(entity);
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
         this.chargeComponent.startCharging(entity, 60.0F);
      }

   }

   private void onContinuityTick(LivingEntity entity, IAbility ability) {
      if (this.grabComponent.getState() != GrabEntityComponent.GrabState.IDLE) {
         if (!this.grabComponent.canContinueGrab(entity)) {
            this.continuousComponent.stopContinuity(entity);
         } else if (this.grabComponent.getState() == GrabEntityComponent.GrabState.THROWN) {
            LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
            if (grabbedTarget.m_20096_()) {
               this.grabComponent.release(entity);
               this.continuousComponent.stopContinuity(entity);
            } else {
               List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(grabbedTarget.m_20182_(), entity.m_9236_(), (double)grabbedTarget.m_20205_(), (double)grabbedTarget.m_20206_(), (double)grabbedTarget.m_20205_(), (Predicate)null);
               targets.remove(grabbedTarget);
               Vec3 dir = entity.m_20154_().m_82541_().m_82490_((double)2.0F);

               for(LivingEntity target : targets) {
                  if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
                     AbilityHelper.setDeltaMovement(target, dir.f_82479_, (double)0.5F, dir.f_82481_);
                  }
               }

               if (this.clearHitsInterval.canTick()) {
                  this.hitTrackerComponent.clearHits();
               }

            }
         }
      }
   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 240.0F);
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
            Vec3 direction = grabbedTarget.m_20182_().m_82546_(entity.m_20182_()).m_82541_();
            float targetYaw = (float)Math.toDegrees(Math.atan2(direction.f_82481_, direction.f_82479_)) - 90.0F;
            float adjustedYaw = (targetYaw + 10.0F) % 360.0F;
            entity.m_146922_(adjustedYaw);
            entity.f_19859_ = adjustedYaw;
            entity.m_146926_(0.0F);
            entity.f_19860_ = 0.0F;
            if (entity instanceof ServerPlayer) {
               ServerPlayer player = (ServerPlayer)entity;
               Set<RelativeMovement> flags = EnumSet.of(RelativeMovement.X, RelativeMovement.Y, RelativeMovement.Z);
               player.f_8906_.m_9780_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), entity.m_146908_(), entity.m_146909_(), flags);
            }

            float distance = 2.0F;
            Vec3 lookVec = entity.m_20154_().m_82541_();
            Vec3 pos = new Vec3(lookVec.f_82479_ * (double)distance, (double)(entity.m_20192_() / 2.0F) + lookVec.f_82480_ * (double)distance, lookVec.f_82481_ * (double)distance);
            AbilityHelper.setDeltaMovement(grabbedTarget, entity.m_20182_().m_82549_(pos).m_82546_(grabbedTarget.m_20182_()), true);
            List<LivingEntity> targets = WyHelper.<LivingEntity>getNearbyLiving(entity.m_20182_(), entity.m_9236_(), (double)grabbedTarget.m_20205_(), (double)grabbedTarget.m_20206_(), (double)grabbedTarget.m_20205_(), ModEntityPredicates.getEnemyFactions(entity));
            targets.remove(grabbedTarget);
            if (!HakiHelper.hasHardeningActive(entity)) {
               targets.removeIf((targetx) -> (Boolean)DevilFruitCapability.get(entity).map((props) -> props.isLogia()).orElse(false));
            }

            Vec3 dir = lookVec.m_82490_((double)2.0F);

            for(LivingEntity target : targets) {
               if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, 10.0F)) {
                  AbilityHelper.setDeltaMovement(target, dir.f_82479_, (double)0.5F, dir.f_82481_);
               }
            }

            if (this.clearHitsInterval.canTick()) {
               this.hitTrackerComponent.clearHits();
            }

         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         if (this.grabComponent.canContinueGrab(entity)) {
            LivingEntity grabbedTarget = this.grabComponent.getGrabbedEntity();
            if (this.dealDamageComponent.hurtTarget(entity, grabbedTarget, 30.0F)) {
               grabbedTarget.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 80, 0));
            }

            this.grabComponent.throwTarget(entity, (double)2.0F, (double)0.5F);
            this.continuousComponent.startContinuity(entity, 40.0F);
         } else {
            super.cooldownComponent.startCooldown(entity, 240.0F);
         }

         this.animationComponent.stop(entity);
      }
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-6.0F, 6.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.addPrerequisites(((AbilityCore)SuplexAbility.INSTANCE.get()).getNode(entity));
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
