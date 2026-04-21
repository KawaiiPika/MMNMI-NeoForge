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


import xyz.pixelatedw.mineminenomi.entities.vehicles.CannonEntity;

public class HandleCannonGoal extends TickedGoal<Mob> {
   private CannonEntity cannon;
   private LivingEntity target;
   private long lastShot;

   public HandleCannonGoal(Mob entity) {
      super(entity);
   }

   public boolean canUse() {
      if (this.entity.tickCount % 100 != 0) {
         return false;
      } else if (this.entity.isPassenger()) {
         return false;
      } else if (!(this.entity.getTarget() != null && this.entity.getTarget().isAlive())) {
         return false;
      } else {
         this.target = this.entity.getTarget();
         if ((this.entity.distanceTo(this.target) < 5.0D)) {
            return false;
         } else {
            StructureStart entityStructure = null;
            StructureStart targetStructure = null;
            if (entityStructure != null && entityStructure == targetStructure) {
               return false;
            } else {
               List<CannonEntity> nearbyCannons = this.entity.level().getEntitiesOfClass(CannonEntity.class, this.entity.getBoundingBox().inflate(5.0D));
               Optional<CannonEntity> targetCannon = nearbyCannons.stream().filter((cannon) -> !cannon.isVehicle()).findFirst();
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

   public boolean canContinueToUse() {
      if (this.cannon != null && this.cannon.isAlive()) {
         if (!(this.entity.getTarget() != null && this.entity.getTarget().isAlive())) {
            return false;
         } else if ((this.entity.distanceTo(this.target) < 5.0D)) {
            return false;
         } else {
            StructureStart entityStructure = null;
            StructureStart targetStructure = null;
            return entityStructure == null || entityStructure != targetStructure;
         }
      } else {
         return false;
      }
   }

   public void start() {
      super.start();
      this.entity.startRiding(this.cannon, true);
   }

   public void tick() {
      super.tick();
      this.entity.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
      this.cannon.setXRot(this.entity.getXRot() - 8.0F);
      this.cannon.setYRot(this.entity.getYRot());
      if (this.lastShot == 0L) {
         this.lastShot = this.entity.level().getGameTime();
      }

      if (this.entity.level().getGameTime() >= this.lastShot + 100L) {
         this.entity.swing(InteractionHand.MAIN_HAND);
         this.lastShot = this.entity.level().getGameTime();
      }

   }

   public void stop() {
      super.stop();
      this.entity.stopRiding();
   }
}
