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

   public void start() {
      super.start();
      this.startTick = this.entity.level().getGameTime();
   }

   public void tick() {
      super.tick();
      ++this.tickCount;
   }

   public void stop() {
      super.stop();
      this.lastEndTick = this.entity.level().getGameTime();
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
      return (float)this.entity.level().getGameTime() >= (float)this.getStartTick() + ticks;
   }

   public boolean hasTimePassedSinceLastEnd(float ticks) {
      if (this.getLastEndTick() == 0L) {
         this.setLastEndTick(this.entity.level().getGameTime());
      }

      return (float)this.entity.level().getGameTime() >= (float)this.getLastEndTick() + ticks;
   }
}
