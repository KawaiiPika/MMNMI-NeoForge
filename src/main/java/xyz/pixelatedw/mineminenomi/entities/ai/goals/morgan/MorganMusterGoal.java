package xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Zombie;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

// Uses generic Mob and generic spawns to allow compilation, since Marine/Morgan hierarchies are not ported fully.
public class MorganMusterGoal extends TickedGoal<Mob> {
   private boolean used;

   public MorganMusterGoal(Mob entity) {
      super(entity);
   }

   @Override
   public boolean canUse() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.used) {
         return false;
      } else {
         return !(this.entity.getHealth() > (this.entity.getMaxHealth() * 0.5f));
      }
   }

   @Override
   public boolean canContinueToUse() {
      return false;
   }

   @Override
   public void start() {
      super.start();

      // Spawns 4 helpers (representing Marines) around the entity
      for (int i = 0; i < 4; i++) {
          double ox = (i == 0) ? -25.0 : (i == 1) ? 25.0 : 0.0;
          double oz = (i == 2) ? 25.0 : (i == 3) ? -25.0 : 0.0;

          Zombie helper = new Zombie(this.entity.level()); // Fallback for MARINE_SNIPER
          helper.setPos(this.entity.getX() + ox, this.entity.getY(), this.entity.getZ() + oz);
          this.entity.level().addFreshEntity(helper);
      }

      this.used = true;
   }
}
