package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.AbilityUseConditions;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

import java.util.function.Predicate;

public class JumpOverProjectilesGoal extends TickedGoal<Mob> {
    private static final float CHECK_AREA = 60.0F;
    private float cooldown;
    private float jumpHeight;
    private Projectile projectileTarget;
    private int hits;
    private int triggerHits = 3;
    private double previousDistance = 0.0D;
    private Predicate<LivingEntity> canUseTest = entity -> true;

    public JumpOverProjectilesGoal(Mob entity, float cooldown, float jumpHeight) {
        super(entity);
        this.cooldown = cooldown;
        this.jumpHeight = jumpHeight;
    }

    @Override
    public boolean canUse() {
        if (!GoalHelper.hasAliveTarget(this.entity)) {
            return false;
        } else if (!this.hasTimePassedSinceLastEnd(this.cooldown)) {
            return false;
        } else if (!GoalHelper.canMove(this.entity)) {
            return false;
        } else {
            AABB searchBox = this.entity.getBoundingBox().inflate(CHECK_AREA);
            for (Projectile proj : this.entity.level().getEntitiesOfClass(Projectile.class, searchBox)) {
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
        Vec3 lookVec = this.entity.getLookAngle().multiply(3.0D, 0.0D, 3.0D);
        this.entity.setDeltaMovement(lookVec.x, this.jumpHeight, lookVec.z);
    }

    @Override
    public void stop() {
        super.stop();
        this.hits = 0;
        this.previousDistance = 0.0D;
    }

    public void setCanUseTest(Predicate<LivingEntity> test) {
        this.canUseTest = test;
    }
}