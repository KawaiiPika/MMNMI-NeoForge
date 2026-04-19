package xyz.pixelatedw.mineminenomi.api.entities.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public abstract class TickedGoal<E extends Mob> extends Goal {
   protected E entity;
   private long startTick;
   private long lastEndTick;
   private long tickCount;

   public TickedGoal(E entity) {
      this.entity = entity;
   }

   public void m_8056_() {
      super.m_8056_();
      this.startTick = this.entity.m_9236_().m_46467_();
   }

   public void m_8037_() {
      super.m_8037_();
      ++this.tickCount;
   }

   public void m_8041_() {
      super.m_8041_();
      this.lastEndTick = this.entity.m_9236_().m_46467_();
   }

   public long getStartTick() {
      return this.startTick;
   }

   public void setLastEndTick(long tick) {
      this.lastEndTick = tick;
   }

   public long getLastEndTick() {
      return this.lastEndTick;
   }

   public long getTickCount() {
      return this.tickCount;
   }

   public boolean hasTimePassedSinceStart(float ticks) {
      return (float)this.entity.m_9236_().m_46467_() >= (float)this.getStartTick() + ticks;
   }

   public boolean hasTimePassedSinceLastEnd(float ticks) {
      if (this.getLastEndTick() == 0L) {
         this.setLastEndTick(this.entity.m_9236_().m_46467_());
      }

      return (float)this.entity.m_9236_().m_46467_() >= (float)this.getLastEndTick() + ticks;
   }
}
