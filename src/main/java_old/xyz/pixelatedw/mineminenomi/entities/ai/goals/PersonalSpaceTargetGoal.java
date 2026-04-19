package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;

public class PersonalSpaceTargetGoal extends TickedGoal<Mob> {
   private static final int TICKS_BEFORE_MARK_UPDATE = 200;
   private HashMap<Integer, Long> personalSpaceTargets;
   private Interval checkInterval;
   private final int ticksBeforeAttacking;
   private final double checkDistance;
   private final Predicate<LivingEntity> targetCheck;

   public PersonalSpaceTargetGoal(Mob entity) {
      this(entity, (double)10.0F, 200);
   }

   public PersonalSpaceTargetGoal(Mob entity, double checkDistance) {
      this(entity, checkDistance, 200);
   }

   public PersonalSpaceTargetGoal(Mob entity, double checkDistance, int ticksBeforeAttacking) {
      super(entity);
      this.personalSpaceTargets = new HashMap();
      this.checkInterval = new Interval(20);
      this.checkDistance = checkDistance;
      this.ticksBeforeAttacking = ticksBeforeAttacking;
      this.targetCheck = this.getDefaultTargetCheck(entity);
   }

   public PersonalSpaceTargetGoal(Mob entity, double checkDistance, int ticksBeforeAttacking, Predicate<LivingEntity> targetCheck) {
      super(entity);
      this.personalSpaceTargets = new HashMap();
      this.checkInterval = new Interval(20);
      this.checkDistance = checkDistance;
      this.ticksBeforeAttacking = ticksBeforeAttacking;
      this.targetCheck = targetCheck;
   }

   public boolean m_8036_() {
      if (!this.entity.m_6084_()) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return this.hasTimePassedSinceLastEnd(60.0F);
      }
   }

   public boolean m_8045_() {
      if (!this.entity.m_6084_()) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return GoalHelper.getNearbyVisibleEntities(this.entity, this.checkDistance, (Predicate)null).size() > 0;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.checkInterval.restartIntervalToMax();
      this.personalSpaceTargets.clear();
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.checkInterval.canTick()) {
         List<LivingEntity> targets = TargetHelper.<LivingEntity>getEntitiesInArea(this.entity.m_9236_(), this.entity, this.checkDistance, (TargetPredicate)null, LivingEntity.class);
         if (targets.size() > 0) {
            for(LivingEntity target : targets) {
               if (this.targetCheck.test(target)) {
                  if (this.personalSpaceTargets.containsKey(target.m_19879_())) {
                     long time = (Long)this.personalSpaceTargets.get(target.m_19879_());
                     if (this.entity.m_9236_().m_46467_() >= time + (long)this.ticksBeforeAttacking) {
                        this.entity.m_6710_(target);
                     }
                  } else {
                     this.personalSpaceTargets.put(target.m_19879_(), this.entity.m_9236_().m_46467_());
                  }
               }
            }
         }
      }

   }

   public void m_8041_() {
      super.m_8041_();
   }

   public Predicate<LivingEntity> getDefaultTargetCheck(LivingEntity entity) {
      return (target) -> {
         if (target instanceof Player) {
            return true;
         } else {
            return !target.m_6095_().equals(entity.m_6095_());
         }
      };
   }
}
