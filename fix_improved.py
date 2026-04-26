import os

filepath = './src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/ImprovedMeleeAttackGoal.java'
os.makedirs(os.path.dirname(filepath), exist_ok=True)

with open(filepath, 'w') as f:
    f.write("""package xyz.pixelatedw.mineminenomi.entities.ai.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ImprovedMeleeAttackGoal extends MeleeAttackGoal {
    public ImprovedMeleeAttackGoal(PathfinderMob entity, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(entity, speedModifier, followingTargetEvenIfNotSeen);
    }
}
""")

filepath = './src/main/java/xyz/pixelatedw/mineminenomi/entities/ai/goals/pacifista/PacifistaRadicalBeamGoal.java'
os.makedirs(os.path.dirname(filepath), exist_ok=True)

with open(filepath, 'w') as f:
    f.write("""package xyz.pixelatedw.mineminenomi.entities.ai.goals.pacifista;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import xyz.pixelatedw.mineminenomi.api.helpers.GoalHelper;

public class PacifistaRadicalBeamGoal extends Goal {
    private final Mob entity;
    private int chargeTime;

    public PacifistaRadicalBeamGoal(Mob entity) {
        this.entity = entity;
    }

    @Override
    public boolean canUse() {
        return GoalHelper.hasAliveTarget(this.entity) && this.entity.getRandom().nextInt(40) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return GoalHelper.hasAliveTarget(this.entity) && this.chargeTime > 0;
    }

    @Override
    public void start() {
        this.chargeTime = 40; // 2 seconds
        this.entity.level().broadcastEntityEvent(this.entity, (byte) 100);
    }

    @Override
    public void tick() {
        this.entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 4));
        LivingEntity target = this.entity.getTarget();
        if (target != null) {
            GoalHelper.lookAtEntity(this.entity, target);
        }

        --this.chargeTime;
        if (this.chargeTime == 0 && target != null) {
            this.entity.level().broadcastEntityEvent(this.entity, (byte) 101);
            if (GoalHelper.canSee(this.entity, target)) {
                target.hurt(this.entity.damageSources().mobAttack(this.entity), 15.0F);
                this.entity.level().explode(this.entity, target.getX(), target.getY(), target.getZ(), 2.0F, net.minecraft.world.level.Level.ExplosionInteraction.NONE);
            }
        }
    }
}
""")
