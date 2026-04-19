package xyz.pixelatedw.mineminenomi.entities.ai.goals.whitewalkie;

import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.WhiteWalkieEntity;
import xyz.pixelatedw.mineminenomi.init.ModMemoryModuleTypes;

public class WhiteWalkieSleepGoal extends TickedGoal<WhiteWalkieEntity> {
   private static final int COOLDOWN = 40;
   private int restTime;
   private int nextRestTime = 2;
   private float startHealth;
   private float startYaw;

   public WhiteWalkieSleepGoal(WhiteWalkieEntity entity) {
      super(entity);
      this.m_7021_(EnumSet.of(Flag.JUMP, Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (!this.hasTimePassedSinceLastEnd(40.0F)) {
         return false;
      } else if (!((WhiteWalkieEntity)this.entity).isIdling()) {
         return false;
      } else if (!((WhiteWalkieEntity)this.entity).m_9236_().m_46462_()) {
         return false;
      } else if (((WhiteWalkieEntity)this.entity).hasMemoryValue((MemoryModuleType)ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())) {
         return false;
      } else {
         if (this.getLastEndTick() <= 0L) {
            this.setLastEndTick(((WhiteWalkieEntity)this.entity).m_9236_().m_46467_());
         }

         if (!this.hasTimePassedSinceLastEnd(WyHelper.minutesToTicks((float)this.nextRestTime))) {
            return false;
         } else {
            return !GoalHelper.hasAliveTarget(this.entity);
         }
      }
   }

   public boolean m_8045_() {
      if (GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!((WhiteWalkieEntity)this.entity).m_5803_()) {
         return false;
      } else if (((WhiteWalkieEntity)this.entity).hasMemoryValue((MemoryModuleType)ModMemoryModuleTypes.LAST_EXPLOSION_HEARD.get())) {
         return false;
      } else if (((WhiteWalkieEntity)this.entity).m_21223_() < this.startHealth) {
         return false;
      } else if (GoalHelper.shouldMove(this.entity)) {
         return false;
      } else {
         return !this.hasTimePassedSinceStart(WyHelper.secondsToTicks((float)this.restTime));
      }
   }

   public void m_8056_() {
      super.m_8056_();
      ((WhiteWalkieEntity)this.entity).setSleeping(true);
      this.restTime = 60 + ((WhiteWalkieEntity)this.entity).m_217043_().m_188503_(60);
      this.nextRestTime = 2 + ((WhiteWalkieEntity)this.entity).m_217043_().m_188503_(3);
      ((WhiteWalkieEntity)this.entity).m_21573_().m_26573_();
      this.startHealth = ((WhiteWalkieEntity)this.entity).m_21223_();
      this.startYaw = (this.entity).f_20883_;
   }

   public void m_8037_() {
      super.m_8037_();
      (this.entity).f_20883_ = this.startYaw;
   }

   public void m_8041_() {
      super.m_8041_();
      ((WhiteWalkieEntity)this.entity).setSleeping(false);
   }
}
