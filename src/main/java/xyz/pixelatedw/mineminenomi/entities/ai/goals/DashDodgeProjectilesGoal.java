package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.entities.projectiles.NuProjectileEntity;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

import java.util.function.Predicate;

public class DashDodgeProjectilesGoal extends TickedGoal<Mob> {
    private static final float CHECK_AREA = 40.0F;
    private float ticksBetweenDashes;
    private float dashDistance;
    private NuProjectileEntity projectileTarget;
    private int hits;
    private int triggerHits = 3;
    private double previousDistance = 0.0D;
    private Predicate<LivingEntity> canUseTest = entity -> true;

    public DashDodgeProjectilesGoal(Mob entity, float ticksBetweenDashes, float dashDistance) {
        super(entity);
        this.ticksBetweenDashes = ticksBetweenDashes;
        this.dashDistance = dashDistance;
    }

    @Override
    public boolean canUse() {
        if (!GoalHelper.hasAliveTarget(this.entity)) {
            return false;
        } else if (!this.hasTimePassedSinceLastEnd(this.ticksBetweenDashes)) {
            return false;
        } else if (!GoalHelper.canMove(this.entity)) {
            return false;
        } else {
            AABB searchBox = this.entity.getBoundingBox().inflate(CHECK_AREA);
            for (NuProjectileEntity proj : this.entity.level().getEntitiesOfClass(NuProjectileEntity.class, searchBox)) {
                if (proj.distanceToSqr(this.entity) > CHECK_AREA * CHECK_AREA) {
                    continue;
                }

                boolean isEnemyProjectile = proj.getOwner() != this.entity; // Simplified for missing ModEntityPredicates
                if (isEnemyProjectile) {
                    this.projectileTarget = proj;
                    double distance = proj.distanceToSqr(this.entity);
                    if (this.previousDistance == 0.0D) {
                        this.previousDistance = distance;
                    }

                    if (distance < this.previousDistance) {
                        this.hits++;
                        this.previousDistance = distance;
                    }
                    break;
                }
            }

            if (this.hits < this.triggerHits) {
                return false;
            } else if (this.projectileTarget == null) {
                return false;
            } else {
                return this.canUseTest.test(this.entity);
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    @Override
    public void start() {
        super.start();
        GoalHelper.lookAtEntity(this.entity, this.projectileTarget);
        boolean dodgeRight = this.entity.getRandom().nextBoolean();
        double angle = dodgeRight ? -45.0D : 135.0D;
        double yRot = Mth.wrapDegrees((double) this.entity.getYRot() * (Math.PI / 180D)) + Math.toRadians(angle);
        double xp = (double) this.dashDistance * Math.cos(yRot) - (double) this.dashDistance * Math.sin(yRot);
        double zp = (double) this.dashDistance * Math.cos(yRot) + (double) this.dashDistance * Math.sin(yRot);
        this.entity.setDeltaMovement(-xp, 0.1D, -zp);
    }

    @Override
    public void stop() {
        super.stop();
        this.hits = 0;
        this.previousDistance = 0.0D;
    }

    public DashDodgeProjectilesGoal setCanUseTest(Predicate<LivingEntity> test) {
        this.canUseTest = test;
        return this;
    }
}