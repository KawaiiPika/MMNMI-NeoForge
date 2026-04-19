package xyz.pixelatedw.mineminenomi.api.abilities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ChangeStatsComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTriggerComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.IDamageSourceHandler;
import xyz.pixelatedw.mineminenomi.api.damagesources.SourceType;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.util.Result;

public abstract class PunchAbility extends Ability {
   protected final ContinuousComponent continuousComponent = (new ContinuousComponent(this, this.isParallel())).addStartEvent(90, this::startContinuityEvent).addTickEvent(90, this::tickContinuityEvent).addEndEvent(90, this::endContinuityEvent);
   protected final ChangeStatsComponent statsComponent = new ChangeStatsComponent(this);
   protected final HitTriggerComponent hitTriggerComponent = (new HitTriggerComponent(this)).addTryHitEvent(200, this::tryHitEvent).addOnHitEvent(200, this::onHitEvent);
   private int uses = 0;
   private boolean markForStopping;

   public PunchAbility(AbilityCore<? extends PunchAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.statsComponent, this.hitTriggerComponent});
      this.addUseEvent(200, this::onUseEvent);
   }

   private void onUseEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.triggerContinuity(entity, this.getPunchHoldTime());
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.uses = 0;
      this.markForStopping = false;
      this.statsComponent.applyModifiers(entity);
   }

   private void tickContinuityEvent(LivingEntity entity, IAbility ability) {
      if (this.markForStopping) {
         this.continuousComponent.stopContinuity(entity);
         this.markForStopping = false;
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.statsComponent.removeModifiers(entity);
      float cooldown = this.getPunchCooldown();
      if (cooldown > 0.0F && this.markForStopping) {
         this.cooldownComponent.startCooldown(entity, cooldown);
      }

   }

   private HitTriggerComponent.HitResult tryHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (!this.continuousComponent.isContinuous()) {
         return HitTriggerComponent.HitResult.PASS;
      } else {
         boolean var10000;
         label32: {
            IDamageSourceHandler sourceHandler = IDamageSourceHandler.getHandler(source);
            Entity var8 = source.m_7640_();
            if (var8 instanceof Projectile) {
               Projectile proj = (Projectile)var8;
               if (proj.m_19749_().equals(entity) && sourceHandler.hasType(SourceType.FIST)) {
                  var10000 = true;
                  break label32;
               }
            }

            var10000 = false;
         }

         boolean isFistProjectile = var10000;
         boolean canTrigger = this.canTriggerHit(entity).isSuccess();
         if (canTrigger || isFistProjectile) {
            boolean canUse = !this.getContinueUseChecksStream().anyMatch((p) -> p.canUse(entity, ability).isFail());
            if (canUse) {
               return HitTriggerComponent.HitResult.HIT;
            }
         }

         return HitTriggerComponent.HitResult.PASS;
      }
   }

   private boolean onHitEvent(LivingEntity entity, LivingEntity target, DamageSource source, IAbility ability) {
      if (this.getPunchDamage() > 0.0F) {
         IDamageSourceHandler.getHandler(source).addAbilityDamage(this.getPunchDamage(), this.getCore());
      }

      boolean result = this.onHitEffect(entity, target, source);
      this.increaseUses();
      return result;
   }

   public void increaseUses() {
      ++this.uses;
      if (this.getUseLimit() > 0 && this.uses >= this.getUseLimit()) {
         this.markForStopping = true;
      }

   }

   public float getPunchDamage() {
      return 0.0F;
   }

   public abstract float getPunchCooldown();

   public abstract boolean onHitEffect(LivingEntity var1, LivingEntity var2, DamageSource var3);

   public float getPunchHoldTime() {
      return -1.0F;
   }

   public boolean isParallel() {
      return false;
   }

   public Result canTriggerHit(LivingEntity entity) {
      return AbilityUseConditions.requiresEmptyHand(entity, this);
   }

   public abstract int getUseLimit();
}
