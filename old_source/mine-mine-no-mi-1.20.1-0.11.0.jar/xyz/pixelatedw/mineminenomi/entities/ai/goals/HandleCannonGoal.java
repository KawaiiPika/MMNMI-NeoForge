package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import xyz.pixelatedw.mineminenomi.api.WyHelper;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import xyz.pixelatedw.mineminenomi.api.helpers.StructuresHelper;
import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class HandleCannonGoal extends TickedGoal<Mob> {
   private CannonEntity cannon;
   private LivingEntity target;
   private long lastShot;

   public HandleCannonGoal(Mob entity) {
      super(entity);
   }

   public boolean m_8036_() {
      if (this.entity.f_19797_ % 100 != 0) {
         return false;
      } else if (this.entity.m_20159_()) {
         return false;
      } else if (!GoalHelper.hasAliveTarget(this.entity)) {
         return false;
      } else {
         this.target = this.entity.m_5448_();
         if (GoalHelper.isWithinDistance(this.entity, this.target, (double)5.0F)) {
            return false;
         } else {
            StructureStart entityStructure = StructuresHelper.getStructureAt((ServerLevel)this.entity.m_9236_(), this.entity.m_20183_());
            StructureStart targetStructure = StructuresHelper.getStructureAt((ServerLevel)this.entity.m_9236_(), this.target.m_20183_());
            if (entityStructure != null && entityStructure == targetStructure) {
               return false;
            } else {
               List<CannonEntity> nearbyCannons = WyHelper.<CannonEntity>getNearbyEntities(this.entity.m_20182_(), this.entity.m_9236_(), (double)5.0F, (Predicate)null, CannonEntity.class);
               Optional<CannonEntity> targetCannon = nearbyCannons.stream().filter((cannon) -> !cannon.m_20160_()).findFirst();
               if (!targetCannon.isPresent()) {
                  return false;
               } else {
                  this.cannon = (CannonEntity)targetCannon.get();
                  return true;
               }
            }
         }
      }
   }

   public boolean m_8045_() {
      if (this.cannon != null && this.cannon.m_6084_()) {
         if (!GoalHelper.hasAliveTarget(this.entity)) {
            return false;
         } else if (GoalHelper.isWithinDistance(this.entity, this.target, (double)5.0F)) {
            return false;
         } else {
            StructureStart entityStructure = StructuresHelper.getStructureAt((ServerLevel)this.entity.m_9236_(), this.entity.m_20183_());
            StructureStart targetStructure = StructuresHelper.getStructureAt((ServerLevel)this.entity.m_9236_(), this.target.m_20183_());
            return entityStructure == null || entityStructure != targetStructure;
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      super.m_8056_();
      this.entity.m_7998_(this.cannon, true);
   }

   public void m_8037_() {
      super.m_8037_();
      GoalHelper.lookAtEntity(this.entity, this.target);
      this.cannon.m_146926_(this.entity.m_146909_() - 8.0F);
      this.cannon.m_146922_(this.entity.m_146908_());
      if (this.lastShot == 0L) {
         this.lastShot = this.entity.m_9236_().m_46467_();
      }

      if (this.entity.m_9236_().m_46467_() >= this.lastShot + 100L) {
         this.entity.m_6674_(InteractionHand.MAIN_HAND);
         this.lastShot = this.entity.m_9236_().m_46467_();
      }

   }

   public void m_8041_() {
      super.m_8041_();
      this.entity.m_8127_();
   }
}
