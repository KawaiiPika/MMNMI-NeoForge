package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.entities.ChakramEntity;
import xyz.pixelatedw.mineminenomi.init.ModWeapons;
import xyz.pixelatedw.mineminenomi.items.weapons.ChakramItem;

public class ThrowChakramsGoal extends TickedGoal<Mob> {
   private LivingEntity target;
   private final int cooldown;

   public ThrowChakramsGoal(Mob entity) {
      super(entity);
      this.cooldown = 200;
   }

   public ThrowChakramsGoal(Mob entity, int cooldown) {
      super(entity);
      this.cooldown = cooldown;
   }

   public boolean m_8036_() {
      if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else if (!this.hasTimePassedSinceLastEnd((float)this.cooldown)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         return !GoalHelper.isWithinDistance(this.entity, this.target, (double)6.0F);
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8056_() {
      super.m_8056_();
      float x = (float)(this.target.m_20185_() - this.entity.m_20185_());
      float z = (float)(this.target.m_20189_() - this.entity.m_20189_());
      float angle = (float)Math.toDegrees(Math.atan2((double)x, (double)z));
      ChakramEntity chakram = new ChakramEntity(this.entity, ((ChakramItem)ModWeapons.CHAKRAM.get()).m_7968_());
      chakram.m_6034_(chakram.m_20185_(), this.entity.m_20188_(), chakram.m_20189_());
      chakram.m_37251_(this.entity, this.entity.m_146909_(), -angle, 0.0F, 3.0F, 1.0F);
      chakram.setAttackDamage(3.0F + ((ChakramItem)ModWeapons.CHAKRAM.get()).m_43299_());
      this.entity.m_9236_().m_7967_(chakram);
   }
}
