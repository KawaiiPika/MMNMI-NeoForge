package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;
import net.minecraft.world.phys.AABB;

import java.util.EnumSet;
import java.util.List;

public class EatNearbyFishGoal extends TickedGoal<Mob> {
    private static final int COOLDOWN = 40;
    private List<AbstractFish> fishes;
    private AbstractFish target;
    private long lastCheck = 0L;
    private boolean justAte = false;

    public EatNearbyFishGoal(Mob entity) {
        super(entity);
        this.setFlags(EnumSet.of(Flag.LOOK, Flag.MOVE, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (GoalHelper.hasAliveTarget(this.entity)) {
            return false;
        } else if (this.lastCheck == 0L) {
            this.lastCheck = this.entity.level().getGameTime();
            return false;
        } else if (!this.hasTimePassedSinceLastCheck(40.0F)) {
            return false;
        } else if (!this.hasTimePassedSinceLastEnd(40.0F)) {
            return false;
        } else {
            double range = this.getFollowDistance();
            AABB searchBox = this.entity.getBoundingBox().inflate(range);
            this.fishes = this.entity.level().getEntitiesOfClass(
                    AbstractFish.class, searchBox,
                    fish -> fish.isAlive() && fish.distanceToSqr(this.entity) <= range * range
            );
            if (this.fishes.isEmpty()) {
                this.lastCheck = this.entity.level().getGameTime();
                return false;
            } else {
                this.target = this.fishes.get(0);
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.target != null && this.target.isAlive()) {
            return !this.justAte;
        } else {
            return false;
        }
    }

    protected double getFollowDistance() {
        return this.entity.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void start() {
        super.start();
        this.justAte = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.entity.tickCount % 20 == 0) {
            this.entity.getNavigation().moveTo(this.target, 1.2D);
        }

        if (this.target != null && this.entity.distanceToSqr(this.target) < 25.0D) {
            this.target.remove(RemovalReason.DISCARDED);
            this.entity.level().broadcastEntityEvent(this.target, (byte) 3);

            for (int i = 0; i < 20; ++i) {
                double d0 = this.entity.getRandom().nextGaussian() * 0.02D;
                double d1 = this.entity.getRandom().nextGaussian() * 0.02D;
                double d2 = this.entity.getRandom().nextGaussian() * 0.02D;
                this.entity.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.entity.getRandomX(1.0D), this.entity.getRandomY(), this.entity.getRandomZ(1.0D), d0, d1, d2);
            }

            this.justAte = true;
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    public boolean hasTimePassedSinceLastCheck(float ticks) {
        return (float) this.entity.level().getGameTime() >= (float) this.lastCheck + ticks;
    }
}