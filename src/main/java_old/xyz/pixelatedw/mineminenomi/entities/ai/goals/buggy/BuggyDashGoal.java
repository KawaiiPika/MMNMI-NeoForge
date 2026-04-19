package xyz.pixelatedw.mineminenomi.entities.ai.goals.buggy;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.damagesources.TargetPredicate;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.TargetHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.pirates.buggypirates.BuggyEntity;

public class BuggyDashGoal extends TickedGoal<BuggyEntity> {
   private static final int COOLDOWN = 200;
   private LivingEntity target;
   private boolean hasReachedTarget;
   private int stopAccel;
   private Vec3 targetPos;
   private double speed;
   private float damage;
   private Set<LivingEntity> alreadyHurt = new HashSet();

   public BuggyDashGoal(BuggyEntity entity, double speed, float damage) {
      super(entity);
      this.speed = speed;
      this.damage = damage;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = ((BuggyEntity)this.entity).m_5448_();
         if (!this.hasTimePassedSinceLastEnd(200.0F)) {
            return false;
         } else {
            return this.target.m_20096_();
         }
      }
   }

   public boolean m_8045_() {
      if (((BuggyEntity)this.entity).m_20238_(this.targetPos) < (double)4.0F) {
         return false;
      } else if (this.stopAccel >= 20) {
         return false;
      } else {
         return !this.hasTimePassedSinceStart(200.0F);
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.alreadyHurt.clear();
      this.hasReachedTarget = false;
      this.stopAccel = 0;
      GoalHelper.lookAtEntity(this.entity, this.target);
      Vec3 extraVec = this.target.m_20182_().m_82546_(((BuggyEntity)this.entity).m_20182_()).m_82490_((double)0.5F);
      this.targetPos = this.target.m_20182_().m_82549_(extraVec);
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.hasReachedTarget) {
         ++this.stopAccel;
      }

      if (this.target.m_20096_()) {
         GoalHelper.lookAt(this.entity, this.targetPos);
         Vec3 look = ((BuggyEntity)this.entity).m_20154_();
         Vec3 speed = look.m_82542_(this.speed, (double)0.0F, this.speed);
         ((BuggyEntity)this.entity).m_6478_(MoverType.SELF, speed);
      }

      for(LivingEntity target : TargetHelper.getEntitiesInArea(((BuggyEntity)this.entity).m_9236_(), this.entity, (double)1.5F, (TargetPredicate)null, LivingEntity.class)) {
         if (!this.alreadyHurt.contains(target)) {
            target.m_6469_(((BuggyEntity)this.entity).m_269291_().m_269333_(this.entity), this.damage);
            this.alreadyHurt.add(target);
            this.hasReachedTarget = true;
         }
      }

   }
}
