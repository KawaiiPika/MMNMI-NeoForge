package xyz.pixelatedw.mineminenomi.entities.ai.goals.lapahn;

import java.util.UUID;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.util.Interval;
import xyz.pixelatedw.mineminenomi.entities.mobs.animals.LapahnEntity;

public class LapahnRageGoal extends Goal {
   private static final AttributeModifier RAGE_MODIFIER;
   private Interval canUseInterval = new Interval(10);
   private LapahnEntity entity;

   public LapahnRageGoal(LapahnEntity entity) {
      this.entity = entity;
   }

   public boolean m_8036_() {
      if (this.entity.isEnraged()) {
         return false;
      } else if (!this.canUseInterval.canTick()) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         return !(this.entity.m_21223_() > this.entity.m_21233_() / 3.0F);
      }
   }

   public boolean m_8045_() {
      return true;
   }

   public void m_8056_() {
      this.entity.setEnraged(true);
      this.entity.getNearbyLapahns().forEach((lapahn) -> lapahn.setEnraged(true));
      this.entity.setResting(false);
      AttributeInstance attr = this.entity.m_21051_(Attributes.f_22281_);
      if (attr != null) {
         attr.m_22130_(RAGE_MODIFIER);
         attr.m_22118_(RAGE_MODIFIER);
      }

   }

   static {
      RAGE_MODIFIER = new AttributeModifier(UUID.fromString("4b03a4b4-1eb5-464a-8312-0f9079044462"), "Rage Mode Multiplier", (double)10.0F, Operation.ADDITION);
   }
}
