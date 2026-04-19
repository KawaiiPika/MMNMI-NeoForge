package xyz.pixelatedw.mineminenomi.abilities;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
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
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.CooldownComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.GrabEntityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.PoolComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.init.ModAbilityPools;
import xyz.pixelatedw.mineminenomi.init.ModAnimations;
import xyz.pixelatedw.mineminenomi.init.ModEffects;
import xyz.pixelatedw.mineminenomi.init.ModRegistry;

public class GrabLockAbility extends Ability {
   private static final int HOLD_TIME = 40;
   private static final int PULL_TIME = 200;
   private static final float COOLDOWN = 200.0F;
   public static final RegistryObject<AbilityCore<GrabLockAbility>> INSTANCE = ModRegistry.registerAbility("grab_lock", "Grab Lock", (id, name) -> {
      Component[] desc = AbilityHelper.registerDescriptionText("mineminenomi", id, ImmutablePair.of("Grabs an opponent from the back and keeps them pinned like that.", (Object)null));
      return (new AbilityCore.Builder(id, name, AbilityCategory.STYLE, GrabLockAbility::new)).addDescriptionLine(desc).addAdvancedDescriptionLine(AbilityDescriptionLine.NEW_LINE, CooldownComponent.getTooltip(200.0F), ContinuousComponent.getTooltip(40.0F)).build("mineminenomi");
   });
   private final ContinuousComponent continuousComponent = (new ContinuousComponent(this, true)).addTickEvent(this::duringContinuityEvent).addEndEvent(this::endContinuityEvent);
   private final AnimationComponent animationComponent = new AnimationComponent(this);
   private final GrabEntityComponent grabComponent = (new GrabEntityComponent(this, true, true, true, 2.0F)).addPullStartEvent(this::onPullStart).addPullEndEvent(this::onPullEnd).addGrabEvent(this::grabEvent).addReleaseEvent(this::releaseEvent);
   private final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(this::tryHitEvent).addOnHitEvent(this::onHitEvent);
   private final PoolComponent poolComponent;

   public GrabLockAbility(AbilityCore<GrabLockAbility> core) {
      super(core);
      this.poolComponent = new PoolComponent(this, ModAbilityPools.GRAB_ABILITY, new AbilityPool[0]);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.animationComponent, this.grabComponent, this.hitTriggerComponent, this.poolComponent});
      this.addCanUseCheck(AbilityUseConditions::requiresEmptyHand);
      this.addUseEvent(this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity);
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
      if (this.grabComponent.canContinueGrab(entity)) {
         this.continuousComponent.setThresholdTime(entity, 40.0F);
      }

   }

   private void duringContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.grabComponent.getState() != GrabEntityComponent.GrabState.IDLE) {
         if (!super.canUse(entity).isFail() && this.grabComponent.canContinueGrab(entity)) {
            LivingEntity target = this.grabComponent.getGrabbedEntity();
            if (target != null) {
               entity.m_7292_(new MobEffectInstance((MobEffect)ModEffects.MOVEMENT_BLOCKED.get(), 5, 1));
               target.m_7292_(new MobEffectInstance((MobEffect)ModEffects.GRABBED.get(), 5, 3));
            }

         } else {
            this.continuousComponent.stopContinuity(entity);
         }
      }
   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      if (!entity.m_9236_().f_46443_) {
         this.grabComponent.release(entity);
         this.animationComponent.stop(entity);
         this.cooldownComponent.startCooldown(entity, 200.0F);
      }
   }

   private boolean grabEvent(LivingEntity entity, LivingEntity target, IAbility ability) {
      this.animationComponent.start(entity, ModAnimations.POINT_ARMS);
      return true;
   }

   private void releaseEvent(LivingEntity entity, LivingEntity target, IAbility ability) {
   }
}
