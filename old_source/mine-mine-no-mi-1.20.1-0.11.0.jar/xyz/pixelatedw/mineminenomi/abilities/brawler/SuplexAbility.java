package xyz.pixelatedw.mineminenomi.abilities.brawler;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
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
import xyz.pixelatedw.mineminenomi.api.math.EasingFunctionHelper;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModFightingStyles;
import xyz.pixelatedw.mineminenomi.init.ModParticleEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;
import xyz.pixelatedw.mineminenomi.particles.effects.CommonExplosionParticleEffect;
import xyz.pixelatedw.mineminenomi.particles.effects.ParticleEffect;

public class SuplexAbility extends Ability {
   private static final int PULL_TIME = 200;
   private static final int CHARGE_TIME = 20;
   private static final float COOLDOWN = 140.0F;
   private static final float DAMAGE = 20.0F;
   public static final RegistryObject<AbilityCore<SuplexAbility>> INSTANCE = ModRegistry.registerAbility("suplex", "Suplex", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Grabs an opponent from the back and launches it into the ground", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, SuplexAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(140.0F), ChargeComponent.getTooltip(20.0F), DealDamageComponent.getTooltip(20.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).setNodeFactories(SuplexAbility::createNode).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onContinuityEnd);
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, false, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;
   private final ExplosionComponent explosionComponent;

   public SuplexAbility(AbilityCore<SuplexAbility> core) {
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
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 2, 3));
            float distance = 1.0F;
            Vec3 lookVec = entity.m_20154_().m_82541_();
            Vec3 pos = (new Vec3(lookVec.f_82479_ * (double)distance, (double)entity.m_20206_(), lookVec.f_82481_ * (double)distance)).m_82490_((double)this.chargeComponent.getChargePercentage());
            AbilityHelper.setDeltaMovement(target, entity.m_20182_().m_82492_(pos.f_82479_, (double)(-EasingFunctionHelper.easeInOutSine((float)pos.f_82480_)), pos.f_82481_).m_82546_(target.m_20182_()), true);
         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      if (this.grabComponent.canContinueGrab(entity)) {
         LivingEntity target = this.grabComponent.getGrabbedEntity();
         if (this.dealDamageComponent.hurtTarget(entity, target, 20.0F)) {
            target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.DIZZY.get(), 40, 0));
         }

         AbilityExplosion explosion = this.explosionComponent.createExplosion(entity, target.m_20185_(), target.m_20186_(), target.m_20189_(), 1.0F);
         explosion.setStaticDamage(6.0F);
         explosion.setExplosionSound(true);
         explosion.setDamageOwner(false);
         explosion.setDestroyBlocks(false);
         explosion.setFireAfterExplosion(false);
         explosion.setSmokeParticles((ParticleEffect)ModParticleEffects.COMMON_EXPLOSION.get(), CommonExplosionParticleEffect.EXPLOSION2);
         explosion.setDamageEntities(true);
         this.grabComponent.release(entity);
      }

      this.animationComponent.stop(entity);
      super.cooldownComponent.startCooldown(entity, 140.0F);
   }

   private static AbilityNode createNode(LivingEntity entity) {
      AbilityNode node = new AbilityNode(((AbilityCore)INSTANCE.get()).getLocalizedName(), ((AbilityCore)INSTANCE.get()).getIcon(), new AbilityNode.NodePos(-6.0F, 4.0F));
      NodeUnlockCondition unlockCondition = RequiredStyleUnlockCondition.requires((FightingStyle)ModFightingStyles.BRAWLER.get());
      NodeUnlockAction unlockAction = UnlockAbilityAction.unlock(INSTANCE);
      node.setUnlockRule(unlockCondition, unlockAction);
      return node;
   }
}
