package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;

public class DespawnAfterTimeGoal extends TickedGoal<Mob> {
   private final int ticksTilDespawn;

   public DespawnAfterTimeGoal(Mob entity, int ticks) {
      super(entity);
      this.ticksTilDespawn = ticks;
   }

   public boolean m_8036_() {
      return true;
   }

   public boolean m_8045_() {
      return !this.hasTimePassedSinceStart((float)this.ticksTilDespawn);
   }

   public void m_8041_() {
      super.m_8041_();
      this.entity.m_142687_(RemovalReason.DISCARDED);
   }
}
