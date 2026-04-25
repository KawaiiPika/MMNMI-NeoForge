package xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan;

import net.minecraft.world.entity.EntityType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class MorganMusterGoal extends TickedGoal {

   public MorganMusterGoal(MorganEntity entity) {
      super(entity);
   }

   @Override
   public boolean canUse() {
      return false; // WIP
   }

   @Override
   public boolean canContinueToUse() {
      return false;
   }

   @Override
   public void start() {
      super.start();
   }
}
