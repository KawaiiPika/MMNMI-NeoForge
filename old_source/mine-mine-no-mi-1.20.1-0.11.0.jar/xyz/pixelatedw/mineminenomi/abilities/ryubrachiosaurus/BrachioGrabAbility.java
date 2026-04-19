package xyz.pixelatedw.mineminenomi.abilities.ryubrachiosaurus;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.pixelatedw.mineminenomi.api.abilities.Ability;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCategory;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityCore;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityDescriptionLine;
import xyz.pixelatedw.mineminenomi.api.abilities.AbilityPool;
import xyz.pixelatedw.mineminenomi.api.abilities.IAbility;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AnimationComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChargeComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceHakiNature;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityTooltipsHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModMorphs;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class BrachioGrabAbility extends Ability {
   private static final int COOLDOWN = 300;
   private static final int CHARGE_TIME = 140;
   private static final int PULL_TIME = 200;
   private static final float DAMAGE = 6.0F;
   public static final RegistryObject<AbilityCore<BrachioGrabAbility>> INSTANCE = ModRegistry.registerAbility("brachio_grab", "Brachio Grab", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Grabs an opponent and squashes them.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.DEVIL_FRUITS, BrachioGrabAbility::new)).addDescriptionLine(desc).addDescriptionLine(AbilityDescriptionLine.NEW_LINE, AbilityTooltipsHelper.getRequiredMorphTooltip(ModMorphs.BRACHIO_HEAVY)).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(300.0F), ChargeComponent.getTooltip(140.0F)).setSourceHakiNature(SourceHakiNature.HARDENING).setSourceType(SourceType.FIST).build("mineminenomi");
   });
   private final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   private final ChargeComponent chargeComponent = (new ChargeComponent(this)).addStartEvent(100, this::onChargeStart).addTickEvent(this::onChargeTick).addEndEvent(this::onChargeEnd);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, true, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addEndEvent(this::onContinuityEnd);
   private final PoolComponent poolComponent;
   private Interval hurtInterval;

   public BrachioGrabAbility(AbilityCore<BrachioGrabAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.hurtInterval = new Interval(30);
      this.addComponents(new AbilityComponent[]{this.dealDamageComponent, this.chargeComponent, this.animationComponent, this.continuousComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent});
      this.addCanUseCheck(RyuBrachioHelper::requiresHeavyPoint);
      this.addContinueUseCheck(RyuBrachioHelper::requiresHeavyPoint);
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
         this.chargeComponent.startCharging(entity, 140.0F);
      }

   }

   private void onContinuityEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (this.grabComponent.getState() != GrabEntityComponent.GrabState.GRABBED) {
            this.grabComponent.release(entity);
         }

         if (!this.grabComponent.canContinueGrab(entity)) {
            super.cooldownComponent.startCooldown(entity, 300.0F);
         }

      }
   }

   public void onChargeStart(LivingEntity entity, IAbility ability) {
      this.hurtInterval.restartIntervalToZero();
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
   }

   public void onChargeTick(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 2, 1));
            if (this.hurtInterval.canTick()) {
               this.dealDamageComponent.hurtTarget(entity, target, 6.0F);
            }

         } else {
            this.chargeComponent.stopCharging(entity);
         }
      }
   }

   public void onChargeEnd(LivingEntity entity, IAbility ability) {
      Level level = entity.m_9236_();
      if (level != null && !level.f_46443_) {
         this.grabComponent.release(entity);
         this.animationComponent.stop(entity);
         super.cooldownComponent.startCooldown(entity, 300.0F);
      }
   }
}
