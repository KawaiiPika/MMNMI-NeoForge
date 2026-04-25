package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import java.util.List;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.phys.AABB;
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

    @Override
    public boolean canUse() {
        if (this.mob.tickCount % 100 != 0) {
            return false;
        } else if (this.mob.isPassenger()) {
            return false;
        } else if (!GoalHelper.hasAliveTarget(this.mob)) {
            return false;
        } else {
            this.target = this.mob.getTarget();
            if (GoalHelper.isWithinDistance(this.mob, this.target, 5.0D)) {
                return false;
            } else {
                StructureStart entityStructure = StructuresHelper.getStructureAt((ServerLevel) this.mob.level(), this.mob.blockPosition());
                StructureStart targetStructure = StructuresHelper.getStructureAt((ServerLevel) this.mob.level(), this.target.blockPosition());
                if (entityStructure != null && entityStructure == targetStructure) {
                    return false;
                } else {
                    AABB searchBox = this.mob.getBoundingBox().inflate(5.0D);
                    List<CannonEntity> nearbyCannons = this.mob.level().getEntitiesOfClass(
                            CannonEntity.class, searchBox,
                            cannon -> !cannon.isRemoved() && cannon.distanceToSqr(this.mob) <= 25.0D
                    );
                    Optional<CannonEntity> targetCannon = nearbyCannons.stream().findFirst();
                    if (!targetCannon.isPresent()) {
                        return false;
                    } else {
                        this.cannon = targetCannon.get();
                        return true;
                    }
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.cannon != null && this.cannon.isAlive()) {
            if (!GoalHelper.hasAliveTarget(this.mob)) {
                return false;
            } else if (GoalHelper.isWithinDistance(this.mob, this.target, 5.0D)) {
                return false;
            } else {
                StructureStart entityStructure = StructuresHelper.getStructureAt((ServerLevel) this.mob.level(), this.mob.blockPosition());
                StructureStart targetStructure = StructuresHelper.getStructureAt((ServerLevel) this.mob.level(), this.target.blockPosition());
                return entityStructure == null || entityStructure != targetStructure;
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        super.start();
        this.mob.startRiding(this.cannon, true);
    }

    @Override
    public void tick() {
        super.tick();
        GoalHelper.lookAtEntity(this.mob, this.target);
        this.cannon.setXRot(this.mob.getXRot() - 8.0F);
        this.cannon.setYRot(this.mob.getYRot());
        if (this.lastShot == 0L) {
            this.lastShot = this.mob.level().getGameTime();
        }

        if (this.mob.level().getGameTime() >= this.lastShot + 100L) {
            this.mob.swing(InteractionHand.MAIN_HAND);
            this.lastShot = this.mob.level().getGameTime();
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.stopRiding();
    }
}
