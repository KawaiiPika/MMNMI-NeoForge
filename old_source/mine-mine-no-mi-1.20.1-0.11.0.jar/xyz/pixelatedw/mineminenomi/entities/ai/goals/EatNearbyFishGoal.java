package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.AbstractFish;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class EatNearbyFishGoal extends TickedGoal<Mob> {
   private static final int COOLDOWN = 40;
   private List<AbstractFish> fishes = new ArrayList();
   private AbstractFish target;
   private long lastCheck = 0L;
   private boolean justAte = false;

   public EatNearbyFishGoal(Mob entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.LOOK, Flag.MOVE, Flag.TARGET));
   }

   public boolean m_8036_() {
      if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.lastCheck == 0L) {
         this.lastCheck = this.entity.m_9236_().m_46467_();
         return false;
      } else if (!this.hasTimePassedSinceLastCheck(40.0F)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd(40.0F)) {
         return false;
      } else {
         this.fishes = WyHelper.<AbstractFish>getNearbyEntities(this.entity.m_20182_(), this.entity.m_9236_(), this.getFollowDistance(), (Predicate)null, AbstractFish.class);
         if (this.fishes.size() <= 0) {
            this.lastCheck = this.entity.m_9236_().m_46467_();
            return false;
         } else {
            this.target = (AbstractFish)this.fishes.get(0);
            return true;
         }
      }
   }

   public boolean m_8045_() {
      if (this.target != null && this.target.m_6084_()) {
         return !this.justAte;
      } else {
         return false;
      }
   }

   protected double getFollowDistance() {
      return this.entity.m_21133_(Attributes.f_22277_);
   }

   public void m_8056_() {
      super.m_8056_();
      this.justAte = false;
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.entity.f_19797_ % 20 == 0) {
         this.entity.m_21573_().m_5624_(this.target, 1.2);
      }

      if (this.target != null && this.entity.m_20280_(this.target) < (double)25.0F) {
         this.target.m_142687_(RemovalReason.DISCARDED);
         this.entity.m_9236_().m_7605_(this.target, (byte)3);

         for(int i = 0; i < 20; ++i) {
            double d0 = this.entity.m_217043_().m_188583_() * 0.02;
            double d1 = this.entity.m_217043_().m_188583_() * 0.02;
            double d2 = this.entity.m_217043_().m_188583_() * 0.02;
            this.entity.m_9236_().m_7106_(ParticleTypes.f_123759_, this.entity.m_20208_((double)1.0F), this.entity.m_20187_(), this.entity.m_20262_((double)1.0F), d0, d1, d2);
         }

         this.justAte = true;
      }

   }

   public void m_8041_() {
      super.m_8041_();
   }

   public boolean hasTimePassedSinceLastCheck(float ticks) {
      return (float)this.entity.m_9236_().m_46467_() >= (float)this.lastCheck + ticks;
   }
}
