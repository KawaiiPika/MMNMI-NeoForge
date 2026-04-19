package xyz.pixelatedw.mineminenomi.entities.ai.goals.dugong;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.AbstractDugongEntity;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;
import xyz.pixelatedw.mineminenomi.init.ModParticleTypes;
import xyz.pixelatedw.mineminenomi.particles.data.SimpleParticleData;

public class DugongRestGoal extends TickedGoal<AbstractDugongEntity> {
   private Interval canUseInterval = new Interval(40);
   private Interval sleepBubbleTimer = new Interval(50);
   private int restTime;
   private int nextRestTime = 2;
   private float startHealth;
   private float startYaw;

   public DugongRestGoal(AbstractDugongEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.canUseInterval.canTick()) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).isIdling()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).hasMemoryValue((MemoryModuleType)ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())) {
         return false;
      } else {
         if (this.getLastEndTick() <= 0L) {
            this.setLastEndTick(((AbstractDugongEntity)this.entity).m_9236_().m_46467_());
         }

         if (!this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)this.nextRestTime))) {
            return false;
         } else {
            return !GoalHelper.hasAliveTarget(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      if (((AbstractDugongEntity)this.entity).isEnraged()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).isCheering()) {
         return false;
      } else if (!((AbstractDugongEntity)this.entity).m_5803_()) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).hasMemoryValue((MemoryModuleType)ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())) {
         return false;
      } else if (((AbstractDugongEntity)this.entity).m_21223_() < this.startHealth) {
         return false;
      } else if (GoalHelper.shouldMove(this.entity)) {
         return false;
      } else if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return !this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)this.restTime));
      }
   }

   public void m_8056_() {
      super.m_8056_();
      ((AbstractDugongEntity)this.entity).setResting(true);
      this.restTime = 20 + ((AbstractDugongEntity)this.entity).m_217043_().m_188503_(20);
      this.nextRestTime = 2 + ((AbstractDugongEntity)this.entity).m_217043_().m_188503_(3);
      ((AbstractDugongEntity)this.entity).m_21573_().m_26573_();
      this.startHealth = ((AbstractDugongEntity)this.entity).m_21223_();
      this.startYaw = (this.entity).f_20883_;
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.sleepBubbleTimer.canTick()) {
         Vec3 look = ((AbstractDugongEntity)this.entity).m_20154_().m_82490_((double)0.75F);
         SimpleParticleData part = new SimpleParticleData((ParticleType)ModParticleTypes.AWA.get());
         part.setLife(20);
         part.setSize(2.0F);
         part.setHasScaleDecay(false);
         WyHelper.spawnParticles(part, (ServerLevel)((AbstractDugongEntity)this.entity).m_9236_(), ((AbstractDugongEntity)this.entity).m_20185_() + look.f_82479_, ((AbstractDugongEntity)this.entity).m_20188_() + look.f_82480_ - 0.85, ((AbstractDugongEntity)this.entity).m_20189_() + look.f_82481_);
      }

      this.startYaw = (this.entity).f_20883_;
   }

   public void m_8041_() {
      super.m_8041_();
      ((AbstractDugongEntity)this.entity).setResting(false);
   }
}
