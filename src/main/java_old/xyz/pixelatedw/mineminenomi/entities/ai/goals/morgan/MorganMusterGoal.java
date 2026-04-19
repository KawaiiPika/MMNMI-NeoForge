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

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (this.used) {
         return false;
      } else if (((MorganEntity)this.entity).isDifficultyStandard()) {
         return false;
      } else {
         return !((double)((MorganEntity)this.entity).m_21223_() > WyHelper.percentage((double)50.0F, (double)((MorganEntity)this.entity).m_21233_()));
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      SniperEntity sniper1 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), ((MorganEntity)this.entity).m_9236_());
      sniper1.m_6034_((double)-25.0F, (double)64.0F, (double)0.0F);
      ((MorganEntity)this.entity).m_9236_().m_7967_(sniper1);
      SniperEntity sniper2 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), ((MorganEntity)this.entity).m_9236_());
      sniper2.m_6034_((double)25.0F, (double)64.0F, (double)0.0F);
      ((MorganEntity)this.entity).m_9236_().m_7967_(sniper2);
      SniperEntity sniper3 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), ((MorganEntity)this.entity).m_9236_());
      sniper3.m_6034_((double)0.0F, (double)64.0F, (double)25.0F);
      ((MorganEntity)this.entity).m_9236_().m_7967_(sniper3);
      SniperEntity sniper4 = SniperEntity.createMarineSniper((EntityType)ModMobs.MARINE_SNIPER.get(), ((MorganEntity)this.entity).m_9236_());
      sniper4.m_6034_((double)0.0F, (double)64.0F, (double)-25.0F);
      ((MorganEntity)this.entity).m_9236_().m_7967_(sniper4);
      this.used = true;
   }
}
