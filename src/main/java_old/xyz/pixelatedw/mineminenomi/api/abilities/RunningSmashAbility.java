package xyz.pixelatedw.mineminenomi.api.abilities;

import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.abilities.components.DealDamageComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.HitTrackerComponent;
import xyz.pixelatedw.mineminenomi.api.abilities.components.RangeComponent;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public abstract class RunningSmashAbility extends PassiveAbility {
   private final HitTrackerComponent hitTrackerComponent;
   private final DealDamageComponent dealDamageComponent;
   private final RangeComponent rangeComponent;
   protected static final float DEFAULT_RANGE = 1.5F;
   protected static final float DEFAULT_DAMAGE = 2.0F;
   private float area;
   private float damage;
   private Interval resetInterval;

   public RunningSmashAbility(AbilityCore<? extends RunningSmashAbility> core) {
      this(core, 1.5F, 2.0F);
   }

   public RunningSmashAbility(AbilityCore<? extends RunningSmashAbility> core, float area, float damage) {
      super(core);
      this.hitTrackerComponent = new HitTrackerComponent(this);
      this.dealDamageComponent = new DealDamageComponent(this);
      this.rangeComponent = new RangeComponent(this);
      this.resetInterval = new Interval(20);
      this.area = area;
      this.damage = damage;
      super.addComponents(this.hitTrackerComponent, this.rangeComponent, this.dealDamageComponent);
      super.addDuringPassiveEvent(this::duringPassiveEvent);
   }

   private void duringPassiveEvent(LivingEntity entity) {
      if (this.resetInterval.canTick()) {
         this.hitTrackerComponent.clearHits();
      }

      if (entity.m_20142_() && this.canSmash(entity)) {
         List<LivingEntity> targets = this.rangeComponent.getTargetsInLine(entity, 1.0F, this.area);
         targets.removeIf((e) -> e.m_20202_() != null && e.m_20202_().equals(entity));

         for(LivingEntity target : targets) {
            if (this.hitTrackerComponent.canHit(target) && this.dealDamageComponent.hurtTarget(entity, target, this.damage)) {
               Vec3 speed = entity.m_20154_();
               AbilityHelper.setDeltaMovement(target, speed.f_82479_ * (double)2.0F, 0.2, speed.f_82481_ * (double)2.0F);
            }
         }
      }

   }

   public abstract boolean canSmash(LivingEntity var1);
}
