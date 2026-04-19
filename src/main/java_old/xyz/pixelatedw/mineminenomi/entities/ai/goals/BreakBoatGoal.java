package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.vehicle.Boat;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.data.entity.kairosekicoating.KairosekiCoatingCapability;

public class BreakBoatGoal extends Goal {
   private final PathfinderMob entity;
   private Entity boat;

   public BreakBoatGoal(PathfinderMob entity) {
      this.entity = entity;
   }

   public boolean m_8036_() {
      for(Boat boat : this.entity.m_9236_().m_45976_(Boat.class, this.entity.m_20191_().m_82363_((double)2.0F, (double)2.0F, (double)2.0F))) {
         Entity entity = boat.m_6688_();
         boolean isFullyCoated = (Boolean)KairosekiCoatingCapability.get(this.entity).map((props) -> props.isFullyCoated()).orElse(false);
         if (entity != null && entity instanceof LivingEntity && boat.m_6084_() && GoalHelper.canSee(this.entity, boat) && isFullyCoated) {
            this.boat = boat;
            break;
         }
      }

      return this.boat != null && this.boat.m_6084_();
   }

   public boolean m_8045_() {
      return this.boat != null && this.boat.m_6084_();
   }

   public void m_8056_() {
      AttributeInstance attr = this.entity.m_21051_(Attributes.f_22281_);
      float damage = (float)(attr != null ? attr.m_22135_() : (double)1.0F);
      this.boat.m_6469_(this.entity.m_269291_().m_269333_(this.entity), damage);
      this.boat = null;
   }

   public void m_8041_() {
      this.boat = null;
   }
}
