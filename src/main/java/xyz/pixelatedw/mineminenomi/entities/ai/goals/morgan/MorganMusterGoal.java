package xyz.pixelatedw.mineminenomi.entities.ai.goals.morgan;

import net.minecraft.world.entity.EntityType;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.mobs.SniperEntity;
import xyz.pixelatedw.mineminenomi.entities.mobs.marines.MorganEntity;
import xyz.pixelatedw.mineminenomi.init.ModMobs;

public class MorganMusterGoal extends TickedGoal<MorganEntity> {
   private boolean used;

   public MorganMusterGoal(MorganEntity entity) {
      super(entity);
   }

   @Override
   public boolean canUse() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.used) {
         return false;
      } else if (this.entity.isDifficultyStandard()) {
         return false;
      } else {
         return !(this.entity.getHealth() > WyHelper.percentage(50.0F, this.entity.getMaxHealth()));
      }
   }

   @Override
   public boolean canContinueToUse() {
      return false;
   }

   @Override
   public void start() {
      super.start();
      SniperEntity sniper1 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), this.entity.level());
      sniper1.setPos(-25.0D, 64.0D, 0.0D); // Note: Should probably base this off entity pos or something, but following legacy exactly for now
      this.entity.level().addFreshEntity(sniper1);

      SniperEntity sniper2 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), this.entity.level());
      sniper2.setPos(25.0D, 64.0D, 0.0D);
      this.entity.level().addFreshEntity(sniper2);

      SniperEntity sniper3 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), this.entity.level());
      sniper3.setPos(0.0D, 64.0D, 25.0D);
      this.entity.level().addFreshEntity(sniper3);

      SniperEntity sniper4 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), this.entity.level());
      sniper4.setPos(0.0D, 64.0D, -25.0D);
      this.entity.level().addFreshEntity(sniper4);

      this.used = true;
   }
}
