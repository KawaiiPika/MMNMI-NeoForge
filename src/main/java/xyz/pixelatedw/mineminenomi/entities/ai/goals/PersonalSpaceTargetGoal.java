package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import xyz.pixelatedw.mineminenomi.api.entities.ai.TickedGoal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class PersonalSpaceTargetGoal extends TickedGoal<Mob> {
    private static final int TICKS_BEFORE_MARK_UPDATE = 200;
    private final HashMap<Integer, Long> personalSpaceTargets;
    private final int ticksBeforeAttacking;
    private final double checkDistance;
    private final Predicate<LivingEntity> targetCheck;
    private int checkIntervalTick = 0;

    public PersonalSpaceTargetGoal(Mob entity) {
        this(entity, 10.0D, TICKS_BEFORE_MARK_UPDATE);
    }

    public PersonalSpaceTargetGoal(Mob entity, double checkDistance) {
        this(entity, checkDistance, TICKS_BEFORE_MARK_UPDATE);
    }

    public PersonalSpaceTargetGoal(Mob entity, double checkDistance, int ticksBeforeAttacking) {
        super(entity);
        this.personalSpaceTargets = new HashMap<>();
        this.checkDistance = checkDistance;
        this.ticksBeforeAttacking = ticksBeforeAttacking;
        this.targetCheck = this.getDefaultTargetCheck(entity);
    }

    public PersonalSpaceTargetGoal(Mob entity, double checkDistance, int ticksBeforeAttacking, Predicate<LivingEntity> targetCheck) {
        super(entity);
        this.personalSpaceTargets = new HashMap<>();
        this.checkDistance = checkDistance;
        this.ticksBeforeAttacking = ticksBeforeAttacking;
        this.targetCheck = targetCheck;
    }

    @Override
    public boolean canUse() {
        if (!this.entity.isAlive()) {
            return false;
        } else if (GoalHelper.hasAliveTarget(this.entity)) {
            return false;
        } else {
            return this.hasTimePassedSinceLastEnd(60.0F);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.entity.isAlive()) {
            return false;
        } else if (GoalHelper.hasAliveTarget(this.entity)) {
            return false;
        } else {
            AABB searchBox = this.entity.getBoundingBox().inflate(this.checkDistance);
            return this.entity.level().getEntitiesOfClass(
                    LivingEntity.class, searchBox,
                    target -> target != this.entity && target.isAlive() && target.distanceToSqr(this.entity) <= this.checkDistance * this.checkDistance && this.entity.hasLineOfSight(target)
            ).size() > 0;
        }
    }

    @Override
    public void start() {
        super.start();
        this.checkIntervalTick = 20;
        this.personalSpaceTargets.clear();
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.checkIntervalTick <= 0) {
            this.checkIntervalTick = 20;
            AABB searchBox = this.entity.getBoundingBox().inflate(this.checkDistance);
            List<LivingEntity> targets = this.entity.level().getEntitiesOfClass(
                    LivingEntity.class, searchBox,
                    target -> target != this.entity && target.isAlive() && target.distanceToSqr(this.entity) <= this.checkDistance * this.checkDistance
            );
            if (!targets.isEmpty()) {
                for (LivingEntity target : targets) {
                    if (this.targetCheck.test(target)) {
                        if (this.personalSpaceTargets.containsKey(target.getId())) {
                            long time = this.personalSpaceTargets.get(target.getId());
                            if (this.entity.level().getGameTime() >= time + this.ticksBeforeAttacking) {
                                this.entity.setTarget(target);
                            }
                        } else {
                            this.personalSpaceTargets.put(target.getId(), this.entity.level().getGameTime());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    public Predicate<LivingEntity> getDefaultTargetCheck(LivingEntity entity) {
        return target -> {
            if (target instanceof Player) {
                return true;
            } else {
                return target.getType() != entity.getType();
            }
        };
    }
}