package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.List;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import xyz.pixelatedw.mineminenomi.api.abilities.components.AbilityComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.ContinuousComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DashComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;

public abstract class DashAbility extends Ability {
   private static final TargetPredicate TARGET_CHECK;
   public final ContinuousComponent continuousComponent = (new ContinuousComponent(this, this.isParallel())).addStartEvent(200, this::startContinuityEvent).addTickEvent(200, this::duringContinuity).addEndEvent(200, this::endContinuityEvent);
   public final HitTrackerComponent hitTrackerComponent = new HitTrackerComponent(this);
   public final RangeComponent rangeComponent = new RangeComponent(this);
   public final DealDamageComponent dealDamageComponent = new DealDamageComponent(this);
   public final DashComponent dashComponent = new DashComponent(this);

   public DashAbility(AbilityCore<? extends DashAbility> core) {
      super(core);
      this.addComponents(new AbilityComponent[]{this.continuousComponent, this.hitTrackerComponent, this.rangeComponent, this.dealDamageComponent, this.dashComponent});
      this.addCanUseCheck(AbilityUseConditions::canUseMomentumAbilities);
      this.addUseEvent(200, this::useEvent);
   }

   private void useEvent(LivingEntity entity, IAbility ability) {
      this.continuousComponent.startContinuity(entity, (float)this.getHoldTime());
   }

   private void startContinuityEvent(LivingEntity entity, IAbility ability) {
      this.hitTrackerComponent.clearHits();
      float dashSpeed = this.getSpeed();
      this.dashComponent.dashXYZ(entity, dashSpeed, this.getYSpeed(), this.getYBump());
   }

   private void duringContinuity(LivingEntity entity, IAbility ability) {
      if (entity.m_6084_()) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInArea(entity, this.getRange() + entity.m_20205_() / 2.0F, this.getTargetCheck());
         float damage = this.getDamage();
         DamageSource source = this.getDamageSource(entity);

         for(LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, damage, source)) {
               this.onTargetHit(entity, target, damage, source);
            }
         }
      }

   }

   private void endContinuityEvent(LivingEntity entity, IAbility ability) {
      this.cooldownComponent.startCooldown(entity, this.getDashCooldown());
   }

   public abstract void onTargetHit(LivingEntity var1, LivingEntity var2, float var3, DamageSource var4);

   public DamageSource getDamageSource(LivingEntity entity) {
      return this.dealDamageComponent.getDamageSource(entity);
   }

   public TargetPredicate getTargetCheck() {
      return TARGET_CHECK;
   }

   public boolean isParallel() {
      return false;
   }

   public abstract float getDashCooldown();

   public abstract float getDamage();

   public float getRange() {
      return 0.0F;
   }

   public abstract float getSpeed();

   public float getYSpeed() {
      return 0.2F;
   }

   public float getYBump() {
      return 0.0F;
   }

   public int getHoldTime() {
      return 10;
   }

   static {
      TARGET_CHECK = TargetPredicate.DEFAULT_AREA_CHECK;
   }
}
